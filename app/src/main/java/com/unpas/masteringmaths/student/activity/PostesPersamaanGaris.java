package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unpas.masteringmaths.R;

import java.util.ArrayList;
import java.util.Objects;

public class PostesPersamaanGaris extends AppCompatActivity {

    private WebView Materi;
    private ArrayList<String> listSlide;
    private int nomor = 1;
    private TextView tvMateriNo;
    private int indext = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_materi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Postes");

        Materi = findViewById(R.id.content_materi);
        Button btnNext = findViewById(R.id.bt_next);
        Button btnprev = findViewById(R.id.btn_prev);
        tvMateriNo = findViewById(R.id.no_materi);
        Materi.getSettings().setBuiltInZoomControls(true);
        tvMateriNo.setText("Slide Postes : 1/6");
        addData();
        btnNext.setOnClickListener(view -> {
            if (indext < listSlide.size()-1){
                indext++;
                nomor++;
                Materi.loadUrl(listSlide.get(indext));
                tvMateriNo.setText("Slide Postes : " + nomor + "/6");
            }
        });

        btnprev.setOnClickListener(view -> {
            if (indext > 0 ){
                indext--;
                nomor--;
                Materi.loadUrl(listSlide.get(indext));
                tvMateriNo.setText("Slide Postes : " + nomor + "/6");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Materi.loadUrl(listSlide.get(indext));
    }

    private void addData(){
        listSlide = new ArrayList<>();
        for (int a = 1; a<=6; a++){
            listSlide.add("file:///android_asset/postes_garislurus_"+a+".html");
        }
    }
}
