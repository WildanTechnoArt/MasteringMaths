package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unpas.masteringmaths.R;

import java.util.Objects;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;

public class ReferensiRelasi extends AppCompatActivity {

    WebView referensi;
    private int index;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_kompetesi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Referensi");
        referensi = findViewById(R.id.main_content);
        referensi.getSettings().setBuiltInZoomControls(true);

        showData();
    }

    private void showData() {
        index = getIntent().getIntExtra(NUMBER_INDEX, 0);
        switch (index) {
            case 0:
                referensi.loadUrl("file:///android_asset/referensi_relasi.html");

                break;
            case 1:
                referensi.loadUrl("file:///android_asset/referensi_pythagoras.html");

                break;
            case 2:
                referensi.loadUrl("file:///android_asset/referensi_polabilangan.html");
                break;
            case 3:
                referensi.loadUrl("file:///android_asset/referensi_garislurus.html");

                break;
            case 4:
                referensi.loadUrl("file:///android_asset/referensi_kartesius.html");

                break;
            case 5:
                referensi.loadUrl("file:///android_asset/referensi_spldv.html");
                break;
        }
    }
}
