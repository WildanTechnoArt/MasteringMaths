package com.unpas.masteringmaths.student.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unpas.masteringmaths.R;
import com.unpas.masteringmaths.student.fragment.ClassFragment;
import com.unpas.masteringmaths.student.fragment.HomeStudentFragment;
import com.unpas.masteringmaths.student.fragment.StudentChatFragment;
import com.unpas.masteringmaths.student.fragment.StudentProfileFragment;

public class StudentDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        //Menampilkan halaman Fragment yang pertama kali muncul
        getFragmentPage(new HomeStudentFragment());

        /*Inisialisasi BottomNavigationView beserta listenernya untuk
         *menangkap setiap kejadian saat salah satu menu item diklik
         */
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {

            Fragment fragment = null;

            //Menantukan halaman Fragment yang akan tampil
            switch (item.getItemId()){
                case R.id.home:
                    fragment = new HomeStudentFragment();
                    break;

                case R.id.chat:
                    fragment = new StudentChatFragment();
                    break;

                case R.id.clas:
                    fragment = new ClassFragment();
                    break;

                case R.id.profil:
                    fragment = new StudentProfileFragment();
                    break;

            }
            return getFragmentPage(fragment);
        });
    }

    //Menampilkan halaman Fragment
    private boolean getFragmentPage(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.page_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
