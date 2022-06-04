package org.umcs.appollo.services;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.umcs.appollo.converters.PollConverter;
import org.umcs.appollo.converters.QuestionConverter;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.PollDetails;
import org.umcs.appollo.model.api.PollLabel;
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
    private QuestionRepository questionRepository;
    @InjectMocks
    PollService pollService;

    private final Gson gson = new Gson();
    private PollEntity pollEntity;
    private PollDetails pollDetails;
    private QuestionEntity questionEntity;

    @Before
    public void setup(){
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

        pollEntity.setQuestions(questionEntitiesList);
//        pollDetails = pollConverter.FromEntityToApiDetailed(pollEntity);
//        System.out.println(pollDetails);
    }

    @Test
    public void getPollsCorrect(){
        given(pollRepository.findAll()).willReturn(List.of(pollEntity));

        List<PollLabel> pollList = pollService.getPolls();

        assertThat(pollList).isNotNull();
//        Mockito.when(pollService.getPolls()).thenReturn(List.of(pollLabel));
    }

    @Test
    public void getPollCorrect(){
        given(pollRepository.findById(1)).willReturn(Optional.of(pollEntity));
        given(pollConverter.FromEntityToApiDetailed(pollEntity)).willReturn(pollDetails);

        PollDetails pollDetailsTest = pollService.getPoll(1);

        assertThat(pollDetails).isNotNull();
        assertEquals(pollDetailsTest.getId(), 1);
    }

    @Test
    public void createPollCorrect(){
        given(pollConverter.FromApiToEntity(pollDetails)).willReturn(pollEntity);
        given(pollRepository.save(pollEntity)).willReturn(pollEntity);
        given(questionRepository.saveAll(pollEntity.getQuestions())).willReturn(pollEntity.getQuestions());

        PollEntity pollEntityTest = pollService.createPoll(pollDetails);

        assertThat(pollEntityTest).isNotNull();
        assertThat(pollEntityTest.getQuestions()).isNotNull();
    }
}
