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

public class TujuanPembelajaran extends AppCompatActivity {

    private WebView Materi;
    private ArrayList<String> listSlide;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_materi);
        Materi = findViewById(R.id.content_materi);
        Button btnNext = findViewById(R.id.bt_next);
        TextView tvMateriNo = findViewById(R.id.no_materi);
        tvMateriNo.setText("Slide : 1/1");
        addData();
        Button btnPrev = findViewById(R.id.btn_prev);
        btnNext.setOnClickListener(view -> finish());
        btnNext.setText("Selesai");
        btnPrev.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Materi.loadUrl(listSlide.get(0));
    }

    private void addData() {
        int indext = getIntent().getIntExtra(NUMBER_INDEX, 0);
        listSlide = new ArrayList<>();

        switch (indext) {
            case 0:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujuan_pembelajaran_" + a + ".html");
                }
            case 1:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_rumus_fungsi_" + a + ".html");
                }
                break;
            case 2:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujuan_pembelajaran_fungsi_" + a + ".html");
                }
                break;
            case 3:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_pythagoras_" + a + ".html");
                }
                break;
            case 4:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_segitiga_" + a + ".html");
                }
                break;
            case 5:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_tripel_" + a + ".html");
                }
                break;
            case 6:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_baris_bilangan_" + a + ".html");
                }
                break;
            case 7:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_aritmetika_" + a + ".html");
                }
                break;
            case 8:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_geometri_" + a + ".html");
                }
                break;
            case 9:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_kemiringan_" + a + ".html");
                }
                break;
            case 10:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_garislurus_" + a + ".html");
                }
                break;
            case 11:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_duagaris_" + a + ".html");
                }
                break;
            case 12:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_kartesius_" + a + ".html");
                }
                break;
            case 13:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_posisi_titik_" + a + ".html");
                }
                break;
            case 14:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_posisi_garis_" + a + ".html");
                }
                break;
            case 15:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_spldv_" + a + ".html");
                }
                break;
            case 16:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_spldv_" + a + ".html");
                }
                break;
            case 17:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_spldv_" + a + ".html");
                }
                break;
            case 18:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_spldv_" + a + ".html");
                }
                break;
            case 19:
                for (int a = 1; a <= 1; a++) {
                    listSlide.add("file:///android_asset/tujpel_penerapan_" + a + ".html");
                }
                break;
        }
    }
}
