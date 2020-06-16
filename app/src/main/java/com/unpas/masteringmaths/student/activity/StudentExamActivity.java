package com.unpas.masteringmaths.student.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.unpas.masteringmaths.R;
import com.unpas.masteringmaths.main.GlideApp;

import java.util.ArrayList;

public class StudentExamActivity extends AppCompatActivity {

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
                    Intent intent = new Intent(StudentExamActivity.this,StudentResultExamActivity.class);
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
            listSoal.add("file:///android_asset/relasi_soal_"+a+".html");
        }
    }

    private void addAnswerA() {
        answerA.add("akar dari");
        answerA.add("{(3,2), (4,2), (5,2)}");
        //jawaban no 3 dengan gambar
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_3_a.png?alt=media&token=cad5a911-3925-47dc-956f-f274bc74be75");
        answerA.add("{(1,3),(3,3),(1,4),(2,4),(4,4),(1,5),(5,5),(1,6),(2,6),(6,3),(6,6),(1,8),(2,8),(4,8)}");
        answerA.add("daerah di Indonesia");
        //jawaban no 6 dengan gambar
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_6_a.png?alt=media&token=bf67d0dd-1fcb-4901-8c92-f29b164b203d");
        answerA.add("A ke B");
        answerA.add("{(Badminton, Annisa), (Badminton, Derrall), (Badminton, Ataya), (Sepak bola, Hafidz), (Sepak bola, Wildan), (Badminton, Azis), (Basket, Azis)}");
        //gambar no 9 dengan gambar
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_9_a.png?alt=media&token=f326bac2-f04f-4507-b23c-d9e5aaa696d7");
        answerA.add("Tami");
    }

    private void addAnswerB() {
        answerB.add("faktor dari");
        answerB.add("{(3,5), (4,6), (5,7)}");
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_3_b.png?alt=media&token=a2eb98c9-1bd3-4cfe-b60e-e8149698e9f7");
        answerB.add("{(1,3),(3,3),(1,4),(2,4),(4,4),(1,5),(5,5),(1,6),(2,6),(3,6),(6,6),(1,8),(3,8),(4,8),(6,8)}");
        answerB.add("daerah asal tari");
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_6_b.png?alt=media&token=e301446d-a293-4ec3-857f-2edbebfa4b39");
        answerB.add("B ke C");
        answerB.add("{(Annisa, Badminton), (Derrall, Badminton), (Ataya, Badminton), (Hafidz, Sepak bola), (Wildan, Sepak bola), (Azis, Badminton),      (Azis, Basket)}");
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_9_b.png?alt=media&token=1ac3fa44-d738-40c7-b082-982552fd6d37");
        answerB.add("Nengsih");
    }

    private void addAnswerC() {
        answerC.add("lebih dari");
        answerC.add("{(3,4), (4,5), (5,6)}");
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_3_c.png?alt=media&token=59b44c66-9ace-46bd-9169-5717a6118c92");
        answerC.add("{(1,3),(3,3),(1,4),(2,4),(4,4),(1,5),(5,5),(1,6),(2,6),(3,6),(6,6),(1,8),(2,8),(4,8)}");
        answerC.add("tari dari");
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_6_c.png?alt=media&token=04a92038-0068-4561-8caf-5b028e8dc9d5");
        answerC.add("A ke D");
        answerC.add("{(Badminton, Annisa), (Badminton, Derrall), (Badminton, Ataya), (Badminton, Hafidz), (Sepak bola, Wildan), (Sepak bola, Azis), (Basket, Azis)}");
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_9_c.png?alt=media&token=608b23a4-55bc-4f15-ae5f-ddadaa7677ec");
        answerC.add("Kinanti");
    }

    private void addAnswerD() {
        answerD.add("jumlah dari");
        answerD.add("{(3,1), (4,2), (5,3)}");
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_3_d.png?alt=media&token=afa3a2f5-062b-4f71-b963-930641a3ddb4");
        answerD.add("{(1,3),(3,3),(1,4),(2,4),(4,4),(1,5),(5,5),(1,6),(2,6),(3,6),(6,6),(1,8),(2,8),(4,8),(6,8)}");
        answerD.add("himpunan nama daerah dan tariannya");
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_6_d.png?alt=media&token=7686c941-3f85-4a77-9528-22aca279e319");
        answerD.add("D ke A");
        answerD.add("{(Annisa, Badminton), (Derrall, Badminton), (Ataya, Badminton), (Hafidz, Sepak bola), (Wildan, Sepak bola), (Azis, Sepak bola),       (Azis, Basket)}");
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_9_d.png?alt=media&token=f99c0a3e-3285-443b-bcb0-fe72af42efb9");
        answerD.add("Suci");
    }

    private void addKunciJawaban(){
        kunciJawaban.add("B");
        kunciJawaban.add("B");
        kunciJawaban.add("A");
        kunciJawaban.add("C");
        kunciJawaban.add("B");
        kunciJawaban.add("C");
        kunciJawaban.add("C");
        kunciJawaban.add("A");
        kunciJawaban.add("D");
        kunciJawaban.add("D");

    }

}
