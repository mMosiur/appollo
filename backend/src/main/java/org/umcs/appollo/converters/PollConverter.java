package org.umcs.appollo.converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.Poll;
import org.umcs.appollo.model.api.Question;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollConverter {
    final QuestionConverter questionConverter;

    public PollConverter(QuestionConverter questionConverter) {
        this.questionConverter = questionConverter;
    }

    public Poll FromEntityToApi(PollEntity pollEntity) {
        if (pollEntity == null)
            return null;
        Poll poll = new Poll();
        poll.setId(pollEntity.getId());
        List<Question> questions = pollEntity.getQuestions()
                .stream()
                .map(questionConverter::FromEntityToApi)
                .collect(Collectors.toList());
        poll.setQuestions(questions);
        poll.setName(pollEntity.getName());
        return poll;
    }

    public PollEntity FromApiToEntity(Poll poll) {
        if (poll == null)
            return null;
        PollEntity pollEntity = new PollEntity();
        pollEntity.setId(poll.getId());
        List<QuestionEntity> questions = poll.getQuestions()
                .stream()
                .map(questionConverter::FromApiToEntity)
                .collect(Collectors.toList());
        pollEntity.setQuestions(questions);
        pollEntity.setName(poll.getName());
        return pollEntity;
    }
}