package org.umcs.appollo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.converters.AnswerConverter;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.AnswerDetails;
import org.umcs.appollo.repository.AnswerRepository;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;

@Service
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

    public List<AnswerDetails> getAnswersForPoll(Integer pollId) {
        Optional<PollEntity> result = pollRepository.findById(pollId);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + pollId + " not found.");
        }
        List<AnswerDetails> answers = new ArrayList<AnswerDetails>();
        for(QuestionEntity question : result.get().getQuestions()) {
            for(AnswerEntity answer : question.getAnswers()) {
                answers.add(answerConverter.FromEntityToApi(answer));
            }
        }
        return answers;
    }

    public List<AnswerDetails> addPollAnswers(Integer id, List<AnswerDetails> answers) {
        Optional<PollEntity> result = pollRepository.findById(id);
        List<AnswerEntity> answersEntities;
        try {
            if (result.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll with id " + id + " not found.");
            }
            answersEntities = answers
                .stream()
                .map(q -> enrichAnswerWithQuestionInfo(q))
                .collect(Collectors.toList());
            answersEntities = answerRepository.saveAll(answersEntities);
        } catch (ResponseStatusException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return answersEntities.stream().map(a -> answerConverter.FromEntityToApi(a)).collect(Collectors.toList());
    }

    // Is this really needed? Isn't ID enough? To do: test it after controller is in
    // place.
    private AnswerEntity enrichAnswerWithQuestionInfo(AnswerDetails a) {
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
