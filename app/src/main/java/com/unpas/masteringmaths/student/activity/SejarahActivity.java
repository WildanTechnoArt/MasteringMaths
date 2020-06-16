package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;

import java.util.ArrayList;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;

public class SejarahActivity extends AppCompatActivity {

    private WebView Materi;
    private ArrayList<String> listSlide;
    private int nomor = 1;
    private TextView tvMateriNo;
    private int indext = 0;
    private int number;
    private Button btnNext, btnPrev;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_materi);
        Materi = findViewById(R.id.content_materi);
        btnNext = findViewById(R.id.bt_next);
        btnPrev = findViewById(R.id.btn_prev);
        tvMateriNo = findViewById(R.id.no_materi);
        tvMateriNo.setText("Slide Sejarah : 1/1");
        btnNext.setOnClickListener(view -> {
            if (indext < listSlide.size() - 1) {
                indext++;
                nomor++;
                Materi.loadUrl(listSlide.get(indext));
                tvMateriNo.setText("Slide Materi : " + nomor + "/1");
            }
        });
        addData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Materi.loadUrl(listSlide.get(indext));
    }

    private void addData() {
        number = getIntent().getIntExtra(NUMBER_INDEX, 0);
        listSlide = new ArrayList<>();
        switch (number) {
            case 0:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/sejarah_relasi_fungsi_"+a+".html");
                }
                break;
            case 1:
                tvMateriNo.setText("Slide Sejarah : 1/2");
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/2");
                    }
                });
                btnPrev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/2");
                    }
                });
                for (int a = 1; a<=2; a++){
                    listSlide.add("file:///android_asset/sejarah_pythagoras_"+a+".html");
                }
                break;
            case 2:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/sejarah_polabilangan_" + a + ".html");
                }
                break;
            case 3:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/sejarah_garislurus_" + a + ".html");
                }
                break;
            case 4:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/sejarah_kartesius_" + a + ".html");
                }
                break;
            case 5:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/sejarah_spldv_"+a+".html");
                }
                break;
        }
    }
}
