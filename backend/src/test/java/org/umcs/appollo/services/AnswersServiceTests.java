package org.umcs.appollo.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.umcs.appollo.converters.AnswerConverter;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.AnswerDetails;
import org.umcs.appollo.repository.AnswerRepository;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AnswersServiceTests {
    AnswersService answersService;

    @Mock
    private PollRepository pollRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AnswerConverter answerConverter;

    @Mock
    private QuestionRepository questionRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        answersService = new AnswersService(pollRepository, answerRepository, answerConverter, questionRepository);
    }

    @Test
    public void addAnswerSuccess(){

    }

    @Test
    public void getListAnswerSuccess(){
        // given
        AnswerEntity answer = new AnswerEntity();
        answer.setId(1);
        answer.setAnswerJson("Blue");

        QuestionEntity question = new QuestionEntity();
        question.setId(1);
        question.setText("What is your favorite color?");
        question.setType("radio");
        question.setOptions("tst");
        question.setAnswers(new ArrayList<>(Arrays.asList(answer)));

        PollEntity pollEntity = new PollEntity();
        pollEntity.setId(1);
        pollEntity.setName("test");
        pollEntity.setQuestions(new ArrayList<>(Arrays.asList(question)));
//        pollEntity.setUser(null);

        given(pollRepository.findById(1)).willReturn(Optional.of(pollEntity));

        // when
        List<AnswerDetails> answers = answersService.getAnswersForPoll(1);

        // then
        assertThat(answers).isNotNull();
    }


}
