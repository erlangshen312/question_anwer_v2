package kg.enesaitech.islam.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kg.enesaitech.islam.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, TestListActivity.class);
                startActivity(intent);
            }
        };
        handler.postDelayed(r, 1000);
    }
}
