package org.umcs.appollo.services;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.umcs.appollo.converters.AnswerConverter;
import org.umcs.appollo.converters.QuestionConverter;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.Answer;
import org.umcs.appollo.model.api.Poll;
import org.umcs.appollo.model.api.Question;
import org.umcs.appollo.repository.AnswerRepository;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;
import org.umcs.appollo.repository.UserRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;

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
    @Mock
    private UserRepository userRepository;

    private final Gson gson = new Gson();
    private PollEntity pollEntity;
    private Poll pollDetails;
    private QuestionEntity questionEntity;
    private AnswerEntity answerEntity;
    private UserEntity userEntity;

    private QuestionConverter questionConverter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        answerConverter = new AnswerConverter();
        answersService = new AnswersService(pollRepository, answerRepository, answerConverter, questionRepository,userRepository);

        questionConverter = new QuestionConverter();

        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("user123");
        userEntity.setPassword("pass123");
        userEntity.setEmail("test@test.com");
        userEntity.setFirstName("Vuko");
        userEntity.setLastName("Drakkainen");

        pollEntity = new PollEntity();
        pollEntity.setId(1);
        pollEntity.setName("test");
        pollEntity.setUser(userEntity);

        pollDetails = new Poll();
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
        answerEntity.setUser(userEntity);

        List<QuestionEntity> questionEntitiesList = new LinkedList<>();
        questionEntitiesList.add(questionEntity);
        List<Question> questionDetailsList = new LinkedList<>();
        questionDetailsList.add(questionConverter.FromEntityToApi(questionEntity));
        List<AnswerEntity> answerEntitiesList = new LinkedList<>();
        answerEntitiesList.add(answerEntity);

        pollDetails.setQuestions(questionDetailsList);
        questionEntity.setAnswers(answerEntitiesList);
        questionEntity.setPoll(pollEntity);
        answerEntity.setQuestion(questionEntity);
    }

    @Test
    public void getListAnswerSuccess(){
        given(pollRepository.findById(1)).willReturn(Optional.of(pollEntity));

        List<Answer> answers = answersService.getAnswersForPoll(1);

        assertThat(answers).isNotEmpty();
    }

    @Test
    public void addAnswerSuccess(){
        List<Answer> answers = new ArrayList<>();
        Answer answerDetails = answerConverter.FromEntityToApi(answerEntity);
        answers.add(answerDetails);

        List<AnswerEntity> answersEntities = new ArrayList<>();
        answersEntities.add(answerEntity);

        given(pollRepository.findById(1)).willReturn(Optional.of(pollEntity));
        given(questionRepository.findById(1)).willReturn(Optional.of(questionEntity));
        given(userRepository.existsById(1)).willReturn(true);
        given(userRepository.getById(1)).willReturn(userEntity);
        when(answerRepository.saveAll(any(List.class))).thenReturn(answersEntities);

        List<Answer> answerDetailsTest = answersService.addPollAnswers(1, answers,1);

        assertThat(answerDetailsTest).isNotEmpty();
        assertThat(answerDetailsTest.get(0).getId()).isEqualTo(1);
        assertThat(answerDetailsTest.get(0).getQuestionId()).isEqualTo(1);
        assertThat(answerDetailsTest.get(0).getAnswerJson()).isEqualTo("Blue");
    }
}
