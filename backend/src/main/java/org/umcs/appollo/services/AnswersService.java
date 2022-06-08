package org.umcs.appollo.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.umcs.appollo.converters.AnswerConverter;
import org.umcs.appollo.exceptions.ConflictException;
import org.umcs.appollo.exceptions.ResourceNotFoundException;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.Answer;
import org.umcs.appollo.repository.AnswerRepository;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;
import org.umcs.appollo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswersService {
    private final PollRepository pollRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final AnswerConverter answerConverter;
    private final QuestionRepository questionRepository;

    public AnswersService(PollRepository pollRepository, AnswerRepository answerRepository,
            AnswerConverter answerConverter, QuestionRepository questionRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.answerRepository = answerRepository;
        this.answerConverter = answerConverter;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public List<Answer> getAnswersForPoll(Integer pollId) {
        Optional<PollEntity> result = pollRepository.findById(pollId);
        if (result.isEmpty())
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found.");

        List<Answer> answers = new ArrayList<Answer>();

        for(QuestionEntity question : result.get().getQuestions())
            for(AnswerEntity answer : question.getAnswers())
                answers.add(answerConverter.FromEntityToApi(answer));


        return answers;
    }

    public List<Answer> addPollAnswers(Integer id, List<Answer> answers, Integer user_id) {
        Optional<PollEntity> result = pollRepository.findById(id);
        List<AnswerEntity> answersEntities;
            if (result.isEmpty())
                throw new ResourceNotFoundException( "No poll of id " + id + " found.");
            answersEntities = answers
                .stream()
                .map(q -> {
                    int questionId = q.getQuestionId();
                    if (!questionRepository.existsById(questionId))
                        throw new ResourceNotFoundException(
                                "Cannot post answer to question of id " + questionId + " because it doesn't exist.");

                    QuestionEntity targetQuestion = questionRepository.getById(q.getQuestionId());
                    if (targetQuestion.getPoll().getId() != id.intValue())
                        throw new ConflictException(
                                "Question of id " + questionId + " does not belong to poll of id " + id + "."
                        );
                    return enrichAnswerWithQuestionInfo(q, user_id);
                })
                .collect(Collectors.toList());
            answersEntities = answerRepository.saveAll(answersEntities);
        return answersEntities.stream().map(answerConverter::FromEntityToApi).collect(Collectors.toList());
    }


    private AnswerEntity enrichAnswerWithQuestionInfo(Answer a, Integer userId) {
        AnswerEntity answer = answerConverter.FromApiToEntity(a);
        answer.setId(null);
        Integer questionId = a.getQuestionId();
        Optional<QuestionEntity> question = questionRepository.findById(questionId);
        answer.setQuestion(question.get());

        if (!userRepository.existsById(userId))
            throw new ResourceNotFoundException(
                    "Cannot add answer for user of id " + userId + " because user does not exist.");
        answer.setUser(userRepository.getById(userId));

        return answer;
    }
}
