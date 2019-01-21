package kg.enesaitech.islam.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kg.enesaitech.islam.R;
import kg.enesaitech.islam.activity.AnswerActivity;
import kg.enesaitech.islam.db.Answer;
import kg.enesaitech.islam.db.Question;

/**
 * Created by e.zhumanasyrov on 14.01.2019.
 */

public class AnswerAdapter extends BaseAdapter {


    final ArrayList<Answer> answerList;
    Question current_question;

    public AnswerAdapter(AnswerActivity answerActivity, ArrayList<Answer> answer, Question current_question) {
        this.answerList = answer;
        this.current_question = current_question;
    }

    @Override
    public int getCount() {
        return answerList.size();
    }

    @Override
    public Object getItem(int position) {
        return answerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_answers, parent, false);
        }
        TextView tv = view.findViewById(R.id.answers);

        if (current_question.getAnswered_id() > 0) {
            if (current_question.getCorrect_answered_id() == answerList.get(position).getId()) {
                tv.setBackgroundColor(Color.GREEN);
            }
            if (answerList.get(position).getId() == current_question.getAnswered_id()) {
                if (current_question.getAnswered_id() != current_question.getCorrect_answered_id()) {
                    tv.setBackgroundColor(Color.RED);
                }
            }
        }
        tv.setText(answerList.get(position).getName());
        return view;
    }
}
