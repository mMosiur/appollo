package org.umcs.appollo;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.umcs.appollo.converters.AnswerConverter;
import org.umcs.appollo.converters.PollConverter;
import org.umcs.appollo.converters.QuestionConverter;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.AnswerDetails;
import org.umcs.appollo.model.api.QuestionDetails;
import org.umcs.appollo.model.api.PollDetails;
import org.umcs.appollo.model.api.PollLabel;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class AppolloConverterTests {
    private final Gson gson = new Gson();

    private QuestionConverter questionConverter;
    private AnswerConverter answerConverter;
    private PollConverter pollConverter;

    private UserEntity user1, user2;
    private final int pollId = 0, questionId = 1, answerId = 2;

    private PollEntity pollEntity;
    private PollLabel pollLabel;
    private PollDetails pollDetails;
    private final String pollName = "Example poll.";

    private QuestionEntity questionEntity;
    private QuestionDetails questionDetails;
    private final String questionText = "What's your favorite color?", questionType = "text";
    private final String questionOptions = gson.toJson(new String[]{
            "Red", "Green", "Blue"
    });

    private AnswerEntity answerEntity;
    private AnswerDetails answerDetails;
    private final String answerJson = gson.toJson("Blue");

    @Before
    public void setup() {
        questionConverter = new QuestionConverter();
        answerConverter = new AnswerConverter();
        pollConverter = new PollConverter(questionConverter);

        answerEntity = new AnswerEntity();
        questionEntity = new QuestionEntity();
        pollEntity = new PollEntity();

        user1 = new UserEntity();
        user2 = new UserEntity();

        // Model a poll
        pollEntity.setId(pollId);
        pollEntity.setName(pollName);
        pollEntity.setUser(user1);

        // Model a question
        questionEntity.setId(questionId);
        questionEntity.setText(questionText);
        questionEntity.setOptions(questionOptions);
        questionEntity.setType(questionType);

        // Model an answer
        answerEntity.setId(answerId);
        answerEntity.setUser(user2);
        answerEntity.setAnswerJson(answerJson);

        // Connect relations
        List<QuestionEntity> questions = new LinkedList<>();
        questions.add(questionEntity);
        List<AnswerEntity> answers = new LinkedList<>();
        answers.add(answerEntity);

        pollEntity.setQuestions(questions);
        questionEntity.setPoll(pollEntity);
        questionEntity.setAnswers(answers);
        answerEntity.setQuestion(questionEntity);
    }

    @Test
    public void ConvertPollFromEntityToApi() {
        Assert.assertNull(pollConverter.FromEntityToApi(null));
        pollLabel = pollConverter.FromEntityToApi(pollEntity);
        Assert.assertEquals(pollLabel.getId().intValue(), pollId);
        Assert.assertEquals(pollLabel.getName(), pollName);
    }

    @Test
    public void ConvertQuestionFromEntityToApi() {
        Assert.assertNull(questionConverter.FromEntityToApi(null));
        questionDetails = questionConverter.FromEntityToApi(questionEntity);
        Assert.assertEquals(questionDetails.getId().intValue(), questionId);
        Assert.assertEquals(gson.toJson(questionDetails.getOptions()), questionOptions);
        Assert.assertEquals(questionDetails.getText(), questionText);
        Assert.assertEquals(questionDetails.getType().toString(), questionType);
    }

    @Test
    public void ConvertPollFromEntityToApiDetailed() {
        Assert.assertNull(pollConverter.FromEntityToApiDetailed(null));
        pollDetails = pollConverter.FromEntityToApiDetailed(pollEntity);
        Assert.assertEquals(pollDetails.getId().intValue(), pollId);
        Assert.assertEquals(pollDetails.getName(), pollName);
        Assert.assertEquals(pollDetails.getIsActive(), true);
        Assert.assertEquals(pollDetails.getQuestions().size(), 1);
        Assert.assertEquals(pollDetails.getQuestions().get(0), questionDetails);
    }

    @Test
    public void ConvertAnswerFromEntityToApi() {
        Assert.assertNull(answerConverter.FromEntityToApi(null));
        answerDetails = answerConverter.FromEntityToApi(answerEntity);
        Assert.assertEquals(answerDetails.getId().intValue(), answerId);
        Assert.assertEquals(answerDetails.getQuestionId().intValue(), questionId);
        Assert.assertEquals(answerDetails.getAnswerJson(), answerJson);
    }

    @Test
    public void ConvertPollFromApiToEntity() {
        Assert.assertNull(questionConverter.FromApiToEntity(null));
        pollEntity = pollConverter.FromApiToEntity(pollDetails);
        Assert.assertEquals(pollEntity.getId().intValue(), pollId);
        Assert.assertEquals(pollEntity.getName(), pollName);
        Assert.assertEquals(pollEntity.getUser(), user1);
        Assert.assertEquals(pollEntity.getQuestions().size(), 1);
        Assert.assertEquals(pollEntity.getQuestions().get(0), questionEntity);
    }

    @Test
    public void ConvertQuestionFromApiToEntity() {
        Assert.assertNull(questionConverter.FromEntityToApi(null));
        questionEntity = questionConverter.FromApiToEntity(questionDetails);
        Assert.assertEquals(questionEntity.getId().intValue(), questionId);
        Assert.assertEquals(questionEntity.getOptions(), questionOptions);
        Assert.assertEquals(questionEntity.getText(), questionText);
        Assert.assertEquals(questionEntity.getType(), questionType);
        Assert.assertEquals(questionEntity.getAnswers().size(), 1);
        Assert.assertEquals(questionEntity.getAnswers().get(0), answerEntity);
        Assert.assertEquals(questionEntity.getPoll(), pollEntity);
    }

    @Test
    public void ConvertAnswerFromApiToEntity() {
        Assert.assertNull(answerConverter.FromApiToEntity(null));
        answerEntity = answerConverter.FromApiToEntity(answerDetails);
        Assert.assertEquals(answerEntity.getId().intValue(), answerId);
        Assert.assertEquals(answerEntity.getQuestion(), questionEntity);
        Assert.assertEquals(answerEntity.getAnswerJson(), answerJson);
        Assert.assertEquals(answerEntity.getUser(), user2);
    }
}
