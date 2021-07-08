package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unpas.masteringmaths.R;

import java.util.Objects;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;

public class RangkumanActivity extends AppCompatActivity {

    private WebView rangkuman;
    private int index;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_kompetesi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Rangkuman");

        rangkuman = findViewById(R.id.main_content);
        rangkuman.getSettings().setBuiltInZoomControls(true);

        showData();
    }

    private void showData() {
        index = getIntent().getIntExtra(NUMBER_INDEX, 0);
        switch (index) {
            case 0:
                rangkuman.loadUrl("file:///android_asset/rangkuman_relasi_1.html");
                break;
            case 1:
                rangkuman.loadUrl("file:///android_asset/rangkuman_pythagoras_1.html");
                break;
            case 2:
                rangkuman.loadUrl("file:///android_asset/rangkuman_polabilangan_1.html");
                break;
            case 3:
                rangkuman.loadUrl("file:///android_asset/rangkuman_garislurus_1.html");

                break;
            case 4:
                rangkuman.loadUrl("file:///android_asset/rangkuman_kartesius_1.html");

                break;
            case 5:
                rangkuman.loadUrl("file:///android_asset/rangkuman_spldv_1.html");
                break;
        }
    }
}
