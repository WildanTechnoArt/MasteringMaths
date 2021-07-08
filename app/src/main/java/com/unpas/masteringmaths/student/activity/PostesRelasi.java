package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unpas.masteringmaths.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;

public class PostesRelasi extends AppCompatActivity {

    private WebView Materi;
    private ArrayList<String> listSlide;
    private int nomor = 1;
    private TextView tvMateriNo;
    private int indext = 0;
    private int position;
    private  Button btnNext, btnprev;
    private Boolean kondisi = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_materi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Postes");

        Materi = findViewById(R.id.content_materi);
        btnNext = findViewById(R.id.bt_next);
        btnprev = findViewById(R.id.btn_prev);
        tvMateriNo = findViewById(R.id.no_materi);
        Materi.getSettings().setBuiltInZoomControls(true);

        addData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Materi.loadUrl(listSlide.get(0));
    }

    private void addData() {
        position = getIntent().getIntExtra(NUMBER_INDEX, 0);
        listSlide = new ArrayList<>();

        switch (position) {
            case 0:
                tvMateriNo.setText("Page : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/postes_relasi_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if(btnNext.getText() == "Finish"){
                        finish();
                    }else{
                        if (indext < listSlide.size() - 1) {
                            indext++;
                            nomor++;
                            Materi.loadUrl(listSlide.get(indext));
                            tvMateriNo.setText("Page : " + nomor + "/6");
                            if (nomor == 6) {
                                btnNext.setText("Finish");
                            }
                            kondisi = false;
                        }
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Page : " + nomor + "/6");
                    }
                });
                break;
            case 1:
                tvMateriNo.setText("Page : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/postes_pythagoras_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if(btnNext.getText() == "Finish"){
                        finish();
                    }else{
                        if (indext < listSlide.size() - 1) {
                            indext++;
                            nomor++;
                            Materi.loadUrl(listSlide.get(indext));
                            tvMateriNo.setText("Page : " + nomor + "/6");

                            if (nomor == 6) {
                                btnNext.setText("Finish");
                            }
                            kondisi = false;
                        }
                    }

                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Page : " + nomor + "/6");
                    }
                });
                break;
            case 2:
                tvMateriNo.setText("Page : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/postes_polabilangan_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if(btnNext.getText() == "Finish"){
                        finish();
                    }else{
                        if (indext < listSlide.size() - 1) {
                            indext++;
                            nomor++;
                            Materi.loadUrl(listSlide.get(indext));
                            tvMateriNo.setText("Page : " + nomor + "/6");
                            if (nomor == 6) {
                                btnNext.setText("Finish");
                            }
                            kondisi = false;
                        }
                    }

                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Page : " + nomor + "/6");
                    }
                });
                break;
            case 3:
                tvMateriNo.setText("Page : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/postes_garislurus_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if(btnNext.getText() == "Finish"){
                        finish();
                    }else{
                        if (indext < listSlide.size() - 1) {
                            indext++;
                            nomor++;
                            Materi.loadUrl(listSlide.get(indext));
                            tvMateriNo.setText("Page : " + nomor + "/6");
                            if (nomor == 6) {
                                btnNext.setText("Finish");
                            }
                            kondisi = false;
                        }
                    }

                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Page : " + nomor + "/6");
                    }
                });
                break;
            case 4:
                tvMateriNo.setText("Page : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/postes_kartesius_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if(btnNext.getText() == "Finish"){
                        finish();
                    }else{
                        if (indext < listSlide.size() - 1) {
                            indext++;
                            nomor++;
                            Materi.loadUrl(listSlide.get(indext));
                            tvMateriNo.setText("Page : " + nomor + "/6");
                            if (nomor == 6) {
                                btnNext.setText("Finish");
                            }
                            kondisi = false;
                        }
                    }

                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Page : " + nomor + "/6");
                    }
                });
                break;
            case 5:
                tvMateriNo.setText("Page : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/postes_spldv_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if(btnNext.getText() == "Finish"){
                        finish();
                    }else{
                        if (indext < listSlide.size() - 1) {
                            indext++;
                            nomor++;
                            Materi.loadUrl(listSlide.get(indext));
                            tvMateriNo.setText("Page : " + nomor + "/6");

                            if (nomor == 6) {
                                btnNext.setText("Finish");
                            }
                            kondisi = false;
                        }
                    }

                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Page : " + nomor + "/6");
                    }
                });
                break;
        }
    }
}
