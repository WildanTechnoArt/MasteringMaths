package com.unpas.masteringmaths.student.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;

public class KonsepActivity extends AppCompatActivity {

    private WebView konsep;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_konsep);
        konsep = findViewById(R.id.main_konsep);
        konsep.getSettings().setBuiltInZoomControls(true);

        index = getIntent().getIntExtra(NUMBER_INDEX, 0);

        switch (index) {
            case 0:
                konsep.loadUrl("file:///android_asset/petakonsepkartesius.html");
                break;
            case 1:
                konsep.loadUrl("file:///android_asset/petakonsepgarislurus.html");
                break;
            case 2:
                konsep.loadUrl("file:///android_asset/petakonseppolabilangan.html");
                break;
            case 3:
                konsep.loadUrl("file:///android_asset/petakonseppythagoras.html");
                break;
            case 4:
                konsep.loadUrl("file:///android_asset/petakonsepspldv.html");
                break;
        }
    }
}