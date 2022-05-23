package org.umcs.appollo.converters;

import org.springframework.stereotype.Service;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.api.AnswerDetails;

@Service
public class AnswerConverter {
    public AnswerDetails FromEntityToApi(AnswerEntity answerEntity) {
        if (answerEntity == null)
            return null;
        AnswerDetails answer = new AnswerDetails();
        answer.setId(answerEntity.getId());
        answer.setQuestionId(answerEntity.getQuestion().getId());
        answer.setAnswerJson(answerEntity.getAnswerJson());
        return answer;
    }

    public AnswerEntity FromApiToEntity(AnswerDetails answer) {
        if (answer == null)
            return null;
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setId(answer.getId());
        answerEntity.setAnswerJson(answer.getAnswerJson());
        return answerEntity;
    }
}
