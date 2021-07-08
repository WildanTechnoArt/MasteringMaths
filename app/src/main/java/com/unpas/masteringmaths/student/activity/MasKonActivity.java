package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;

import java.util.ArrayList;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;

public class MasKonActivity extends AppCompatActivity {

    private WebView Materi;
    private ArrayList<String> listSlide;
    private TextView tvMateriNo;
    private int indext;
    private Button btnNext, btnPrev;
    private int nomor = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_materi);

        Materi = findViewById(R.id.content_materi);
        btnNext = findViewById(R.id.bt_next);
        tvMateriNo = findViewById(R.id.no_materi);
        Materi.getSettings().setBuiltInZoomControls(true);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext.setOnClickListener(view -> finish());
        btnNext.setText("Selesai");
        btnPrev.setVisibility(View.GONE);
        addData();
        tvMateriNo.setText("Slide : 1/1");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Materi.loadUrl(listSlide.get(0));
    }

    private void addData() {
        indext = getIntent().getIntExtra(NUMBER_INDEX, 0);
        listSlide = new ArrayList<>();
        switch (indext) {
            case 0:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_relasi_"+a+".html");
                }
                break;
            case 1:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_rumus_fungsi_"+a+".html");
                }
                break;
            case 2:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_fungsi_"+a+".html");
                }
                break;
            case 3:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_pythagoras_"+a+".html");
                }
                break;
            case 4:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_segitiga_"+a+".html");
                }
                break;
            case 5:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_tripel_"+a+".html");
                }
                break;
            case 6:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/maskon_baris_bilangan_" + a + ".html");
                }
                break;
            case 7:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_aritmetika_"+a+".html");
                }
                break;
            case 8:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_geometri_"+a+".html");
                }
                break;
            case 9:
                tvMateriNo.setText("Slide Materi : 1/3");
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size() - 1) {
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                        if (nomor == 2) {
                            btnNext.setText("Selesai");
                        }
                    } else {
                        finish();
                    }
                });
                btnPrev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                    }
                    if(indext < 3){
                        btnNext.setText("Lanjut");
                    }
                });
                for (int a = 1; a <= 3; a++) {
                    listSlide.add("file:///android_asset/maskon_kemiringan_" + a + ".html");
                }
                break;
            case 10:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_garis_lurus_"+a+".html");
                }
                break;
            case 11:
                tvMateriNo.setText("Slide Materi : 1/2");
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size() - 1) {
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/2");
                        if (nomor == 2) {
                            btnNext.setText("Selesai");
                        }
                    } else {
                        finish();
                    }
                });
                btnPrev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/2");
                    }
                    if(indext < 2){
                        btnNext.setText("Lanjut");
                    }
                });
                for (int a = 1; a <= 2; a++) {
                    listSlide.add("file:///android_asset/maskon_duagaris_" + a + ".html");
                }
                break;
            case 12:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_kartesius_"+a+".html");
                }
                break;
            case 13:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_posisi_titik_"+a+".html");
                }
                break;
            case 14:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_posisi_garis_"+a+".html");
                }
                break;
            case 15:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_spldv_"+a+".html");
                }
                break;
            case 16:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_eliminasi_"+a+".html");
                }
                break;
            case 17:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_spldv_"+a+".html");
                }
                break;
            case 18:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_eliminasi_"+a+".html");
                }
                break;
            case 19:
                for (int a = 1; a<=1; a++){
                    listSlide.add("file:///android_asset/maskon_penerapan_"+a+".html");
                }
                break;
        }
    }
}
