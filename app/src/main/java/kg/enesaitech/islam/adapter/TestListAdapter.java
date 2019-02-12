package kg.enesaitech.islam.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kg.enesaitech.islam.R;
import kg.enesaitech.islam.activity.TestListActivity;
import kg.enesaitech.islam.db.Question;
import kg.enesaitech.islam.db.Test;

/**
 * Created by e.zhumanasyrov on 26.11.2018.
 */

public class TestListAdapter extends BaseAdapter {


    final ArrayList<Test> testListAdapter;

    public TestListAdapter(TestListActivity testListActivity, ArrayList<Test> tests) {
        this.testListAdapter = tests;
    }

    @Override
    public int getCount() {
        return testListAdapter.size();
    }

    @Override
    public Object getItem(final int position) {
        return testListAdapter.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_number, parent, false);
        }
        TextView tv = view.findViewById(R.id.numbers);
//        tv.setText(testListAdapter.get(position).getName());

        if (testListAdapter.get(position).getLocked() ==  1 ) {
            tv.setBackgroundColor(Color.DKGRAY);
            tv.setTextColor(Color.WHITE);
            tv.setPadding(10,10,10,10);
            tv.setGravity(Gravity.CENTER);
            tv.setText(testListAdapter.get(position).getName());
        } else {
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(10,10,10,10);
            tv.setGravity(Gravity.CENTER);
            tv.setText(testListAdapter.get(position).getName());
        }
//        Log.i("TESTADAPTER => ***", "ADAPTER + " + tv.getText());
        return view;
    }
}
