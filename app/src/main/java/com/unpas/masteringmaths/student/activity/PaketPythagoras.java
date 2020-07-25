package com.unpas.masteringmaths.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;

public class PaketPythagoras extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_paket_exam);

        RelativeLayout lay1 = findViewById(R.id.layM1);
        RelativeLayout lay2 = findViewById(R.id.layM2);
        RelativeLayout lay3 = findViewById(R.id.layM3);
        RelativeLayout lay4 = findViewById(R.id.layM4);

        lay1.setOnClickListener(view -> startActivity(new Intent(PaketPythagoras.this,ExamTeorema.class)));

        lay2.setOnClickListener(view -> startActivity(new Intent(PaketPythagoras.this,ExamTripelPythagoras.class)));

        lay3.setOnClickListener(view -> startActivity(new Intent(PaketPythagoras.this,ExamSegitigaPythagoras.class)));

        lay4.setOnClickListener(view -> startActivity(new Intent(PaketPythagoras.this,ExamPenerapanPythagoras.class)));
    }
}
