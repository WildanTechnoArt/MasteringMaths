package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unpas.masteringmaths.R;
import com.unpas.masteringmaths.main.GlideApp;

import java.util.ArrayList;

public class ExamDuaGaris extends AppCompatActivity {

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
        Button btnNext = findViewById(R.id.myanswer);
        tvSoalNo = findViewById(R.id.no_soal);
        tvAnswerA = findViewById(R.id.answerA);
        tvAnswerB = findViewById(R.id.answerB);
        tvAnswerC = findViewById(R.id.answerC);
        tvAnswerD = findViewById(R.id.answerD);
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
        soal.getSettings().setBuiltInZoomControls(true);

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
            setColorImg("#FF4480A3", "#FFFFFFFF", "#FFFFFFFF", "#FFFFFFFF",
                    "#FFFFFFFF", "#FF757575", "#FF757575", "#FF757575");
        });

        getAnsImgB.setOnClickListener(view -> {
            myAnswer = "B";
            kondisi = true;
            setColorImg("#FFFFFFFF", "#FF4480A3", "#FFFFFFFF", "#FFFFFFFF",
                    "#FF757575", "#FFFFFFFF", "#FF757575", "#FF757575");
        });

        getAnsImgC.setOnClickListener(view -> {
            myAnswer = "C";
            kondisi = true;
            setColorImg("#FFFFFFFF", "#FFFFFFFF", "#FF4480A3", "#FFFFFFFF",
                    "#FF757575", "#FF757575", "#FFFFFFFF", "#FF757575");
        });

        getAnsImgD.setOnClickListener(view -> {
            myAnswer = "D";
            kondisi = true;
            setColorImg("#FFFFFFFF", "#FFFFFFFF", "#FFFFFFFF", "#FF4480A3",
                    "#FF757575", "#FF757575", "#FF757575", "#FFFFFFFF");
        });

        getAnsTextA.setOnClickListener(view -> {
            myAnswer = "A";
            kondisi = true;
            setColorText("#FF4480A3", "#FFFFFFFF", "#FFFFFFFF", "#FFFFFFFF",
                    "#FFFFFFFF", "#FF757575", "#FF757575", "#FF757575");
        });

        getAnsTextB.setOnClickListener(view -> {
            myAnswer = "B";
            kondisi = true;
            setColorText("#FFFFFFFF", "#FF4480A3", "#FFFFFFFF", "#FFFFFFFF",
                    "#FF757575", "#FFFFFFFF", "#FF757575", "#FF757575");
        });

        getAnsTextC.setOnClickListener(view -> {
            myAnswer = "C";
            kondisi = true;
            setColorText("#FFFFFFFF", "#FFFFFFFF", "#FF4480A3", "#FFFFFFFF",
                    "#FF757575", "#FF757575", "#FFFFFFFF", "#FF757575");
        });

        getAnsTextD.setOnClickListener(view -> {
            myAnswer = "D";
            kondisi = true;
            setColorText("#FFFFFFFF", "#FFFFFFFF", "#FFFFFFFF", "#FF4480A3",
                    "#FF757575", "#FF757575", "#FF757575", "#FFFFFFFF");
        });

        btnNext.setOnClickListener(view -> {
            if (kondisi) {
                defaultColorImg();
                defaultColorText();

                if (myAnswer.equals(kunciJawaban.get(indext))) {
                    Toast.makeText(getApplicationContext(), "Benar", Toast.LENGTH_SHORT).show();
                    point += 10;
                    benar += 1;
                } else {
                    salah += 1;
                    Toast.makeText(getApplicationContext(), "Salah", Toast.LENGTH_SHORT).show();
                }

                if (indext < listSoal.size() - 1) {

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
                } else {
                    Intent intent = new Intent(ExamDuaGaris.this, StudentResultExamActivity.class);
                    intent.putExtra(MY_POINT, point);
                    intent.putExtra(TRUE_ANSWER, benar);
                    intent.putExtra(FALSE_ANSWER, salah);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan Pilih Jawaban Terlebih Dahulu", Toast.LENGTH_SHORT).show();
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

    private void addData() {
        listSoal = new ArrayList<>();
        for (int a = 1; a <= 10; a++) {
            listSoal.add("file:///android_asset/soal_dua_garis_" + a + ".html");
        }
    }

    private void addAnswerA() {
        answerA.add("y = -x + 9");
        answerA.add("2y + 3x = 6");
        answerA.add("y = - 3/4 x + 5");
        answerA.add("3y - 2x - 11 = 0");
        answerA.add("y = x - 4");
        answerA.add("3y + x = 14");
        answerA.add("2y + 3x = -21");
        answerA.add("2x + y - 6 = 0");
        answerA.add("(18, 0)");
        answerA.add("2y - 5x = -16");
    }

    private void addAnswerB() {
        answerB.add("y = x - 9");
        answerB.add("-2y + 3x = 6");
        answerB.add("y = 3/4 x + 5");
        answerB.add("-3y - 2x - 11 = 0");
        answerB.add("y = x + 4");
        answerB.add("3y + x = -14");
        answerB.add("2y - 3x = 21");
        answerB.add("2x - y - 6 = 0");
        answerB.add("(22, 0)");
        answerB.add("2y - 5x = 16");
    }

    private void addAnswerC() {
        answerC.add("y = -x - 9");
        answerC.add("2y + 3x = -6");
        answerC.add("y = - 3/4 x - 7");
        answerC.add("3y + 2x - 11 = 0");
        answerC.add("y = -x - 4");
        answerC.add("3y - x = 14");
        answerC.add("2y + 3x = 21");
        answerC.add("2x - y + 6 = 0");
        answerC.add("(25, 0)");
        answerC.add("2y + 5x = -16");
    }

    private void addAnswerD() {
        answerD.add("y = x + 9");
        answerD.add("2y - 3x = -6");
        answerD.add("y = 3/4 x - 7");
        answerD.add("3y - 2x + 11 = 0");
        answerD.add("y = -x + 4");
        answerD.add("3y - x = -14");
        answerD.add("2y - 3x = -21");
        answerD.add("2x + y + 6 = 0");
        answerD.add("(29, 0)");
        answerD.add("2y + 5x = 16");
    }

    private void addKunciJawaban() {
        kunciJawaban.add("A");
        kunciJawaban.add("D");
        kunciJawaban.add("B");
        kunciJawaban.add("C");
        kunciJawaban.add("C");
        kunciJawaban.add("D");
        kunciJawaban.add("C");
        kunciJawaban.add("A");
        kunciJawaban.add("C");
        kunciJawaban.add("D");

    }
}
