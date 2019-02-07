package kg.enesaitech.islam.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kg.enesaitech.islam.R;
import kg.enesaitech.islam.db.Database;
import kg.enesaitech.islam.db.Point;
import kg.enesaitech.islam.db.Question;
import kg.enesaitech.islam.db.Test;

public class ResultActivity extends AppCompatActivity {

    Button buttonNext, buttonAgain;
    TextView resultTV, titleTV;
    Database db = new Database(this);
    int empty = 0, correct = 0, incorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        buttonNext = (Button) findViewById(R.id.btnNext);
        buttonAgain = (Button) findViewById(R.id.btnAgain);
        resultTV = (TextView) findViewById(R.id.resultTV);
        titleTV = (TextView) findViewById(R.id.titleTV);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {

            finish();
            return;
        }
        final int test_id = bundle.getInt("test_id");

        final ArrayList<Question> questions = db.getQuestions(test_id);

        for (Question q : questions) {
            if (q.getAnswered_id() == 0) {
                empty++;
            }
            if (q.getAnswered_id() == q.getCorrect_answered_id()) {
                correct++;
            } else {
                incorrect++;
            }
        }
        Point p = new Point();
        p.setTest_id(test_id);
        p.setCorrect(correct);
        p.setWrong(incorrect);
        p.setEmpty(empty);
        db.setResults(p);
        if (correct >= questions.size() * 0.9) {
            titleTV.setText("Куттуктайбыз!");

            int nextTestID = 0;
            ArrayList<Test> tests = db.getTests();
            boolean foundCurrent = false;
            for (Test t : tests) {
                if (foundCurrent) {
                    nextTestID = t.getId();
                    break;
                }
                if (t.getId() == test_id) {
                    foundCurrent = true;
                }
            }
            if (nextTestID != 0) {
                db.unlockNext(nextTestID);
            }
        } else {
            titleTV.setText("Cиз тестти өткөн жоксуз!");
            buttonNext.setEnabled(false);
        }


        db.resetTest(test_id);

        Point results = db.getResults();

        resultTV.setText("Туура жооп: " + correct + " \n Туура эмес жооп: " + incorrect + " \n Жооп берилбегендер: " + empty

        +" \n \n ЖАЛПЫ: \n Туура жооп: " + results.getCorrect() + " \n Туура эмес жооп: " +
                results.getWrong()+ " \n Жооп берилбегендер: " + results.getEmpty());


        buttonAgain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ResultActivity.this, AnswerActivity.class);
                        intent.putExtra("test_id", test_id);
                        finish();
                        startActivity(intent);
                    }
                }
        );
        buttonNext.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int nextTestID = 0;
                        ArrayList<Test> tests = db.getTests();
                        boolean foundCurrent = false;
                        for (Test t : tests) {
                            if (foundCurrent) {
                                nextTestID = t.getId();
                                break;
                            }
                            if (t.getId() == test_id) {
                                foundCurrent = true;
                            }
                        }
                        if (nextTestID != 0) {
                            Intent intent = new Intent(ResultActivity.this, AnswerActivity.class);
                            intent.putExtra("test_id", nextTestID);
                            finish();
                            startActivity(intent);

                        } else {
                            finish();
                        }
                    }
                }
        );
    }
}
