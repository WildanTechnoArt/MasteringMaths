package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;
import com.unpas.masteringmaths.main.GlideApp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class QuizeKordinat extends AppCompatActivity {

    private WebView soal;
    private ArrayList<String> listSoal;
    private int nomor = 1;
    private TextView tvSoalNo, tvAnswerA, tvAnswerB, tvAnswerC, tvAnswerD;
    private ImageView ansImgA, ansImgB, ansImgC, ansImgD;
    private int indext = 0;
    private ArrayList<String> answerA, answerB, answerC, answerD;
    private ArrayList<String> kunciJawaban;
    private LinearLayout getAnsImgA, getAnsImgB, getAnsImgC, getAnsImgD, getAnsTextA, getAnsTextB, getAnsTextC, getAnsTextD;
    private TextView cardImgA, cardImgB, cardImgC, cardImgD, cardTextA, cardTextB, cardTextC, cardTextD;
    private View imgLay, textLay;
    private TextView waktu;
    private CountDownTimer countDownTimer;
    private Boolean kondisi = false;
    private String myAnswer;
    private int point = 0;
    private int benar = 0;
    private int salah = 0;

    public static String MY_POINT = "my_point";
    public static String TRUE_ANSWER = "true_answer";
    public static String FALSE_ANSWER = "false_answer";
    public static String TIMER = "timer";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudent_quize);
        addData();
        soal = findViewById(R.id.content_soal);
        Button btnNext = findViewById(R.id.myanswer);
        tvSoalNo = findViewById(R.id.no_soal);
        tvAnswerA = findViewById(R.id.answerA);
        tvAnswerB = findViewById(R.id.answerB);
        tvAnswerC = findViewById(R.id.answerC);
        tvAnswerD = findViewById(R.id.answerD);
        waktu = findViewById(R.id.waktu);
        ansImgA = findViewById(R.id.img_answer_A);
        ansImgB = findViewById(R.id.img_answer_B);
        ansImgC = findViewById(R.id.img_answer_C);
        ansImgD = findViewById(R.id.img_answer_D);
        imgLay = findViewById(R.id.main_answer_img);
        textLay = findViewById(R.id.main_answer_text);

        getAnsImgA = imgLay.findViewById(R.id.getAnsImgA);
        getAnsImgB = imgLay.findViewById(R.id.getAnsImgB);
        getAnsImgC = imgLay.findViewById(R.id.getAnsImgC);
        getAnsImgD = imgLay.findViewById(R.id.getAnsImgD);
        getAnsTextA = textLay.findViewById(R.id.getAnsTextA);
        getAnsTextB = textLay.findViewById(R.id.getAnsTextB);
        getAnsTextC = textLay.findViewById(R.id.getAnsTextC);
        getAnsTextD = textLay.findViewById(R.id.getAnsTextD);

        cardImgA = imgLay.findViewById(R.id.char_ImgA);
        cardImgB = imgLay.findViewById(R.id.char_ImgB);
        cardImgC = imgLay.findViewById(R.id.char_ImgC);
        cardImgD = imgLay.findViewById(R.id.char_ImgD);
        cardTextA = textLay.findViewById(R.id.char_TextA);
        cardTextB = textLay.findViewById(R.id.char_TextB);
        cardTextC = textLay.findViewById(R.id.char_TextC);
        cardTextD = textLay.findViewById(R.id.char_TextD);

        answerA = new ArrayList<>();
        answerB = new ArrayList<>();
        answerC = new ArrayList<>();
        answerD = new ArrayList<>();

        kunciJawaban = new ArrayList<>();

        addAnswerA();
        addAnswerB();
        addAnswerC();
        addAnswerD();
        addKunciJawaban();

        getAnsImgA.setOnClickListener(view -> {
            myAnswer = "A";
            kondisi = true;
            setColorImg("#FF4480A3","#FFFFFFFF","#FFFFFFFF","#FFFFFFFF",
                    "#FFFFFFFF","#FF757575","#FF757575","#FF757575");
        });

        getAnsImgB.setOnClickListener(view -> {
            myAnswer = "B";
            kondisi = true;
            setColorImg("#FFFFFFFF","#FF4480A3","#FFFFFFFF","#FFFFFFFF",
                    "#FF757575","#FFFFFFFF","#FF757575","#FF757575");
        });

        getAnsImgC.setOnClickListener(view -> {
            myAnswer = "C";
            kondisi = true;
            setColorImg("#FFFFFFFF","#FFFFFFFF","#FF4480A3","#FFFFFFFF",
                    "#FF757575","#FF757575","#FFFFFFFF","#FF757575");
        });

        getAnsImgD.setOnClickListener(view -> {
            myAnswer = "D";
            kondisi = true;
            setColorImg("#FFFFFFFF","#FFFFFFFF","#FFFFFFFF","#FF4480A3",
                    "#FF757575","#FF757575","#FF757575","#FFFFFFFF");
        });

        getAnsTextA.setOnClickListener(view -> {
            myAnswer = "A";
            kondisi = true;
            setColorText("#FF4480A3","#FFFFFFFF","#FFFFFFFF","#FFFFFFFF",
                    "#FFFFFFFF","#FF757575","#FF757575","#FF757575");
        });

        getAnsTextB.setOnClickListener(view -> {
            myAnswer = "B";
            kondisi = true;
            setColorText("#FFFFFFFF","#FF4480A3","#FFFFFFFF","#FFFFFFFF",
                    "#FF757575","#FFFFFFFF","#FF757575","#FF757575");
        });

        getAnsTextC.setOnClickListener(view -> {
            myAnswer = "C";
            kondisi = true;
            setColorText("#FFFFFFFF","#FFFFFFFF","#FF4480A3","#FFFFFFFF",
                    "#FF757575","#FF757575","#FFFFFFFF","#FF757575");
        });

        getAnsTextD.setOnClickListener(view -> {
            myAnswer = "D";
            kondisi = true;
            setColorText("#FFFFFFFF","#FFFFFFFF","#FFFFFFFF","#FF4480A3",
                    "#FF757575","#FF757575","#FF757575","#FFFFFFFF");
        });

        btnNext.setOnClickListener(view -> {
            if (kondisi) {
                defaultColorImg();
                defaultColorText();

                if (myAnswer.equals(kunciJawaban.get(indext))){
                    point += 10;
                    benar += 1;
                }else{
                    salah +=1;
                }

                if (indext < listSoal.size()-1){

                    indext++;
                    nomor++;
                    if (nomor == 3 || nomor == 6 || nomor == 9) {
                        imgLay.setVisibility(View.VISIBLE);
                        textLay.setVisibility(View.GONE);
                        GlideApp.with(getApplicationContext())
                                .load(answerA.get(indext))
                                .into(ansImgA);

                        GlideApp.with(getApplicationContext())
                                .load(answerB.get(indext))
                                .into(ansImgB);

                        GlideApp.with(getApplicationContext())
                                .load(answerC.get(indext))
                                .into(ansImgC);

                        GlideApp.with(getApplicationContext())
                                .load(answerD.get(indext))
                                .into(ansImgD);
                    }
                    else {
                        imgLay.setVisibility(View.GONE);
                        textLay.setVisibility(View.VISIBLE);
                        tvAnswerA.setText(answerA.get(indext));
                        tvAnswerB.setText(answerB.get(indext));
                        tvAnswerC.setText(answerC.get(indext));
                        tvAnswerD.setText(answerD.get(indext));

                    }

                    soal.loadUrl(listSoal.get(indext));
                    tvSoalNo.setText("No Soal : " + nomor + "/10");

                    if (nomor == 10) {
                        btnNext.setText("Selesai");
                    }

                    kondisi = false;
                }else {
                    Intent intent = new Intent(QuizeKordinat.this,StudentResultActivity.class);
                    intent.putExtra(MY_POINT, point);
                    intent.putExtra(TRUE_ANSWER, benar);
                    intent.putExtra(FALSE_ANSWER, salah);
                    intent.putExtra(TIMER, waktu.getText().toString());
                    startActivity(intent);
                    countDownTimer.cancel();
                    finish();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Silahkan Pilih Jawaban Terlebih Dahulu",Toast.LENGTH_SHORT).show();
            }

        });

        tvAnswerA.setText(answerA.get(0));
        tvAnswerB.setText(answerB.get(0));
        tvAnswerC.setText(answerC.get(0));
        tvAnswerD.setText(answerD.get(0));

        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                @SuppressLint("DefaultLocale") String timer = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                waktu.setText(timer);
            }

            public void onFinish() {
                AlertDialog.Builder alert = new AlertDialog.Builder(QuizeKordinat.this);
                alert.setMessage("Waktu Habis!!");
                alert.setPositiveButton("Ok", (dialogInterface, i) -> finish());
                alert.setCancelable(false);
                alert.create();
                alert.show();
            }

        };
        countDownTimer.start();
    }

    private void defaultColorText() {
        cardTextA.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        cardTextA.setTextColor(Color.parseColor("#FF757575"));
        cardTextB.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        cardTextB.setTextColor(Color.parseColor("#FF757575"));
        cardTextC.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        cardTextC.setTextColor(Color.parseColor("#FF757575"));
        cardTextD.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        cardTextD.setTextColor(Color.parseColor("#FF757575"));
    }

    private void defaultColorImg() {
        cardImgA.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        cardImgA.setTextColor(Color.parseColor("#FF757575"));
        cardImgB.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        cardImgB.setTextColor(Color.parseColor("#FF757575"));
        cardImgC.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        cardImgC.setTextColor(Color.parseColor("#FF757575"));
        cardImgD.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        cardImgD.setTextColor(Color.parseColor("#FF757575"));
    }

    private void setColorText(String BackcolorA, String BackcolorB, String BackcolorC, String BackcolorD,
                              String CharcolorA, String CharcolorB, String CharcolorC, String CharcolorD) {
        cardTextA.setBackgroundColor(Color.parseColor(BackcolorA));
        cardTextA.setTextColor(Color.parseColor(CharcolorA));
        cardTextB.setBackgroundColor(Color.parseColor(BackcolorB));
        cardTextB.setTextColor(Color.parseColor(CharcolorB));
        cardTextC.setBackgroundColor(Color.parseColor(BackcolorC));
        cardTextC.setTextColor(Color.parseColor(CharcolorC));
        cardTextD.setBackgroundColor(Color.parseColor(BackcolorD));
        cardTextD.setTextColor(Color.parseColor(CharcolorD));
    }

    private void setColorImg(String BackcolorA, String BackcolorB, String BackcolorC, String BackcolorD,
                             String CharcolorA, String CharcolorB, String CharcolorC, String CharcolorD) {
        cardImgA.setBackgroundColor(Color.parseColor(BackcolorA));
        cardImgA.setTextColor(Color.parseColor(CharcolorA));
        cardImgB.setBackgroundColor(Color.parseColor(BackcolorB));
        cardImgB.setTextColor(Color.parseColor(CharcolorB));
        cardImgC.setBackgroundColor(Color.parseColor(BackcolorC));
        cardImgC.setTextColor(Color.parseColor(CharcolorC));
        cardImgD.setBackgroundColor(Color.parseColor(BackcolorD));
        cardImgD.setTextColor(Color.parseColor(CharcolorD));
    }

    @Override
    protected void onStart() {
        super.onStart();
        soal.loadUrl(listSoal.get(indext));
    }

    private void addData(){
        listSoal = new ArrayList<>();
        for (int a = 1; a<=10; a++){
            listSoal.add("file:///android_asset/soal_kartesius_"+a+".html");
        }
    }

    private void addAnswerA() {
        answerA.add("(6, 8)");
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fbidang_kartesiusA.png?alt=media&token=f486989f-0284-4bf7-b806-925fd9a2d192");
        answerA.add("(-1, 1), (-6,1) dan (-4, 4)");
        answerA.add("(6, 5)");
        answerA.add("(6, 5)");
        answerA.add("(4,3)");
        answerA.add("(4, 6)");
        answerA.add("(-2, 7)");
        answerA.add("A(0,2)");
        answerA.add("Jarak tenda regu harimau dan regu singa sama dengan jarak tiang bendera ke tenda regu serigala");
    }

    private void addAnswerB() {
        answerB.add("(8, 6)");
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fbidang_kartesiusB.png?alt=media&token=d79db130-757c-41d5-9e5d-8187271f85f9");
        answerB.add("(2, 3), (2, 8) dan (7, 6)");
        answerB.add("(7, 5)");
        answerB.add("(4, 5)");
        answerB.add("(-3,-4)");
        answerB.add("(4, -6)");
        answerB.add("(-2, -7)");
        answerB.add("B(1,5)");
        answerB.add("Jarak tenda regu harimau dan regu singa adalah dua kali jarak tiang bendera ke tenda regu serigala");
    }

    private void addAnswerC() {
        answerC.add("(8, 8)");
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fbidang_kartesiusC.png?alt=media&token=3d150c0e-ff45-4000-bfd5-d9872fa5343d");
        answerC.add("(-1, 1), (6, 1) dan (-4, 4)");
        answerC.add("(3, 5)");
        answerC.add("(6, 1)");
        answerC.add("(-4,3)");
        answerC.add("(-4, 6)");
        answerC.add("(-7, 2)");
        answerC.add("(-2,2)");
        answerC.add("Jarak tenda regu harimau dan regu singa adalah tiga kali jarak tiang bendera ke tenda regu serigala");
    }

    private void addAnswerD() {
        answerD.add("(6, 6)");
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fbidang_kartesiusD.png?alt=media&token=979bfa1c-47de-46e4-8044-144a039afc39");
        answerD.add("(2, 3), (2, 8) dan (6, 7)");
        answerD.add("(3, -5)");
        answerD.add("(4, 1)");
        answerD.add("(-4,-3)");
        answerD.add("(-4, -6)");
        answerD.add("(-7, -2)");
        answerD.add("D(3,-3)");
        answerD.add("Jarak tenda regu harimau dan regu singa adalah jarak tiang bendera ke tenda regu serigala");
    }

    private void addKunciJawaban(){
        kunciJawaban.add("B");
        kunciJawaban.add("B");
        kunciJawaban.add("A");
        kunciJawaban.add("B");
        kunciJawaban.add("D");
        kunciJawaban.add("D");
        kunciJawaban.add("D");
        kunciJawaban.add("B");
        kunciJawaban.add("D");
        kunciJawaban.add("B");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }
}