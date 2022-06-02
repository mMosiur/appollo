package org.umcs.appollo.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.api.PollsApi;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.api.AnswerDetails;
import org.umcs.appollo.model.api.JsonPatchOperation;
import org.umcs.appollo.model.api.PollDetails;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.services.AnswersService;
import org.umcs.appollo.services.PollService;

@RestController
public class PollController implements PollsApi {

    private PollService pollService;
    private AnswersService answersService;

    public PollController(PollService pollService, AnswersService answersService) {
        this.pollService = pollService;
        this.answersService = answersService;
    }

    @Override
    public ResponseEntity<PollDetails> createPoll(@Valid PollDetails poll) {
        PollEntity createdPool;
        try{
            createdPool = pollService.createPoll(poll);

        }catch(ResponseStatusException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(pollService.getPoll(createdPool.getId()));
    }

    @Override
    public ResponseEntity<Void> deletePollById(Integer id) {
        try{
            pollService.deletePoll(id);
        }catch(ResponseStatusException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<AnswerDetails>> getAnswersToPollById(Integer id) {
        List<AnswerDetails> answers;
        try{
            answers = answersService.getAnswersForPoll(id);
        }catch(ResponseStatusException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(answers);
    }

    @Override
    public ResponseEntity<PollDetails> getPollById(Integer id) {
        PollDetails poll;
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
    public ResponseEntity<List<AnswerDetails>> submitAnswersToPollById(Integer id, @Valid List<AnswerDetails> filledPoll) {
        List<AnswerDetails> addPollAnswers;
        try {
            addPollAnswers = answersService.addPollAnswers(id, filledPoll);
        } catch(ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(addPollAnswers);
    }

    @Override
    public ResponseEntity<PollDetails> updatePoll(Integer id, @Valid PollDetails pollDetails) {
        try {
            pollDetails = pollService.updatePoll(id, pollDetails);
        } catch(ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(pollDetails);
    }

    @Override
    public ResponseEntity<PollDetails> updatePollById(Integer id, @Valid List<JsonPatchOperation> jsonPatchOperation) {
        PollDetails pollPatched;
        try {
            pollPatched = pollService.updatePollById(id, jsonPatchOperation);
        } catch (JsonPatchException | ResponseStatusException | IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok(pollPatched);
    }
}
