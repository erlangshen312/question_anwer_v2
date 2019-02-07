package kg.enesaitech.islam.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kg.enesaitech.islam.R;
import kg.enesaitech.islam.db.Database;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        importData();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, TestListActivity.class);
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(r, 2500);
    }

    void importData() {
        Database db = new Database(this);
        if (!db.isImported()) {
            db.openDatabase();
            db.setImported();
        }
    }

//    void importData() {
//        System.out.println("called import data");
//        Database db = new Database(this);
//        if (!db.isImported()) {
//            System.out.println("STARTING IMPORTING");
//            String json = readJson();
//            try {
//                JSONArray jsonArray = new JSONArray(json);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject test = jsonArray.getJSONObject(i);
//                    System.out.println("******************************************importing test: " + test.getString("name"));
//                    int locked = 0;
//                    if (i > 0){
//                        locked = 1;
//                    }
//                    int test_id = db.insertTest(test.getString("name"), locked);
//
//                    JSONArray questArray = test.getJSONArray("questions");
//                    for (int q = 0; q < questArray.length(); q++) {
//                        JSONObject question = questArray.getJSONObject(q);
//                        int question_id = db.insertQuestion(test_id, question.getString("name"));
//                        System.out.println("importing question: " + question.getString("name"));
//                        int correct_test_id = 0;
//                        JSONArray answerArray = question.getJSONArray("answers");
//                        for (int aIdx = 0; aIdx < answerArray.length(); aIdx++) {
//                            String answer = answerArray.getString(aIdx);
//                            System.out.println(answer);
//                            if (answer.endsWith("+")) {
//                                System.out.println("question_id: "+ question_id);
//                                System.out.println("Correnct answer found: "+ answer);
//                                answer = answer.substring(0, answer.length() - 1);
//
//                                correct_test_id = db.insertAnswer(question_id, answer);
//                            }
//                            else{
//
//                                int answer_id = db.insertAnswer(question_id, answer);
//                            }
//                        }
//                        if (correct_test_id == 0) {
//                            throw new Exception("Correct answer NOT FOUND");
//                        } else {
//                            db.updateCorrectAnswer(question_id, correct_test_id);
//                        }
//                    }
//                }
//
//                db.setImported();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    String readJson() {
//        String result = "";
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(
//                    new InputStreamReader(getAssets().open("tests.json")));
//            // do reading, usually loop until end of file reading
//            String mLine;
//            while ((mLine = reader.readLine()) != null) {
//                //process line
//                result = result + mLine;
//                System.out.println(mLine);
//            }
//        } catch (IOException e) {
//            //log the exception
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    //log the exception
//                }
//            }
//        }
//        return result;
//    }
}
