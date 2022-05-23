package org.umcs.appollo.converters;

import org.springframework.stereotype.Service;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.PollDetails;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.model.api.QuestionDetails;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollConverter {
    final QuestionConverter questionConverter;

    public PollConverter(QuestionConverter questionConverter) {
        this.questionConverter = questionConverter;
    }

    public PollLabel FromEntityToApi(PollEntity pollEntity) {
        if (pollEntity == null) {
            return null;
        }
        PollLabel result = new PollLabel();
        result.setId(pollEntity.getId());
        result.setName(pollEntity.getName());
        return result;
    }

    public PollDetails FromEntityToApiDetailed(PollEntity pollEntity) {
        if (pollEntity == null)
            return null;
            PollDetails poll = new PollDetails();
        poll.setId(pollEntity.getId());
        List<QuestionDetails> questions = pollEntity.getQuestions()
                .stream()
                .map(questionConverter::FromEntityToApi)
                .collect(Collectors.toList());
        poll.setQuestions(questions);
        poll.setName(pollEntity.getName());
        return poll;
    }

    public PollEntity FromApiToEntity(PollDetails poll) {
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
