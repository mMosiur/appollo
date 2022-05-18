package org.umcs.appollo.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.converters.PollConverter;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.Poll;
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

    public PollService(PollRepository pollRepository, PollConverter pollConverter, QuestionRepository questionRepository) {
        this.pollRepository = pollRepository;
        this.pollConverter = pollConverter;
        this.questionRepository = questionRepository;
    }

    public List<Poll> getPolls() {
        List<Poll> polls = pollRepository.findAll()
            .stream()
            .map(pollConverter::FromEntityToApi)
            .collect(Collectors.toList());
        return polls;
    }

    public Poll getPoll(Integer id) {
        Optional<PollEntity> result = pollRepository.findById(id);
        if(result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + id + " not found.");

        PollEntity pollEntity = result.get();
        return pollConverter.FromEntityToApi(pollEntity);
    }

    public PollEntity createPoll(Poll poll) {
        PollEntity pollEntity = pollConverter.FromApiToEntity(poll);
        if(pollEntity==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll not found");
        for(QuestionEntity question : pollEntity.getQuestions()) question.setPoll(pollEntity);
        pollEntity = pollRepository.save(pollEntity);
        pollEntity.setQuestions(
            questionRepository.saveAll(pollEntity.getQuestions())
        );
        return pollEntity;
    }

    public Poll updatePoll(Integer id, Poll updatedPoll) {
        Optional<PollEntity> result = pollRepository.findById(id);
        if(result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + id + " not found.");
        PollEntity updatedPollEntity = pollConverter.FromApiToEntity(updatedPoll);
        updatedPollEntity.setId(id);
        for(QuestionEntity question : updatedPollEntity.getQuestions()) question.setPoll(updatedPollEntity);
        updatedPollEntity = pollRepository.save(updatedPollEntity);
        updatedPollEntity.setQuestions(
            questionRepository.saveAll(updatedPollEntity.getQuestions())
        );
        updatedPoll = pollConverter.FromEntityToApi(updatedPollEntity);
        return updatedPoll;
    }

   // TODO: 18.05.2022  delete endpoint service
}
