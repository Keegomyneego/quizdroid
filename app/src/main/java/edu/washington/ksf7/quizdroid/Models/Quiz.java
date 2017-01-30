package edu.washington.ksf7.quizdroid.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by keegomyneego on 1/29/17.
 */

public class Quiz {

    String topic;
    List<MultipleChoiceQuestion> questions;

    public Quiz(String topic, MultipleChoiceQuestion[] questions) {
        this.topic = topic;
        this.questions = new ArrayList<>(Arrays.asList(questions));
    }

    public String getTopic() {
        return topic;
    }

    public List<MultipleChoiceQuestion> getQuestions() {
        return questions;
    }
}
