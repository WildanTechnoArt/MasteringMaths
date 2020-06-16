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
import com.unpas.masteringmaths.main.GlideApp;

import java.util.ArrayList;

public class ExamFungsiActivity extends AppCompatActivity {

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
                    if (nomor == 2 || nomor == 3 || nomor == 4) {
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
                    Intent intent = new Intent(ExamFungsiActivity.this,StudentResultExamActivity.class);
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
            listSoal.add("file:///android_asset/soal_fungsi_"+a+".html");
        }
    }

    private void addAnswerA() {
        answerA.add("(i) dan (ii)");
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_1_a.png?alt=media&token=cee53ef3-a956-4df5-b550-fa06cb8c0c25");
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_3_a.png?alt=media&token=8c7d7e5e-dd87-4330-b030-654e238bc927");
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_4_a.png?alt=media&token=42be81ec-81b5-4b3f-a775-0ae1ed7bc110");
        answerA.add("{a,b}");
        answerA.add("{x│0≤x≤7, x∈ℤ}");
        answerA.add("{−2, −1, 0}");
        answerA.add("PPKn, Bahasa Indonesia, Bahasa Inggris");
        answerA.add("(3,2)");
        answerA.add("Rp. 150.000,-");
    }

    private void addAnswerB() {
        answerB.add("(i) dan (iii)");
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_1_b.png?alt=media&token=88b1e698-4306-442a-aaea-a5e5deef3c54");
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_3_b.png?alt=media&token=cde8a6d5-b67d-4190-a7d3-aab36f9760ad");
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_4_b.png?alt=media&token=366879b8-265a-4341-92cc-46ed20df8f72");
        answerB.add("{1,2,3,4}");
        answerB.add("{x│0≤x<7, x∈ℤ}");
        answerB.add("{−2, −1, 1}");
        answerB.add("Matematika, IPS, Seni Budaya");
        answerB.add("(4,2)");
        answerB.add("Rp. 200.000,-");
    }

    private void addAnswerC() {
        answerC.add("(ii) dan (iii)");
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_1_c.png?alt=media&token=9bd7100c-637d-40a4-a9c3-506c43372212");
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_3_c.png?alt=media&token=57db4556-9260-4e1b-8af5-983fecf7f564");
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_4_c.png?alt=media&token=199ebdf6-cde4-45d5-b08d-15edefae12ae");
        answerC.add("{a,b,c,d}");
        answerC.add("{y│−5≤y≤10, y∈ℤ}");
        answerC.add("{−6, −1, 0}");
        answerC.add("Matematika, IPS, Olah Raga");
        answerC.add("(3,1)");
        answerC.add("Rp. 300.000,-");
    }

    private void addAnswerD() {
        answerD.add("(iii) dan (iv)");
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_1_d.png?alt=media&token=41d52d3a-8700-4ca8-922e-c0aa0a698fc8");
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_3_d.png?alt=media&token=f5aa5d9f-df41-41c8-adba-7d13a4a61af8");
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_4_d.png?alt=media&token=67cf17b5-7440-42e5-92ce-786d912b34e3");
        answerD.add("{(1, a,2, b,3, c,4, d}");
        answerD.add("{y│−5<y≤10, y∈ℤ}");
        answerD.add("{−6, 0, 1}");
        answerD.add("Matematika, Bahasa Indonesia, PPKn");
        answerD.add("(7,1)");
        answerD.add("Rp. 400.000,-");
    }

    private void addKunciJawaban(){
        kunciJawaban.add("C");
        kunciJawaban.add("B");
        kunciJawaban.add("D");
        kunciJawaban.add("A");
        kunciJawaban.add("B");
        kunciJawaban.add("C");
        kunciJawaban.add("C");
        kunciJawaban.add("C");
        kunciJawaban.add("C");
        kunciJawaban.add("C");

    }
}
