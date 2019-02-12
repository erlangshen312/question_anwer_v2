package kg.enesaitech.islam.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kg.enesaitech.islam.R;
import kg.enesaitech.islam.adapter.TestListAdapter;
import kg.enesaitech.islam.db.Database;
import kg.enesaitech.islam.db.Test;

public class TestListActivity extends AppCompatActivity {

    TestListAdapter testListAdapter;
    Toolbar toolbarList;
    GridView mGridView;
    Intent intent;
    TextView nameTV;
    ArrayList<Test> tests;

    Database db = new Database(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbarList = (Toolbar) findViewById(R.id.toolbar_list);
//        toolbarList.setTitleTextColor(getResources().getColor(R.color.colorBlack));
//        toolbarList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
//        setSupportActionBar(toolbarList);
//        getSupportActionBar().setTitle("Список тестов");

        mGridView = findViewById(R.id.gridViewListActivity);
        tests = db.getTests();

        testListAdapter = new TestListAdapter(TestListActivity.this, tests);
        mGridView.setAdapter(testListAdapter);
        mGridView.deferNotifyDataSetChanged();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (tests.get(position).getLocked() == 1) {
                    Toast.makeText(TestListActivity.this, "Sorry this is disabled", Toast.LENGTH_LONG).show();
                } else {
                    int test_id = tests.get(position).getId();
                    String test_name = tests.get(position).getName();
                    intent = new Intent(TestListActivity.this, AnswerActivity.class);
                    intent.putExtra("test_id", test_id);
                    Log.i("BUNDLE*******", "You clicked Item: " + test_id + " " + test_name );
                    Toast.makeText(TestListActivity.this, "Сиз " + test_name + " дегенди тандадыныз", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }


            }
        }
        );
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        tests = db.getTests();
        testListAdapter = new TestListAdapter(TestListActivity.this, tests);
        mGridView.setAdapter(testListAdapter);
        mGridView.deferNotifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                share();
                return true;
            case R.id.info:
                info();
                return true;
            default:
                return false;
        }
    }

    public void share() {
        String shareBody = "https://play.google.com/store/apps/details?id=kg.enesaitech.islam";

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Поделиться через"));
    }

    public void info() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }


}
