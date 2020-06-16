package com.unpas.masteringmaths.main.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.activity.StudentDashboardActivity
import com.unpas.masteringmaths.teacher.activity.TeacherDashboardActivity
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.PLAY_SERVICES_RESOLUTION_REQUEST
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private lateinit var mAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        checkPlayServices()
        prepare()
        screenProgress()
    }

    private fun prepare() {
        mAnalytics = FirebaseAnalytics.getInstance(this@SplashScreenActivity)
        mAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
        mAuth = FirebaseAuth.getInstance()
        GlideApp.with(this)
            .load(R.drawable.logo_not_background)
            .into(img_app_name)
    }

    private fun screenProgress() {
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                val userId = user.uid

                val db = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .get()

                db.addOnSuccessListener { result ->
                    if (result.getBoolean("teacher") == true) {
                        startActivity(Intent(this, TeacherDashboardActivity::class.java))
                        finishAffinity()
                    } else {
                        startActivity(Intent(this, StudentDashboardActivity::class.java))
                        finishAffinity()
                    }
                }
            } else {
                val thread = object : Thread() {
                    override fun run() {
                        try {
                            sleep(1500)
                        } catch (ex: InterruptedException) {
                            ex.printStackTrace()
                        } finally {
                            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                            finish()
                        }
                    }
                }
                thread.start()
            }
        }
    }

    private fun checkPlayServices() {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(this)
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(
                    this, result,
                    PLAY_SERVICES_RESOLUTION_REQUEST
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }
}