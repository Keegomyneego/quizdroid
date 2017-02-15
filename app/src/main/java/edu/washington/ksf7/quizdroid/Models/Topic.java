package edu.washington.ksf7.quizdroid.Models;

/**
 * Created by keegomyneego on 2/15/17.
 */

public class Topic {

    public final String title;
    public final String shortDescription;
    public final String longDescription;
    public final Question[] questions;

    public Topic(String title, String shortDescription, String longDescription, Question[] questions) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.questions = questions;
    }
}
