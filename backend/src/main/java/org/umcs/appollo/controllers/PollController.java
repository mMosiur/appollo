import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.api.PollsApi;
import org.umcs.appollo.model.api.FilledPoll;
import org.umcs.appollo.model.api.FilledPollList;
import org.umcs.appollo.model.api.Poll;
import org.umcs.appollo.model.api.PollList;
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
    public ResponseEntity<Poll> createPoll(@Valid Poll poll) {
        // TODO Auto-generated method stub
        return PollsApi.super.createPoll(poll);
    }

    @Override
    public ResponseEntity<Void> deletePollById(Integer id) {
        // TODO Auto-generated method stub
        return PollsApi.super.deletePollById(id);
    }

    @Override
    public ResponseEntity<List<FilledPollList>> getAnswersToPollById(Integer id) {
        // TODO Auto-generated method stub
        return PollsApi.super.getAnswersToPollById(id);
    }

    @Override
    public ResponseEntity<Poll> getPollById(Integer id) {
        Poll poll;
        try{
            poll = pollService.getPoll(id);
        }catch(ResponseStatusException  ex){
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok().body(poll);
    }

    @Override
    public ResponseEntity<PollList> getPolls() {
        // TODO Auto-generated method stub
        return PollsApi.super.getPolls();
    }

    @Override
    public ResponseEntity<FilledPoll> submitAnswersToPollById(Integer id, @Valid FilledPoll filledPoll) {
        // TODO Auto-generated method stub
        return PollsApi.super.submitAnswersToPollById(id, filledPoll);
    }

    @Override
    public ResponseEntity<Poll> updatePollById(Integer id, @Valid Poll poll) {
        // TODO Auto-generated method stub
        return PollsApi.super.updatePollById(id, poll);
    }
    
}
