package org.umcs.appollo.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.umcs.appollo.converters.PollConverter;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.repository.PollRepository;
import org.umcs.appollo.repository.QuestionRepository;

import java.util.List;

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

    @Test
    public void getPollsCorrect(){
        PollEntity poll = new PollEntity();
        poll.setId(1);
        poll.setName("test");

        given(pollRepository.findAll()).willReturn(List.of(poll));

        List<PollLabel> pollList = pollService.getPolls();

        assertThat(pollList).isNotNull();
//        Mockito.when(pollService.getPolls()).thenReturn(List.of(pollLabel));
    }
}
