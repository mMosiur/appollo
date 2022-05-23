package org.umcs.appollo.converters;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.umcs.appollo.model.QuestionEntity;
import org.umcs.appollo.model.api.QuestionDetails;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class QuestionConverter {
    private final Gson gson = new Gson();
    private final Type stringListType = new TypeToken<List<String>>(){}.getType();

    public QuestionDetails FromEntityToApi(QuestionEntity questionEntity) {
        if (questionEntity == null)
            return null;
            QuestionDetails question = new QuestionDetails();
        question.setId(questionEntity.getId());
        question.setText(questionEntity.getText());
        QuestionDetails.TypeEnum type = QuestionDetails.TypeEnum.valueOf(questionEntity.getType().toUpperCase());
        question.setType(type);
        List<String> options = gson.fromJson(
            questionEntity.getOptions(),
            stringListType
        );
        question.setOptions(options);
        return question;
    }

    public QuestionEntity FromApiToEntity(QuestionDetails question) {
        if (question == null)
            return null;
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(question.getId());
        questionEntity.setText(question.getText());
        questionEntity.setType(question.getType().toString());
        questionEntity.setOptions(
            gson.toJson(question.getOptions())
        );
        return questionEntity;
    }

}
