package com.unpas.masteringmaths.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;

public class PaketKordinat extends AppCompatActivity {

    private RelativeLayout lay1,lay2,lay3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_paket_exam);

        lay1 = findViewById(R.id.layM1);
        lay2 = findViewById(R.id.layM2);
        lay3 = findViewById(R.id.layM3);

        lay1.setOnClickListener(view -> startActivity(new Intent(PaketKordinat.this,ExamKartesius.class)));

        lay2.setOnClickListener(view -> startActivity(new Intent(PaketKordinat.this,ExamPosisiTitik.class)));

        lay3.setOnClickListener(view -> startActivity(new Intent(PaketKordinat.this,ExamPosisiGaris.class)));
    }
}
