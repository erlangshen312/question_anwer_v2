package kg.enesaitech.islam.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
    TextView nameTV, mTimer;
    Button prevBtn, nextBtn;
    int questionPosition;
    Test test;
    int test_id;
    public int sleep;
    boolean GOTONEXT = false;


    CountDownTimer timer;

    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

//        toolbarAnswer = (Toolbar) findViewById(R.id.toolbar_answer);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        prevBtn = (Button) findViewById(R.id.prevBtn);
        nameTV = (TextView) findViewById(R.id.tvAnswer);
        mTimer = (TextView) findViewById(R.id.countDown);
//        toolbarAnswer.setTitleTextColor(getResources().getColor(R.color.colorWhite));
//        setSupportActionBar(toolbarAnswer);
//        getSupportActionBar().setTitle("Список тестов");

        mGridView = (GridView) findViewById(R.id.gridViewAnswerActivity);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {

            finish();
            return;
        }
        test_id = bundle.getInt("test_id");
        test = db.getTest(test_id);
        questionPosition = test.getPosition();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionPosition = questionPosition + 1;
                db.setPosition(test_id, questionPosition);
                renderPage();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionPosition = questionPosition - 1;
                db.setPosition(test_id, questionPosition);
                renderPage();
            }
        });


        renderPage();
        count();
    }

    void goToNext() {
        questionPosition = questionPosition + 1;
        db.setPosition(test_id, questionPosition);
        renderPage();
        count();
    }

    void count() {

        if (timer == null) {
            timer = new CountDownTimer(40000, 1000) {
                public void onTick(long millisUntilFinished) {
                    mTimer.setText("Убакыт калды: " + millisUntilFinished / 1000);
                }
                public void onFinish() {
                    if (GOTONEXT) {
                        mTimer.setText("Убакыт бутту!");
                        System.out.println("#################################");
                        System.out.println("On finish called");
                        goToNext();
                    }
                }
            };
            GOTONEXT = true;
            timer.start();
        } else {
            timer.cancel();
            timer.start();
        }
    }

    @Override
    public void onResume() {
        timer.start();
        super.onResume();
        Log.i("RESUME #########", "Timer resume");
    }

    @Override
    public void onPause() {
        GOTONEXT = false;
        timer.cancel();
        super.onPause();
        Log.i("PAUSE #########", "Timer pause");
    }

    void renderPage() {

        prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);


        ArrayList<Question> testQuestions = db.getQuestions(test_id);
        if (testQuestions.size() < questionPosition) {
            Toast.makeText(this, "Системная ошибка", Toast.LENGTH_LONG).show();
        } else if (testQuestions.size() == questionPosition) {
            // go to result page
            questionPosition = 0;
            db.setPosition(test_id, questionPosition);
            GOTONEXT = false;
            timer.cancel();
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("test_id", test_id);
            AnswerActivity.this.finish();
            startActivity(intent);

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
                        renderPage();
                        new WaiterTask().execute("123");

                    } else {
                        Toast.makeText(AnswerActivity.this, "Вы уже ответили на этот вопрос", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private class WaiterTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... urls) {

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            goToNext();
        }
    }
}
