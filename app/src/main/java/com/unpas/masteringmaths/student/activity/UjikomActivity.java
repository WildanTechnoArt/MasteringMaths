package com.unpas.masteringmaths.student.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.unpas.masteringmaths.R;

public class UjikomActivity extends AppCompatActivity {

    private RelativeLayout layR, layF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujikom);

        layR = findViewById(R.id.lay_MateriR);
        layF = findViewById(R.id.lay_MateriF);

        layR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UjikomActivity.this,StudentPaketExamActivity.class));
            }
        });

        layF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UjikomActivity.this,StudentPaketExamActivity.class));
            }
        });

    }
}
