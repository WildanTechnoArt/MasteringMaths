package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;

import java.util.ArrayList;

import static com.unpas.masteringmaths.utils.UtilsConstant.NUMBER_INDEX;

public class MateriActivity extends AppCompatActivity {

    private WebView Materi;
    private ArrayList<String> listSlide;
    private int nomor = 1;
    private TextView tvMateriNo;
    private int position;
    private int indext = 0;
    private  Button btnNext, btnprev;
    private Boolean kondisi = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_materi);

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
                tvMateriNo.setText("Slide Materi : 1/8");
                for (int a = 1; a <= 8; a++) {
                    listSlide.add("file:///android_asset/materi_relasi_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/8");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/8");
                    }
                });
                break;
            case 1:
                tvMateriNo.setText("Slide Materi : 1/9");
                for (int a = 1; a<=9; a++){
                    listSlide.add("file:///android_asset/rumus_fungsi_"+a+".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/9");

                        if (nomor==9) {
                            btnNext.setText("Selesai");
                        }
                        kondisi = false;
                    }else {
                        showDialogRumusFungsi();
                    }

                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/9");
                    }
                });
                break;
            case 2:
                tvMateriNo.setText("Slide Materi : 1/12");
                for (int a = 1; a <= 12; a++) {
                    listSlide.add("file:///android_asset/materi_fungsi_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/12");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/12");
                    }
                });
                break;
            case 3:
                tvMateriNo.setText("Slide Materi : 1/4");
                for (int a = 1; a <= 4; a++) {
                    listSlide.add("file:///android_asset/materi_pythagoras_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/4");
                        if (nomor==8) {
                            btnNext.setText("Selesai");
                        }
                        kondisi = false;
                    }else {

                        showDialogTeorema();

                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/4");
                    }
                });
                break;
            case 4:
                tvMateriNo.setText("Slide Materi : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/materi_segitiga_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                break;
            case 5:
                tvMateriNo.setText("Slide Materi : 1/3");
                for (int a = 1; a<=3; a++){
                    listSlide.add("file:///android_asset/materi_tripel_"+a+".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");

                        if (nomor==8) {
                            btnNext.setText("Selesai");
                        }
                        kondisi = false;
                    }else {

                        showDialogTripelPythagoras();

                    }

                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                    }
                });
                break;
            case 6:
                tvMateriNo.setText("Slide Materi : 1/4");
                for (int a = 1; a <= 4; a++) {
                    listSlide.add("file:///android_asset/materi_baris_bilangan_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/4");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/4");
                    }
                });
                break;
            case 7:
                tvMateriNo.setText("Slide Materi : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/materi_aritmetika_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                break;
            case 8:
                tvMateriNo.setText("Slide Materi : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/materi_geometri_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                break;
            case 9:
                tvMateriNo.setText("Slide Materi : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/materi_kemiringan_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                break;
            case 10:
                tvMateriNo.setText("Slide Materi : 1/11");
                for (int a = 1; a <= 11; a++) {
                    listSlide.add("file:///android_asset/materi_garislurus_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/11");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                break;
            case 11:
                tvMateriNo.setText("Slide Materi : 1/9");
                for (int a = 1; a<=9; a++){
                    listSlide.add("file:///android_asset/materi_duagaris_"+a+".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/9");

                        if (nomor==8) {
                            btnNext.setText("Selesai");
                        }
                        kondisi = false;
                    }else {

                        showDialog();

                    }

                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/9");
                    }
                });
                break;
            case 12:
                tvMateriNo.setText("Slide Materi : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/materi_kartesius_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                break;
            case 13:
                tvMateriNo.setText("Slide Materi : 1/3");
                for (int a = 1; a <= 3; a++) {
                    listSlide.add("file:///android_asset/materi_posisi_titik_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                    }
                });
                break;
            case 14:
                tvMateriNo.setText("Slide Materi : 1/5");
                for (int a = 1; a <= 5; a++) {
                    listSlide.add("file:///android_asset/materi_posisi_garis_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size() - 1) {
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/5");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0) {
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/5");
                    }
                });
                break;
            case 15:
                tvMateriNo.setText("Slide Materi : 1/8");
                for (int a = 1; a <= 8; a++) {
                    listSlide.add("file:///android_asset/materi_spldv_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/8");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/8");
                    }
                });
                break;
            case 16:
                tvMateriNo.setText("Slide Materi : 1/3");
                for (int a = 1; a <= 3; a++) {
                    listSlide.add("file:///android_asset/materi_eliminasi_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                    }
                });
                break;
            case 17:
                tvMateriNo.setText("Slide Materi : 1/4");
                for (int a = 1; a <= 4; a++) {
                    listSlide.add("file:///android_asset/materi_grafik_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/4");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/4");
                    }
                });
                break;
            case 18:
                tvMateriNo.setText("Slide Materi : 1/6");
                for (int a = 1; a <= 6; a++) {
                    listSlide.add("file:///android_asset/materi_substitusi_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/6");
                    }
                });
                break;

            case 19:
                tvMateriNo.setText("Slide Materi : 1/3");
                for (int a = 1; a <= 3; a++) {
                    listSlide.add("file:///android_asset/materi_penerapan_" + a + ".html");
                }
                btnNext.setOnClickListener(view -> {
                    if (indext < listSlide.size()-1){
                        indext++;
                        nomor++;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                    }
                });
                btnprev.setOnClickListener(view -> {
                    if (indext > 0 ){
                        indext--;
                        nomor--;
                        Materi.loadUrl(listSlide.get(indext));
                        tvMateriNo.setText("Slide Materi : " + nomor + "/3");
                    }
                });
                break;
        }
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Ingin Mencoba Latihan Soal?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Lanjut untuk melanjutkan!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Lanjut", (dialog, id) -> {
                    // jika tombol diklik, maka akan menutup activity ini
                    Intent intent = new Intent(MateriActivity.this, ExamRumusFungsi.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Tidak", (dialog, id) -> {
                    // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    MateriActivity.this.finish();
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void showDialogRumusFungsi(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Ingin Mencoba Latihan Soal?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Lanjut untuk melanjutkan!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Lanjut", (dialog, id) -> {
                    // jika tombol diklik, maka akan menutup activity ini
                    Intent intent = new Intent(MateriActivity.this,ExamRumusFungsi.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Tidak", (dialog, id) -> {
                    // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    MateriActivity.this.finish();
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void showDialogSegtigaPythagoras(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Ingin Mencoba Latihan Soal?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Lanjut untuk melanjutkan!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Lanjut", (dialog, id) -> {
                    // jika tombol diklik, maka akan menutup activity ini
                    Intent intent = new Intent(MateriActivity.this,ExamSegitigaPythagoras.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Tidak", (dialog, id) -> {
                    // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    MateriActivity.this.finish();
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void showDialogTeorema(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Ingin Mencoba Latihan Soal?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Lanjut untuk melanjutkan!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Lanjut", (dialog, id) -> {
                    // jika tombol diklik, maka akan menutup activity ini
                    Intent intent = new Intent(MateriActivity.this,ExamTeorema.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Tidak", (dialog, id) -> {
                    // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    MateriActivity.this.finish();
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void showDialogTripelPythagoras(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Ingin Mencoba Latihan Soal?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Lanjut untuk melanjutkan!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Lanjut", (dialog, id) -> {
                    // jika tombol diklik, maka akan menutup activity ini
                    Intent intent = new Intent(MateriActivity.this,ExamTripelPythagoras.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Tidak", (dialog, id) -> {
                    // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    MateriActivity.this.finish();
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}