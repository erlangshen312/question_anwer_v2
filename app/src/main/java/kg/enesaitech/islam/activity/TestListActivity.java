package kg.enesaitech.islam.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    Database db = new Database(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbarList = (Toolbar) findViewById(R.id.toolbar_list);
        toolbarList.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbarList);
        getSupportActionBar().setTitle("Список тестов");

        mGridView = findViewById(R.id.gridViewListActivity);
        final ArrayList<Test> tests = db.getTests();

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
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        ArrayList<Test> tests = db.getTests();
        testListAdapter = new TestListAdapter(TestListActivity.this, tests);
        mGridView.setAdapter(testListAdapter);
        mGridView.deferNotifyDataSetChanged();

    }
}
