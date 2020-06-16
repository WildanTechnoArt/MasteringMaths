package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.unpas.masteringmaths.R;

import java.util.ArrayList;

public class RumusFungsiActivity extends AppCompatActivity {

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

        Materi = findViewById(R.id.content_materi);
        Button btnNext = findViewById(R.id.bt_next);
        Button btnprev = findViewById(R.id.btn_prev);
        tvMateriNo = findViewById(R.id.no_materi);
        tvMateriNo.setText("Slide Materi : 1/9");
        addData();
        btnNext.setOnClickListener(view -> {
            if (indext < listSlide.size()-1){
                indext++;
                nomor++;
                Materi.loadUrl(listSlide.get(indext));
                tvMateriNo.setText("Slide Materi : " + nomor + "/9");
            }
        });

        btnprev.setOnClickListener(view -> {
            if (indext > 0 ){
                indext--;
                nomor--;
                Materi.loadUrl(listSlide.get(indext));
                tvMateriNo.setText("Slide Materi : " + nomor + "/9");
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
        for (int a = 1; a<=9; a++){
            listSlide.add("file:///android_asset/rumus_fungsi_"+a+".html");
        }
    }
}
