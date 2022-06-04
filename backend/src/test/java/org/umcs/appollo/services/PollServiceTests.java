package org.umcs.appollo.services;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.umcs.appollo.converters.PollConverter;
import org.umcs.appollo.converters.QuestionConverter;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.PollDetails;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.model.api.QuestionDetails;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PollServiceTests {
    @Mock
    private PollRepository pollRepository;
    @Mock
    private PollConverter pollConverter;
    @Mock
    private QuestionConverter questionConverter;
    @Mock
    private QuestionRepository questionRepository;
    @InjectMocks
    PollService pollService;

    private final Gson gson = new Gson();
    private PollEntity pollEntity;
    private PollDetails pollDetails;

    private QuestionEntity questionEntity;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        questionConverter = new QuestionConverter();
        pollConverter = new PollConverter(questionConverter);
        pollService = new PollService(pollRepository, pollConverter, questionRepository);

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

        List<QuestionEntity> questionEntitiesList = new LinkedList<>();
        questionEntitiesList.add(questionEntity);
        List<QuestionDetails> questionDetailsList = new LinkedList<>();
        questionDetailsList.add(questionConverter.FromEntityToApi(questionEntity));

        pollEntity.setQuestions(questionEntitiesList);
        pollDetails.setQuestions(questionDetailsList);
    }

    @Test
    public void getPollsCorrect(){
        given(pollRepository.findAll()).willReturn(List.of(pollEntity));

        List<PollLabel> pollList = pollService.getPolls();

        assertThat(pollList).isNotEmpty();
    }

    @Test
    public void getPollCorrect(){
        given(pollRepository.findById(1)).willReturn(Optional.of(pollEntity));

        PollDetails pollDetailsTest = pollService.getPoll(1);

        assertThat(pollDetails).isNotNull();
        assertEquals(pollDetailsTest.getId(), 1);
    }

    @Test
    public void createPollCorrect(){
        when(pollRepository.save(any(PollEntity.class))).thenReturn(pollEntity);
        when(questionRepository.saveAll(pollEntity.getQuestions())).thenReturn(pollEntity.getQuestions());

        PollEntity pollEntityTest = pollService.createPoll(pollDetails);

        assertThat(pollEntityTest).isNotNull();
        assertThat(pollEntityTest.getQuestions()).isNotEmpty();
        assertThat(pollEntityTest.getId()).isEqualTo(1);
        assertThat(pollEntityTest.getName()).isEqualTo("test");
    }

    @Test
    public void deletePollCorrect(){
        given(pollRepository.getById(1)).willReturn(pollEntity);

        pollService.deletePoll(1);

        verify(pollRepository, times(1)).delete(pollEntity);
    }

    @Test
    public void updatePollCorrect(){
        given(pollRepository.getById(1)).willReturn(pollEntity);
        pollDetails.setName("test123");

        when(pollRepository.save(any(PollEntity.class))).thenReturn(pollConverter.FromApiToEntity(pollDetails));
        when(questionRepository.saveAll(pollEntity.getQuestions())).thenReturn(pollEntity.getQuestions());

        PollDetails pollDetailsTest = pollService.updatePoll(1, pollDetails);

        assertThat(pollDetailsTest.getName()).isEqualTo("test123");
    }
}
