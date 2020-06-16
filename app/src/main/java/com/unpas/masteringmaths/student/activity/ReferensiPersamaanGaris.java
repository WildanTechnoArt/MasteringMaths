package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unpas.masteringmaths.R;

import java.util.Objects;

public class ReferensiPersamaanGaris extends AppCompatActivity {

    WebView referensi;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_kompetesi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Referensi");

        referensi = findViewById(R.id.main_content);
        referensi.loadUrl("file:///android_asset/referensi_garislurus.html");
    }
}
