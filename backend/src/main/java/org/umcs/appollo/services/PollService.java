package org.umcs.appollo.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.converters.PollConverter;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.Poll;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService {
    private final PollRepository pollRepository;
    private final PollConverter pollConverter;
    private final QuestionRepository questionRepository;

    public PollService(PollRepository pollRepository, PollConverter pollConverter,
            QuestionRepository questionRepository) {
        this.pollRepository = pollRepository;
        this.pollConverter = pollConverter;
        this.questionRepository = questionRepository;
    }

    public List<PollLabel> getPolls() {
        List<PollLabel> pollList = pollRepository
                .findAll()
                .stream()
                .map(pollConverter::FromEntityToApi)
                .collect(Collectors.toList());
        return pollList;
    }

    public Poll getPoll(Integer id) {
        Optional<PollEntity> result = pollRepository.findById(id);
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + id + " not found.");

        PollEntity pollEntity = result.get();
        return pollConverter.FromEntityToApiDetailed(pollEntity);
    }

    public PollEntity createPoll(Poll poll) {
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

    public Poll updatePoll(Integer id, Poll newPoll) {
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
}
