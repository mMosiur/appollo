package org.umcs.appollo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.api.PollsApi;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.api.Answer;
import org.umcs.appollo.model.api.Poll;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.services.AnswersService;
import org.umcs.appollo.services.PollService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PollController implements PollsApi {

    private final PollService pollService;
    private final AnswersService answersService;

    public PollController(PollService pollService, AnswersService answersService) {
        this.pollService = pollService;
        this.answersService = answersService;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poll> createPoll(@Valid Poll poll) {
        PollEntity createdPool;
        try{
            createdPool = pollService.createPoll(poll);

        }catch(ResponseStatusException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(pollService.getPoll(createdPool.getId()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePollById(Integer id) {
        try{
            pollService.deletePoll(id);
        }catch(ResponseStatusException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Answer>> getAnswersToPollById(Integer id) {
        List<Answer> answers;
        try{
            answers = answersService.getAnswersForPoll(id);
        }catch(ResponseStatusException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(answers);
    }

    @Override
    public ResponseEntity<Poll> getPollById(Integer id) {
        Poll poll;
        try{
            poll = pollService.getPoll(id);

        }catch(ResponseStatusException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(poll);
    }

    @Override
    public ResponseEntity<List<PollLabel>> getPolls() {
        List<PollLabel> polls;
        try {
            polls = pollService.getPolls();
        } catch(ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(polls);
    }

    @Override
    public ResponseEntity<List<Answer>> submitAnswersToPollById(Integer id, @Valid List<Answer> filledPoll) {
        List<Answer> addPollAnswers;
        try {
            addPollAnswers = answersService.addPollAnswers(id, filledPoll);
        } catch(ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(addPollAnswers);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poll> updatePoll(Integer id, @Valid Poll pollDetails) {
        try {
            pollDetails = pollService.updatePoll(id, pollDetails);
        } catch(ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(pollDetails);
    }
}
