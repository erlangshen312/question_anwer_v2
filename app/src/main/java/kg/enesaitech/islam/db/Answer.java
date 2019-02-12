package kg.enesaitech.islam.db;

/**
 * Created by e.zhumanasyrov on 11.01.2019.
 */

public class Answer {

    private int id;
    private String name;
    private int question_id;


    public Answer() {
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

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
