package kg.enesaitech.islam.db;

/**
 * Created by e.zhumanasyrov on 11.01.2019.
 */

public class Question {

    private int id;
    private String name;
    private int correct_answered_id;
    private int answered_id;
    private int test_id;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCorrect_answered_id() {
        return correct_answered_id;
    }

    public void setCorrect_answered_id(int correct_answered_id) {
        this.correct_answered_id = correct_answered_id;
    }

    public int getAnswered_id() {
        return answered_id;
    }

    public void setAnswered_id(int answered_id) {
        this.answered_id = answered_id;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }
}
