package org.umcs.appollo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.umcs.appollo.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/")
public class PollController implements PollsApi {

    private final PollService pollService;
    private final UserService userService;
    private final AnswersService answersService;

    public PollController(PollService pollService, AnswersService answersService, UserService userService) {
        this.pollService = pollService;
        this.answersService = answersService;
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poll> createPoll(@Valid Poll poll) {
        PollEntity createdPoll = pollService.createPoll(poll,
                    userService.findOne(
                            ((User)SecurityContextHolder
                                    .getContext()
                                    .getAuthentication()
                                    .getPrincipal())
                                    .getUsername())
                            .getId());
        return new ResponseEntity<>(pollService.getPoll(createdPoll.getId()), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePollById(Integer id) {
        pollService.deletePoll(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Answer>> getAnswersToPollById(Integer id) {
        List<Answer> answers = answersService.getAnswersForPoll(id);
        return ResponseEntity.ok().body(answers);
    }

    @Override
    public ResponseEntity<Poll> getPollById(Integer id) {
        Poll poll = pollService.getPoll(id);
        return ResponseEntity.ok().body(poll);
    }

    @Override
    public ResponseEntity<List<PollLabel>> getPolls() {
        List<PollLabel> polls;
        try {
            polls = pollService.getPolls();
        } catch (ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(polls);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Answer>> submitAnswersToPollById(Integer id, @Valid List<Answer> filledPoll) {
        List<Answer> addPollAnswers = answersService.addPollAnswers(id, filledPoll,
                    userService.findOne(
                            ((User) SecurityContextHolder
                                    .getContext()
                                    .getAuthentication()
                                    .getPrincipal())
                                    .getUsername())
                            .getId());

        return ResponseEntity.ok().body(addPollAnswers);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poll> updatePoll(Integer id, @Valid Poll pollDetails) {
        pollDetails = pollService.updatePoll(id, pollDetails);
        return ResponseEntity.ok().body(pollDetails);
    }
}
