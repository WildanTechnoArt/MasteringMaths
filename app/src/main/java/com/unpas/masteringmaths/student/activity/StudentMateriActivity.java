package com.unpas.masteringmaths.student.activity;

import android.content.Intent;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.unpas.masteringmaths.R;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;

public class StudentMateriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_materi);

        RelativeLayout layS = findViewById(R.id.lay_sejarah);
        RelativeLayout layR = findViewById(R.id.lay_MateriR);
        RelativeLayout layF = findViewById(R.id.lay_MateriF);

        layS.setOnClickListener(view -> {
            Intent intent = new Intent(StudentMateriActivity.this,TujuanPembelajaran.class);
            intent.putExtra(NUMBER_INDEX, 12);
            startActivity(intent);
        });

        layR.setOnClickListener(view -> {
            Intent intent = new Intent(StudentMateriActivity.this,MasKonActivity.class);
            intent.putExtra(NUMBER_INDEX, 13);
            startActivity(intent);
        });

        layF.setOnClickListener(view -> startActivity(new Intent(StudentMateriActivity.this,InMateriActivity.class)));
    }
}
