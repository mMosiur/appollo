package org.umcs.appollo.services;

import org.springframework.stereotype.Service;
import org.umcs.appollo.converters.PollConverter;
import org.umcs.appollo.converters.QuestionConverter;
import org.umcs.appollo.exceptions.ResourceNotFoundException;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.Poll;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.model.api.Question;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService {
    private final PollRepository pollRepository;
    private final PollConverter pollConverter;
    private final QuestionConverter questionConverter;
    private final UserRepository userRepository;

    public PollService(PollRepository pollRepository, PollConverter pollConverter,
                       QuestionConverter questionConverter, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.pollConverter = pollConverter;
        this.questionConverter = questionConverter;
        this.userRepository = userRepository;
    }

    public List<PollLabel> getPolls() {
        return pollRepository
                .findAll()
                .stream()
                .map(pollConverter::FromEntityToApi)
                .collect(Collectors.toList());
    }

    public Poll getPoll(Integer id) {
        Optional<PollEntity> result = pollRepository.findById(id);
        if (result.isEmpty())
            throw new ResourceNotFoundException( "No poll of id " + id + " found.");

        PollEntity pollEntity = result.get();
        return pollConverter.FromEntityToApiDetailed(pollEntity);
    }

    public PollEntity createPoll(Poll poll, int owner_id) {
        PollEntity pollEntity = pollConverter.FromApiToEntity(poll);
        pollEntity.setId(null);
        pollEntity.setUser(userRepository.getById(owner_id));
        pollEntity.setQuestions(null);
        pollEntity = pollRepository.save(pollEntity);
        List<QuestionEntity> questionEntities = new ArrayList<>();
        for (Question question : poll.getQuestions()) {
            QuestionEntity questionEntity = questionConverter.FromApiToEntity(question);
            questionEntity.setPoll(pollEntity);
            questionEntities.add(questionEntity);
        }
        pollEntity.setQuestions(questionEntities);
        return pollRepository.save(pollEntity);
    }

    public void deletePoll(Integer id) {
        if (!pollRepository.existsById(id))
            throw new ResourceNotFoundException( "No poll of id " + id + " found.");

        PollEntity poll = pollRepository.getById(id);
        pollRepository.delete(poll);
    }

    public Poll updatePoll(Integer id, Poll newPoll) {
        if (!pollRepository.existsById(id))
            throw new ResourceNotFoundException( "No poll of id " + id + " found.");

        PollEntity newPollEntity = pollConverter.FromApiToEntity(newPoll);

        // TODO: 08.06.2022 kasowanie uzytkownika po update oraz dodaje edytowane pytanie jako zupełnie nowe pytanie
        for (QuestionEntity question : newPollEntity.getQuestions())
            question.setPoll(newPollEntity);
        newPollEntity.setId(id);
        newPollEntity = pollRepository.save(newPollEntity);
        return pollConverter.FromEntityToApiDetailed(newPollEntity);
    }
}
