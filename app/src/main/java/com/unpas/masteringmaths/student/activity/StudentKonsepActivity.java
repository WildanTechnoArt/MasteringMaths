package com.unpas.masteringmaths.student.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.unpas.masteringmaths.R;

import static com.unpas.masteringmaths.utils.UtilsConstant.COMPETENT_NUMBER;

public class StudentKonsepActivity extends AppCompatActivity {

    private WebView konsep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_konsep);

        konsep = findViewById(R.id.main_konsep);
        konsep.getSettings().setBuiltInZoomControls(true);

        int competentNumber = getIntent().getIntExtra(COMPETENT_NUMBER, 0);

        switch (competentNumber){
            case 0:
                konsep.loadUrl("file:///android_asset/petakonseprelasi.html");
                break;
            case 1:
                konsep.loadUrl("file:///android_asset/petakonseppythagoras.html");
                break;
            case 2:
                konsep.loadUrl("file:///android_asset/petakonseppolabilangan.html");
                break;
            case 3:
                konsep.loadUrl("file:///android_asset/petakonsepgarislurus.html");
                break;
            case 4:
                konsep.loadUrl("file:///android_asset/petakonsepkartesius.html");
                break;
            case 5:
                konsep.loadUrl("file:///android_asset/petakonsepspldv.html");
                break;
        }
    }
}
