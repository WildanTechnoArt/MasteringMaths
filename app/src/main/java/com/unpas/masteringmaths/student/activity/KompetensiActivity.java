package com.unpas.masteringmaths.student.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;

import static com.unpas.masteringmaths.utils.UtilsConstant.COMPETENT_NUMBER;

public class KompetensiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_kompetesi);
        WebView komdas = findViewById(R.id.main_content);
        komdas.getSettings().setBuiltInZoomControls(true);

        int competentNumber = getIntent().getIntExtra(COMPETENT_NUMBER, 0);

        switch (competentNumber){
            case 0:
                komdas.loadUrl("file:///android_asset/relasi_komdas.html");
                break;
            case 1:
                komdas.loadUrl("file:///android_asset/pythagoras_komdas.html");
                break;
            case 2:
                komdas.loadUrl("file:///android_asset/pola_bilangan_komdas.html");
                break;
            case 3:
                komdas.loadUrl("file:///android_asset/garis_lurus_komdas.html");
                break;
            case 4:
                komdas.loadUrl("file:///android_asset/cartesius_komdas.html");
                break;
            case 5:
                komdas.loadUrl("file:///android_asset/spldv_komdas.html");
                break;
        }
    }
}
