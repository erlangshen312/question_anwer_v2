package kg.enesaitech.islam.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kg.enesaitech.islam.R;
import kg.enesaitech.islam.adapter.AnswerAdapter;
import kg.enesaitech.islam.adapter.TestListAdapter;
import kg.enesaitech.islam.db.Answer;
import kg.enesaitech.islam.db.Database;
import kg.enesaitech.islam.db.Question;
import kg.enesaitech.islam.db.Test;

public class AnswerActivity extends AppCompatActivity {

    Toolbar toolbarAnswer;
    AnswerAdapter answerAdapter;
    GridView mGridView;
    Intent intent;
    TextView nameTV;
    Button prevBtn, nextBtn;
    int questionPosition;

    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

//        toolbarAnswer = (Toolbar) findViewById(R.id.toolbar_answer);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        prevBtn = (Button) findViewById(R.id.prevBtn);
        nameTV = (TextView) findViewById(R.id.tvAnswer);
//        toolbarAnswer.setTitleTextColor(getResources().getColor(R.color.colorWhite));
//        setSupportActionBar(toolbarAnswer);
//        getSupportActionBar().setTitle("Список тестов");

        mGridView = (GridView) findViewById(R.id.gridViewAnswerActivity);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {

            finish();
            return;
        }
        final int test_id = bundle.getInt("test_id");
        Test test = db.getTest(test_id);
        questionPosition = test.getPosition();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionPosition = questionPosition + 1;
                db.setPosition(test_id, questionPosition);
                renderPage(test_id);
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionPosition = questionPosition - 1;
                db.setPosition(test_id, questionPosition);
                renderPage(test_id);
            }
        });

        renderPage(test_id);
    }

    void renderPage(final int test_id) {
        prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);

        ArrayList<Question> testQuestions = db.getQuestions(test_id);
        if (testQuestions.size() < questionPosition) {
            Toast.makeText(this, "Системная ошибка", Toast.LENGTH_LONG).show();
        } else if (testQuestions.size() == questionPosition) {
            // go to result page
            questionPosition = 0;
            db.setPosition(test_id, questionPosition);
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("test_id", test_id);
            startActivity(intent);
            finish();


        } else {
            if (questionPosition == 0) {
                prevBtn.setEnabled(false);
            }
            final Question current_question = testQuestions.get(questionPosition);
            nameTV.setText(current_question.getName());

            final ArrayList<Answer> answers = db.getAnswers(current_question.getId());

            answerAdapter = new AnswerAdapter(AnswerActivity.this, answers, current_question);
            mGridView.setAdapter(answerAdapter);
            mGridView.deferNotifyDataSetChanged();

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (current_question.getAnswered_id() == 0) {
                        db.setAnswered(current_question.getId(), answers.get(position).getId());
                        current_question.setAnswered_id(answers.get(position).getId());
//                        questionPosition = questionPosition + 1;
//                        db.setPosition(test_id, questionPosition);
                        renderPage(test_id);
                    } else {
                        Toast.makeText(AnswerActivity.this, "Вы уже ответили на этот вопрос", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
