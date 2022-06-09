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
import org.umcs.appollo.converters.UserConverter;
import org.umcs.appollo.model.AnswerEntity;
import org.umcs.appollo.model.PollEntity;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class AppolloConverterTests {
    private final Gson gson = new Gson();

    private QuestionConverter questionConverter;
    private AnswerConverter answerConverter;
    private PollConverter pollConverter;
    private UserConverter userConverter;

    private final int pollId = 0, questionId = 1, answerId = 2, user1Id = 3;

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

    private UserEntity user1Entity, user2Entity;
    private User user1;
    private final String username1 = "user1";
    private final String password1 = "p1";
    private final String email1 = "hehe@yo.com";
    private final String firstName1 = "Man";
    private final String lastName1 = "Spider";

    @Before
    public void setup() {
        questionConverter = new QuestionConverter();
        answerConverter = new AnswerConverter();
        pollConverter = new PollConverter(questionConverter);
        userConverter = new UserConverter();

        answerEntity = new AnswerEntity();
        questionEntity = new QuestionEntity();
        pollEntity = new PollEntity();

        answer = new Answer();
        question = new Question();
        poll = new Poll();

        // Model users
        user1Entity = new UserEntity();
        user1Entity.setId(user1Id);
        user1Entity.setUsername(username1);
        user1Entity.setPassword(password1);
        user1Entity.setEmail(email1);
        user1Entity.setFirstName(firstName1);
        user1Entity.setLastName(lastName1);
        user1 = new User();
        user1.setId(user1Id);
        user1.setUsername(username1);
        user1.setPassword(password1);
        user1.setEmail(email1);
        user1.setFirstname(firstName1);
        user1.setLastname(lastName1);

        user2Entity = new UserEntity();

        // Model a poll
        pollEntity.setId(pollId);
        pollEntity.setName(pollName);
        pollEntity.setUser(user1Entity);
        poll.setId(pollId);
        poll.setName(pollName);

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
        answerEntity.setUser(user2Entity);
        answerEntity.setAnswerJson(answerJson);
        answer.setId(answerId);
        answer.setAnswerJson(answerJson);

        // Connect relations
        List<Question> questionDetailsList = new LinkedList<>();
        questionDetailsList.add(question);
        List<AnswerEntity> answerEntitiesList = new LinkedList<>();
        answerEntitiesList.add(answerEntity);

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
    }

    @Test
    public void ConvertQuestionFromApiToEntity() {
        Assert.assertNull(questionConverter.FromEntityToApi(null));
        QuestionEntity questionEntity = questionConverter.FromApiToEntity(question);
        Assert.assertEquals(questionEntity.getId().intValue(), questionId);
        Assert.assertEquals(questionEntity.getOptions(), questionOptions);
        Assert.assertEquals(questionEntity.getText(), questionText);
        Assert.assertEquals(questionEntity.getType(), questionType);
    }

    @Test
    public void ConvertAnswerFromApiToEntity() {
        Assert.assertNull(answerConverter.FromApiToEntity(null));
        AnswerEntity answerEntity = answerConverter.FromApiToEntity(answer);
        Assert.assertEquals(answerEntity.getId().intValue(), answerId);
        Assert.assertEquals(answerEntity.getAnswerJson(), answerJson);
    }

    @Test
    public void ConvertUserFromApiToEntity() {
        Assert.assertNull(userConverter.FromApiToEntity(null));
        UserEntity userEntity = userConverter.FromApiToEntity(user1);
        Assert.assertEquals(userEntity.getId().intValue(), user1Id);
        Assert.assertEquals(userEntity.getUsername(), username1);
        Assert.assertEquals(userEntity.getPassword(), password1);
        Assert.assertEquals(userEntity.getEmail(), email1);
        Assert.assertEquals(userEntity.getFirstName(), firstName1);
        Assert.assertEquals(userEntity.getLastName(), lastName1);
    }

    @Test
    public void ConvertUserFromEntityToApi() {
        Assert.assertNull(userConverter.FromEntityToApi(null));
        User user = userConverter.FromEntityToApi(user1Entity);
        Assert.assertEquals(user.getId().intValue(), user1Id);
        Assert.assertEquals(user.getUsername(), username1);
        Assert.assertEquals(user.getPassword(), password1);
        Assert.assertEquals(user.getEmail(), email1);
        Assert.assertEquals(user.getFirstname(), firstName1);
        Assert.assertEquals(user.getLastname(), lastName1);
    }
}
