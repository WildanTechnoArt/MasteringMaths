package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.unpas.masteringmaths.R;

import static com.unpas.masteringmaths.student.activity.StudentExamActivity.*;
import static com.unpas.masteringmaths.student.activity.SudentQuizeActivity.TIMER;

public class StudentResultActivity extends AppCompatActivity {

    private int myPoint;
    private int myTrueAnswer;
    private int myFalseAnswer;
    private String myTime;
    private TextView tvTrue, tvFalse, tvPoint, tvBad, tvGood,tvTime;
    private Toolbar toolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTrue = findViewById(R.id.tv_true);
        tvFalse = findViewById(R.id.tv_false);
        tvPoint = findViewById(R.id.tv_myscore);
        tvTime = findViewById(R.id.myTime);
        tvBad = findViewById(R.id.bad);
        tvGood = findViewById(R.id.good);

        myPoint = getIntent().getIntExtra(MY_POINT, 0);
        myTrueAnswer = getIntent().getIntExtra(TRUE_ANSWER, 0);
        myFalseAnswer = getIntent().getIntExtra(FALSE_ANSWER, 0);
        myTime = getIntent().getStringExtra(TIMER);

        if (myPoint >= 70) {
            tvGood.setBackgroundColor(Color.parseColor("#FF15D600"));
            tvGood.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            tvBad.setBackgroundColor(Color.parseColor("#FFF60400"));
            tvBad.setTextColor(Color.parseColor("#FFFFFF"));
        }

        tvTrue.setText("Benar : " + myTrueAnswer);
        tvFalse.setText("Salah : " + myFalseAnswer);
        tvPoint.setText(String.valueOf(myPoint));
        tvTime.setText("Sisa Waktu : " + myTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.finish:
                finish();
                break;
        }
        return true;
    }
}
