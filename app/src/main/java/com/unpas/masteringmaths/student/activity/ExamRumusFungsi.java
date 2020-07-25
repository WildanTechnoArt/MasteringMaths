package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.unpas.masteringmaths.R;

import java.util.ArrayList;

public class ExamRumusFungsi extends AppCompatActivity {

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
    private Boolean kondisi = false;
    private String myAnswer;
    private int point = 0;
    private int benar = 0;
    private int salah = 0;

    public static String MY_POINT = "my_point";
    public static String TRUE_ANSWER = "true_answer";
    public static String FALSE_ANSWER = "false_answer";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_exam);
        addData();
        soal = findViewById(R.id.content_soal);
        Button btnNext = findViewById(R.id.btn_answer);
        tvSoalNo = findViewById(R.id.no_soal);
        tvAnswerA = findViewById(R.id.tv_answerA);
        tvAnswerB = findViewById(R.id.tv_answerB);
        tvAnswerC = findViewById(R.id.tv_answerC);
        tvAnswerD = findViewById(R.id.tv_answerD);
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
                defaultColorText();

                if (myAnswer.equals(kunciJawaban.get(indext))){
                    Toast.makeText(getApplicationContext(),"Benar",Toast.LENGTH_SHORT).show();
                    point += 10;
                    benar += 1;
                }else{
                    salah +=1;
                    Toast.makeText(getApplicationContext(),"Salah",Toast.LENGTH_SHORT).show();
                }

                if (indext < listSoal.size()-1){
                    indext++;
                    nomor++;

                    imgLay.setVisibility(View.GONE);
                    textLay.setVisibility(View.VISIBLE);
                    tvAnswerA.setText(answerA.get(indext));
                    tvAnswerB.setText(answerB.get(indext));
                    tvAnswerC.setText(answerC.get(indext));
                    tvAnswerD.setText(answerD.get(indext));
                    soal.loadUrl(listSoal.get(indext));
                    tvSoalNo.setText("No Soal : " + nomor + "/10");

                    if (nomor == 10) {
                        btnNext.setText("Selesai");
                    }

                    kondisi = false;
                }else {
                    Intent intent = new Intent(ExamRumusFungsi.this,StudentResultExamActivity.class);
                    intent.putExtra(MY_POINT, point);
                    intent.putExtra(TRUE_ANSWER, benar);
                    intent.putExtra(FALSE_ANSWER, salah);
                    startActivity(intent);
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

    @Override
    protected void onStart() {
        super.onStart();
        soal.loadUrl(listSoal.get(indext));
    }

    private void addData(){
        listSoal = new ArrayList<>();
        for (int a = 1; a<=10; a++){
            listSoal.add("file:///android_asset/soal_rumus_"+a+".html");
        }
    }

    private void addAnswerA() {
        answerA.add("ℎ(x)=−x");
        answerA.add("-1");
        answerA.add("-14");
        answerA.add("-3");
        answerA.add("-5");
        answerA.add("-9");
        answerA.add("f(x)=−3−2x^2");
        answerA.add("8 meter");
        answerA.add("5 liter");
        answerA.add("Rp. 102.000.000,-");

    }

    private void addAnswerB() {
        answerB.add("ℎ(x)=x^2");
        answerB.add("0");
        answerB.add("-11");
        answerB.add("3");
        answerB.add("0");
        answerB.add("-2");
        answerB.add("f(x)=3-2x^2");
        answerB.add("16 meter ");
        answerB.add("6 liter");
        answerB.add("Rp. 250.000.000,-");

    }

    private void addAnswerC() {
        answerC.add("ℎ(x)=√x");
        answerC.add("1");
        answerC.add("3");
        answerC.add("6");
        answerC.add("5");
        answerC.add("7");
        answerC.add("f(x)=2+3x^2");
        answerC.add("32 meter ");
        answerC.add("12 liter");
        answerC.add("Rp. 350.000.000,-");

    }

    private void addAnswerD() {
        answerD.add("ℎ(x)=2x");
        answerD.add("2");
        answerD.add("14");
        answerD.add("9");
        answerD.add("10");
        answerD.add("16");
        answerD.add("f(x)=3+2x^2");
        answerD.add("48 meter ");
        answerD.add("24 liter");
        answerD.add("Rp. 352.000.000,-");

    }

    private void addKunciJawaban(){
        kunciJawaban.add("B");
        kunciJawaban.add("B");
        kunciJawaban.add("A");
        kunciJawaban.add("C");
        kunciJawaban.add("A");
        kunciJawaban.add("B");
        kunciJawaban.add("D");
        kunciJawaban.add("B");
        kunciJawaban.add("A");
        kunciJawaban.add("D");

    }
}
