package kg.enesaitech.islam.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kg.enesaitech.islam.R;

public class InfoActivity extends AppCompatActivity {

    Toolbar toolbarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        toolbarInfo = (Toolbar) findViewById(R.id.toolbar_info);
        toolbarInfo.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_button));
        setSupportActionBar(toolbarInfo);
        getSupportActionBar().setTitle("Информация");
        toolbarInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
