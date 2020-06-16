package com.unpas.masteringmaths.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;
import static com.unpas.masteringmaths.utils.UtilsConstant.SUB_ACTIVITY;

public class SubActivity extends AppCompatActivity {

    private int laySIndex, layRIndex, layFIndex;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_materi);

        RelativeLayout layS = findViewById(R.id.lay_sejarah);
        RelativeLayout layR = findViewById(R.id.lay_MateriR);
        RelativeLayout layF = findViewById(R.id.lay_MateriF);

        getNumberIndex();

        layS.setOnClickListener(view -> {
            Intent intent = new Intent(SubActivity.this, TujuanPembelajaran.class);
            intent.putExtra(NUMBER_INDEX, laySIndex);
            startActivity(intent);
        });

        layR.setOnClickListener(view -> {
            Intent intent = new Intent(SubActivity.this, MasKonActivity.class);
            intent.putExtra(NUMBER_INDEX, layRIndex);
            startActivity(intent);
        });

        layF.setOnClickListener(view -> {
            Intent intent = new Intent(SubActivity.this, MateriActivity.class);
            intent.putExtra(NUMBER_INDEX, layFIndex);
            startActivity(intent);
        });
    }

    private void getNumberIndex() {
        index = getIntent().getStringExtra(SUB_ACTIVITY);
        switch (index) {
            case "Relasi":
                laySIndex = 0;
                layRIndex = 0;
                layFIndex = 0;
                break;
            case "Rumus Fungsi":
                laySIndex = 1;
                layRIndex = 1;
                layFIndex = 1;
                break;
            case "Fungsi":
                laySIndex = 2;
                layRIndex = 2;
                layFIndex = 2;
                break;
            case "Teorema Pythagoras":
                laySIndex = 3;
                layRIndex = 3;
                layFIndex = 3;
                break;
            case "Segitiga Siku-Siku Khusus":
                laySIndex = 4;
                layRIndex = 4;
                layFIndex = 4;
                break;
            case "Tripel Pythagoras":
                // SubFungsi
                laySIndex = 5;
                layRIndex = 5;
                layFIndex = 5;
                break;
            case "Barisan Bilangan":
                laySIndex = 6;
                layRIndex = 6;
                layFIndex = 6;
                break;
            case "Barisan dan Deret Aritmatika":
                // SubGeometri
                laySIndex = 7;
                layRIndex = 7;
                layFIndex = 7;
                break;
            case "Barisan dan Deret Geometri":
                // SubGrafik
                laySIndex = 8;
                layRIndex = 8;
                layFIndex = 8;
                break;
            case "Kemiringan":
                // SubKartesius
                laySIndex = 9;
                layRIndex = 9;
                layFIndex = 9;
                break;
            case "Persamaan Garis":
                // SubKemiringan
                laySIndex = 10;
                layRIndex = 10;
                layFIndex = 10;
                break;
            case "Kedudukan Dua Garis":
                // SubPenerapanPythagoras
                laySIndex = 11;
                layRIndex = 11;
                layFIndex = 11;
                break;
            case "Koordinat Cartesius":
                // SubPosisiGaris
                laySIndex = 12;
                layRIndex = 12;
                layFIndex = 12;
                break;
            case "Posisi Titik":
                // SubPosisiTitik
                laySIndex = 13;
                layRIndex = 13;
                layFIndex = 13;
                break;
            case "Posisi Garis":
                // SubPythagoras
                laySIndex = 14;
                layRIndex = 14;
                layFIndex = 14;
                break;
            case "Bentuk SPLDV":
                // SubRumusFungsi
                laySIndex = 15;
                layRIndex = 15;
                layFIndex = 15;
                break;
            case "Metode Eleminasi":
                // SubSegitigaKhusus
                laySIndex = 16;
                layRIndex = 16;
                layFIndex = 16;
                break;
            case "Metode Grafik":
                // SubSubstitusi
                laySIndex = 17;
                layRIndex = 17;
                layFIndex = 17;
                break;
            case "Metode Subtitusi":
                // SubTripelPythagoras
                laySIndex = 18;
                layRIndex = 18;
                layFIndex = 18;
                break;
            case "Penerapan Teorema Pythagoras":
                // SubTripelPythagoras
                laySIndex = 19;
                layRIndex = 19;
                layFIndex = 19;
                break;
        }
    }
}