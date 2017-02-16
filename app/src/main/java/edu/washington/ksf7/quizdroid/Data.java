package edu.washington.ksf7.quizdroid;

import java.util.ArrayList;
import java.util.List;

import edu.washington.ksf7.quizdroid.Models.Question;
import edu.washington.ksf7.quizdroid.Models.Quiz;

/**
 * Created by keegomyneego on 1/30/17.
 */

public class Data {

    public static final String questionsJSON = "" +
            "[\n" +
            "    { \"title\":\"Science!\",\n" +
            "      \"desc\":\"Because SCIENCE!\",\n" +
            "      \"questions\":[\n" +
            "        {\n" +
            "          \"text\":\"What is fire?\",\n" +
            "          \"answer\":\"1\",\n" +
            "          \"answers\":[\n" +
            "            \"One of the four classical elements\",\n" +
            "            \"A magical reaction given to us by God\",\n" +
            "            \"A band that hasn't yet been discovered\",\n" +
            "            \"Fire! Fire! Fire! heh-heh\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    { \"title\":\"Marvel Super Heroes\", \"desc\": \"Avengers, Assemble!\",\n" +
            "      \"questions\":[\n" +
            "        {\n" +
            "          \"text\":\"Who is Iron Man?\",\n" +
            "          \"answer\":\"1\",\n" +
            "          \"answers\":[\n" +
            "            \"Tony Stark\",\n" +
            "            \"Obadiah Stane\",\n" +
            "            \"A rock hit by Megadeth\",\n" +
            "            \"Nobody knows\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"text\":\"Who founded the X-Men?\",\n" +
            "          \"answer\":\"2\",\n" +
            "          \"answers\":[\n" +
            "            \"Tony Stark\",\n" +
            "            \"Professor X\",\n" +
            "            \"The X-Institute\",\n" +
            "            \"Erik Lensherr\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"text\":\"How did Spider-Man get his powers?\",\n" +
            "          \"answer\":\"1\",\n" +
            "          \"answers\":[\n" +
            "            \"He was bitten by a radioactive spider\",\n" +
            "            \"He ate a radioactive spider\",\n" +
            "            \"He is a radioactive spider\",\n" +
            "            \"He looked at a radioactive spider\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    { \"title\":\"Mathematics\", \"desc\":\"Did you pass the third grade?\",\n" +
            "      \"questions\":[\n" +
            "         {\n" +
            "           \"text\":\"What is 2+2?\",\n" +
            "           \"answer\":\"1\",\n" +
            "           \"answers\":[\n" +
            "             \"4\",\n" +
            "             \"22\",\n" +
            "             \"An irrational number\",\n" +
            "             \"Nobody knows\"\n" +
            "           ]\n" +
            "         }\n" +
            "      ]\n" +
            "   }\n" +
            "]";

    private static List<Quiz> quizzes;
    private static List<String> quizTopics;

    public static void loadQuizzes() {
        quizzes = new ArrayList<>();
        quizTopics = new ArrayList<>();

        addQuiz(new Quiz("Math", new Question[] {
                new Question("What is the answer?", new String[] {
                        "Its definitely A",
                        "It must be B",
                        "Certainly not C",
                        "Bruh, its D"
                }, 0),
                new Question("What is the answer?", new String[] {
                        "Its definitely A",
                        "It must be B",
                        "Certainly not C",
                        "Bruh, its D"
                }, 1)
        }));

        addQuiz(new Quiz("Physics", new Question[] {
                new Question("What is the answer?", new String[] {
                        "Its definitely A",
                        "It must be B",
                        "Certainly not C",
                        "Bruh, its D"
                }, 0)
        }));

        addQuiz(new Quiz("Marvel Super Heroes", new Question[] {
                new Question("What is the answer?", new String[] {
                        "Its definitely A",
                        "It must be B",
                        "Certainly not C",
                        "Bruh, its D"
                }, 0)
        }));
    }

    public static Quiz getQuiz(int index) {
        return quizzes.get(index);
    }

    public static List<String> getTopics() {
        return quizTopics;
    }

    //

    private static void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        quizTopics.add(quiz.getTopic());
    }
}
