package org.umcs.appollo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.converters.AnswerConverter;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.Answer;
import org.umcs.appollo.repository.AnswerRepository;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;
import org.umcs.appollo.model.api.FilledPoll;

public class AnswersService {
    private final PollRepository pollRepository;
    private final AnswerRepository answerRepository;
    private final AnswerConverter answerConverter;
    private final QuestionRepository questionRepository;

    public AnswersService(PollRepository pollRepository, AnswerRepository answerRepository,
            AnswerConverter answerConverter, QuestionRepository questionRepository) {
        this.pollRepository = pollRepository;
        this.answerRepository = answerRepository;
        this.answerConverter = answerConverter;
        this.questionRepository = questionRepository;
    }

    public List<Answer> addPollAnswers(Integer id, FilledPoll filledPoll) {
        if (filledPoll.getPollId() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poll id from path and body did not match.");
        }
        Optional<PollEntity> result = pollRepository.findById(id);
        List<AnswerEntity> answers;
        try {
            if (result.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + id + " not found.");
            }

            answers = filledPoll.getAnswers()
                    .stream()
                    .map(q -> enrichAnswerWithQuestionInfo(q))
                    .collect(Collectors.toList());
            answers = answerRepository.saveAll(answers);
        } catch (ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return answers.stream().map(a -> answerConverter.FromEntityToApi(a)).collect(Collectors.toList());
    }

    // Is this really needed? Isn't ID enough? To do: test it after controller is in
    // place.
    private AnswerEntity enrichAnswerWithQuestionInfo(Answer a) {
        AnswerEntity answer = answerConverter.FromApiToEntity(a);
        Integer questionId = a.getQuestionId();
        Optional<QuestionEntity> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with id " + questionId + " not found.");
        }
        answer.setQuestion(question.get());
        return answer;
    }
}
