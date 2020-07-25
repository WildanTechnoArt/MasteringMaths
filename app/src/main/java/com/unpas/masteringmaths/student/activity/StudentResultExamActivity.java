package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.unpas.masteringmaths.R;
import com.unpas.masteringmaths.teacher.model.Teacher;

import static com.unpas.masteringmaths.student.activity.StudentExamActivity.*;

public class StudentResultExamActivity extends AppCompatActivity {

    private int myPoint;
    private int myTrueAnswer;
    private int myFalseAnswer;
    private TextView tvTrue, tvFalse, tvPoint, tvBad, tvGood;
    private Toolbar toolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result_exam);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTrue = findViewById(R.id.tv_true);
        tvFalse = findViewById(R.id.tv_false);
        tvPoint = findViewById(R.id.tv_myscore);
        tvBad = findViewById(R.id.bad);
        tvGood = findViewById(R.id.good);

        myPoint = getIntent().getIntExtra(MY_POINT, 0);
        myTrueAnswer = getIntent().getIntExtra(TRUE_ANSWER, 0);
        myFalseAnswer = getIntent().getIntExtra(FALSE_ANSWER, 0);

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.finish) {
            finish();
        }
        return true;
    }
}
