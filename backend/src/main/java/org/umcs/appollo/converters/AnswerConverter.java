package org.umcs.appollo.converters;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.Answer;

import java.util.Optional;

@Service
public class AnswerConverter {
    public Answer FromEntityToApi(AnswerEntity answerEntity) {
        if (answerEntity == null)
            return null;
        Answer answer = new Answer();
        answer.setId(answerEntity.getId());
        answer.setQuestionId(answerEntity.getQuestion().getId());
        answer.setAnswerJson(answerEntity.getAnswerJson());
        return answer;
    }

    public AnswerEntity FromApiToEntity(Answer answer, Optional<QuestionEntity> questionId) {
        if (answer == null)
            return null;
        if(questionId.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Question not found");
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setId(answer.getId());
        answerEntity.setAnswerJson(answer.getAnswerJson());
        answerEntity.setQuestion(questionId.get());
        return answerEntity;
    }
}
