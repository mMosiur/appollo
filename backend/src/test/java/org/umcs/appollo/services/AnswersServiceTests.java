package org.umcs.appollo.services;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.umcs.appollo.converters.AnswerConverter;
import org.umcs.appollo.converters.QuestionConverter;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.AnswerDetails;
import org.umcs.appollo.model.api.PollDetails;
import org.umcs.appollo.model.api.QuestionDetails;
import org.umcs.appollo.repository.AnswerRepository;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class AnswersServiceTests {
    @InjectMocks
    AnswersService answersService;
    @Mock
    private PollRepository pollRepository;
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private AnswerConverter answerConverter;
    @Mock
    private QuestionRepository questionRepository;

    private final Gson gson = new Gson();
    private PollEntity pollEntity;
    private PollDetails pollDetails;
    private QuestionEntity questionEntity;
    private AnswerEntity answerEntity;

    private QuestionConverter questionConverter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        answerConverter = new AnswerConverter();
        answersService = new AnswersService(pollRepository, answerRepository, answerConverter, questionRepository);

        questionConverter = new QuestionConverter();

        pollEntity = new PollEntity();
        pollEntity.setId(1);
        pollEntity.setName("test");

        pollDetails = new PollDetails();
        pollDetails.setId(1);
        pollDetails.setName("test");

        questionEntity = new QuestionEntity();
        questionEntity.setId(1);
        questionEntity.setText("What's your favorite color?");
        questionEntity.setType("text");
        questionEntity.setOptions(gson.toJson(new String[] {
                "Red", "Green", "Blue"
        }));

        answerEntity = new AnswerEntity();
        answerEntity.setId(1);
        answerEntity.setAnswerJson("Blue");

        List<QuestionEntity> questionEntitiesList = new LinkedList<>();
        questionEntitiesList.add(questionEntity);
        List<QuestionDetails> questionDetailsList = new LinkedList<>();
        questionDetailsList.add(questionConverter.FromEntityToApi(questionEntity));
        List<AnswerEntity> answerEntitiesList = new LinkedList<>();
        answerEntitiesList.add(answerEntity);

        pollEntity.setQuestions(questionEntitiesList);
        pollDetails.setQuestions(questionDetailsList);
        questionEntity.setAnswers(answerEntitiesList);
        questionEntity.setPoll(pollEntity);
        answerEntity.setQuestion(questionEntity);
    }

    @Test
    public void getListAnswerSuccess(){
        given(pollRepository.findById(1)).willReturn(Optional.of(pollEntity));

        List<AnswerDetails> answers = answersService.getAnswersForPoll(1);

        assertThat(answers).isNotEmpty();
    }

    @Test
    public void addAnswerSuccess(){
        List<AnswerDetails> answers = new ArrayList<>();
        AnswerDetails answerDetails = answerConverter.FromEntityToApi(answerEntity);
        answers.add(answerDetails);

        List<AnswerEntity> answersEntities = new ArrayList<>();
        answersEntities.add(answerEntity);

        given(pollRepository.findById(1)).willReturn(Optional.of(pollEntity));
        given(questionRepository.findById(1)).willReturn(Optional.of(questionEntity));
        when(answerRepository.saveAll(any(List.class))).thenReturn(answersEntities);

        List<AnswerDetails> answerDetailsTest = answersService.addPollAnswers(1, answers);

        assertThat(answerDetailsTest).isNotEmpty();
        assertThat(answerDetailsTest.get(0).getId()).isEqualTo(1);
        assertThat(answerDetailsTest.get(0).getQuestionId()).isEqualTo(1);
        assertThat(answerDetailsTest.get(0).getAnswerJson()).isEqualTo("Blue");
    }
}
