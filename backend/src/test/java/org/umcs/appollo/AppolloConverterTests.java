package org.umcs.appollo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import org.umcs.appollo.model.api.Answer;
import org.umcs.appollo.model.api.Poll;
import org.umcs.appollo.model.api.PollLabel;
import org.umcs.appollo.model.api.Question;

import java.util.ArrayList;
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
    private Poll poll;
    private final String pollName = "Example poll.";

    private QuestionEntity questionEntity;
    private Question question;
    private final String questionText = "What's your favorite color?", questionType = "text";
    private final String questionOptions = gson.toJson(new String[]{
            "Red", "Green", "Blue"
    });

    private AnswerEntity answerEntity;
    private Answer answer;
    private final String answerJson = gson.toJson("Blue");

    @Before
    public void setup() {
        questionConverter = new QuestionConverter();
        answerConverter = new AnswerConverter();
        pollConverter = new PollConverter(questionConverter);

        answerEntity = new AnswerEntity();
        questionEntity = new QuestionEntity();
        pollEntity = new PollEntity();

        answer = new Answer();
        question = new Question();
        poll = new Poll();

        user1 = new UserEntity();
        user2 = new UserEntity();

        // Model a poll
        pollEntity.setId(pollId);
        pollEntity.setName(pollName);
        pollEntity.setUser(user1);
        poll.setId(pollId);
        poll.setName(pollName);
        // TODO: no method?
//        pollDetails.setUser(user1);

        // Model a question
        questionEntity.setId(questionId);
        questionEntity.setText(questionText);
        questionEntity.setOptions(questionOptions);
        questionEntity.setType(questionType);
        question.setId(questionId);
        question.setText(questionText);
        question.setOptions(gson.fromJson(questionOptions, new TypeToken<ArrayList<String>>() {
        }.getType()));
        question.setType(Question.TypeEnum.TEXT);

        // Model an answer
        answerEntity.setId(answerId);
        answerEntity.setUser(user2);
        answerEntity.setAnswerJson(answerJson);
        answer.setId(answerId);
        answer.setAnswerJson(answerJson);

        // Connect relations
        List<QuestionEntity> questionEntitiesList = new LinkedList<>();
        questionEntitiesList.add(questionEntity);
        List<Question> questionDetailsList = new LinkedList<>();
        questionDetailsList.add(question);
        List<AnswerEntity> answerEntitiesList = new LinkedList<>();
        answerEntitiesList.add(answerEntity);
        List<Answer> answerDetailsList = new LinkedList<>();
        answerDetailsList.add(answer);

        pollEntity.setQuestions(questionEntitiesList);
        poll.setQuestions(questionDetailsList);
        questionEntity.setPoll(pollEntity);
        questionEntity.setAnswers(answerEntitiesList);
        answerEntity.setQuestion(questionEntity);
        answer.setQuestionId(questionId);
    }

    @Test
    public void ConvertPollFromEntityToApi() {
        Assert.assertNull(pollConverter.FromEntityToApi(null));
        PollLabel pollLabel = pollConverter.FromEntityToApi(pollEntity);
        Assert.assertEquals(pollLabel.getId().intValue(), pollId);
        Assert.assertEquals(pollLabel.getName(), pollName);
    }

    @Test
    public void ConvertQuestionFromEntityToApi() {
        Assert.assertNull(questionConverter.FromEntityToApi(null));
        Question question = questionConverter.FromEntityToApi(questionEntity);
        Assert.assertEquals(question.getId().intValue(), questionId);
        Assert.assertEquals(gson.toJson(question.getOptions()), questionOptions);
        Assert.assertEquals(question.getText(), questionText);
        Assert.assertEquals(question.getType().toString(), questionType);
    }

    @Test
    public void ConvertPollFromEntityToApiDetailed() {
        Assert.assertNull(pollConverter.FromEntityToApiDetailed(null));
        Poll poll = pollConverter.FromEntityToApiDetailed(pollEntity);
        Assert.assertEquals(poll.getId().intValue(), pollId);
        Assert.assertEquals(poll.getName(), pollName);
        Assert.assertEquals(poll.getIsActive(), true);
        Assert.assertEquals(poll.getQuestions().size(), 1);
        Assert.assertEquals(poll.getQuestions().get(0), question);
    }

    @Test
    public void ConvertAnswerFromEntityToApi() {
        Assert.assertNull(answerConverter.FromEntityToApi(null));
        Answer answer = answerConverter.FromEntityToApi(answerEntity);
        Assert.assertEquals(answer.getId().intValue(), answerId);
        Assert.assertEquals(answer.getQuestionId().intValue(), questionId);
        Assert.assertEquals(answer.getAnswerJson(), answerJson);
    }

    @Test
    public void ConvertPollFromApiToEntity() {
        Assert.assertNull(questionConverter.FromApiToEntity(null));
        PollEntity pollEntity = pollConverter.FromApiToEntity(poll);
        Assert.assertEquals(pollEntity.getId().intValue(), pollId);
        Assert.assertEquals(pollEntity.getName(), pollName);
//        Assert.assertEquals(pollEntity.getUser(), user1);
        Assert.assertEquals(pollEntity.getQuestions().size(), 1);
//        Assert.assertEquals(pollEntity.getQuestions().get(0), questionEntity);
    }

    @Test
    public void ConvertQuestionFromApiToEntity() {
        Assert.assertNull(questionConverter.FromEntityToApi(null));
        QuestionEntity questionEntity = questionConverter.FromApiToEntity(question);
        Assert.assertEquals(questionEntity.getId().intValue(), questionId);
        Assert.assertEquals(questionEntity.getOptions(), questionOptions);
        Assert.assertEquals(questionEntity.getText(), questionText);
        Assert.assertEquals(questionEntity.getType(), questionType);
//        Assert.assertEquals(questionEntity.getAnswers().size(), 1);
//        Assert.assertEquals(questionEntity.getAnswers().get(0), answerEntity);
//        Assert.assertEquals(questionEntity.getPoll(), pollEntity);
    }

    @Test
    public void ConvertAnswerFromApiToEntity() {
        Assert.assertNull(answerConverter.FromApiToEntity(null));
        AnswerEntity answerEntity = answerConverter.FromApiToEntity(answer);
        Assert.assertEquals(answerEntity.getId().intValue(), answerId);
//        Assert.assertEquals(answerEntity.getQuestion(), questionEntity);
        Assert.assertEquals(answerEntity.getAnswerJson(), answerJson);
//        Assert.assertEquals(answerEntity.getUser(), user2);
    }
}
