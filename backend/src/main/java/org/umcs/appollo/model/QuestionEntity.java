package org.umcs.appollo.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "options", nullable = false)
    private String options;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="poll_id", nullable = false)
    private PollEntity poll;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerEntity> answers;

    public QuestionEntity() {

    }

    public QuestionEntity(String text, String type, String options) {
        this.text = text;
        this.type = type;
        this.options = options;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }
}
