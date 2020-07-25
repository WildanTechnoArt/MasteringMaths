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

public class QuizePythagorasActivity extends AppCompatActivity {

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
        Button btnNext = findViewById(R.id.btn_answer);
        tvSoalNo = findViewById(R.id.no_soal);
        tvAnswerA = findViewById(R.id.tv_answerA);
        tvAnswerB = findViewById(R.id.tv_answerB);
        tvAnswerC = findViewById(R.id.tv_answerC);
        tvAnswerD = findViewById(R.id.tv_answerD);
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
                    if (nomor == 0 || nomor == 0 || nomor == 0) {
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
                    Intent intent = new Intent(QuizePythagorasActivity.this,StudentResultActivity.class);
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
                AlertDialog.Builder alert = new AlertDialog.Builder(QuizePythagorasActivity.this);
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
            listSoal.add("file:///android_asset/soal_pythagoras_"+a+".html");
        }
    }

    private void addAnswerA() {
        answerA.add("Jika \uD835\uDC5F²=\uD835\uDC5D²+\uD835\uDC5E², besar ∠P = 90º");
        answerA.add("13 m");
        //jawaban no 3 dengan gambar
        answerA.add("24 cm");
        answerA.add("290 \uD835\uDC50\uD835\uDC5A²");
        answerA.add("2 cm");
        //jawaban no 6 dengan gambar
        answerA.add("25 cm");
        answerA.add("3");
        answerA.add("19");
        //gambar no 9 dengan gambar
        answerA.add("√1000");
        answerA.add("30 cm");
    }

    private void addAnswerB() {
        answerB.add("Jika \uD835\uDC5F²=\uD835\uDC5D²−\uD835\uDC5E², besar ∠R = 90º");
        answerB.add("12 m");
        answerB.add("25 cm");
        answerB.add("294 \uD835\uDC50\uD835\uDC5A²");
        answerB.add("2,1 cm");
        answerB.add("24 cm");
        answerB.add("4");
        answerB.add("18");
        answerB.add("√130");
        answerB.add("40 cm");
    }

    private void addAnswerC() {
        answerC.add("Jika \uD835\uDC5F²=\uD835\uDC5E²−\uD835\uDC5D², besar ∠ Q = 90º");
        answerC.add("11 m");
        answerC.add("26 cm");
        answerC.add("490 \uD835\uDC50\uD835\uDC5A²");
        answerC.add("2,4 cm");
        answerC.add("17 cm");
        answerC.add("5");
        answerC.add("17");
        answerC.add("√216,67");
        answerC.add("48 cm");
    }

    private void addAnswerD() {
        answerD.add("Jika \uD835\uDC5D²=\uD835\uDC5E²+\uD835\uDC5F², besar ∠P = 90º");
        answerD.add("10 m");
        answerD.add("27 cm");
        answerD.add("494 \uD835\uDC50\uD835\uDC5A²");
        answerD.add("2,9 cm");
        answerD.add("15 cm");
        answerD.add("6");
        answerD.add("16");
        answerD.add("√260");
        answerD.add("52 cm");
    }

    private void addKunciJawaban(){
        kunciJawaban.add("D");
        kunciJawaban.add("A");
        kunciJawaban.add("A");
        kunciJawaban.add("B");
        kunciJawaban.add("A");
        kunciJawaban.add("C");
        kunciJawaban.add("B");
        kunciJawaban.add("C");
        kunciJawaban.add("A");
        kunciJawaban.add("D");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }
}