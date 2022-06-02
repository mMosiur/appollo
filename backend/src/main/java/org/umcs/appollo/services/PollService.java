package org.umcs.appollo.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.converters.PollConverter;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.JsonPatchOperation;
import org.umcs.appollo.model.api.PollDetails;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService {
    private final PollRepository pollRepository;
    private final PollConverter pollConverter;
    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;

    public PollService(PollRepository pollRepository, PollConverter pollConverter,
            QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.pollRepository = pollRepository;
        this.pollConverter = pollConverter;
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
    }

    public List<PollLabel> getPolls() {
        List<PollLabel> pollList = pollRepository
                .findAll()
                .stream()
                .map(pollConverter::FromEntityToApi)
                .collect(Collectors.toList());
        return pollList;
    }

    public PollDetails getPoll(Integer id) {
        Optional<PollEntity> result = pollRepository.findById(id);
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + id + " not found.");

        PollEntity pollEntity = result.get();
        return pollConverter.FromEntityToApiDetailed(pollEntity);
    }

    public PollEntity createPoll(PollDetails poll) {
        PollEntity pollEntity = pollConverter.FromApiToEntity(poll);
        if (pollEntity == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll not found");
        for (QuestionEntity question : pollEntity.getQuestions())
            question.setPoll(pollEntity);
        pollEntity = pollRepository.save(pollEntity);
        pollEntity.setQuestions(
                questionRepository.saveAll(pollEntity.getQuestions()));
        return pollEntity;
    }

    public void deletePoll(Integer id) {
        PollEntity poll = pollRepository.getById(id);
        if (poll == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + id + " not found.");
        }
        pollRepository.delete(poll);
    }

    public PollDetails updatePoll(Integer id, PollDetails newPoll) {
        if (pollRepository.getById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + id + " not found.");
        }
        PollEntity newPollEntity = pollConverter.FromApiToEntity(newPoll);
        if (newPollEntity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll not found");
        }
        for (QuestionEntity question : newPollEntity.getQuestions())
            question.setPoll(newPollEntity);
        newPollEntity.setId(id);
        newPollEntity = pollRepository.save(newPollEntity);
        newPollEntity.setQuestions(
                questionRepository.saveAll(newPollEntity.getQuestions()));
        return pollConverter.FromEntityToApiDetailed(newPollEntity);
    }

    public PollDetails updatePollById(Integer id, List<JsonPatchOperation> jsonPatchOperation)
            throws IOException, JsonPatchException {
        if (!pollRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll of id " + id + " doesn't exist.");

        PollEntity poll = pollRepository.getById(id);

        JsonNode operation = objectMapper.valueToTree(jsonPatchOperation);
        JsonPatch patch = JsonPatch.fromJson(operation);
        JsonNode patched = patch.apply(objectMapper.convertValue(poll, JsonNode.class));
        poll = objectMapper.treeToValue(patched, PollEntity.class);

        pollRepository.save(poll);
        return pollConverter.FromEntityToApiDetailed(poll);
    }
}
