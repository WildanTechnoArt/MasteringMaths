package com.unpas.masteringmaths.student.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.teacher.presenter.PostPresenter
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.teacher.view.PostView
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.GET_POST
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_EDITED
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TOOLBAR_TITLE
import kotlinx.android.synthetic.main.activity_post.*

class StudentPostActivity : AppCompatActivity(), PostView.View {

    private lateinit var presenter: PostView.Presenter
    private lateinit var userId: String
    private lateinit var teacherId: String
    private lateinit var username: String
    private lateinit var nip: String
    private lateinit var classId: String
    private lateinit var photoUrl: String
    private lateinit var className: String
    private var isEdited: Boolean? = false
    private lateinit var getPostId: String
    private lateinit var getPostContent: String
    private var post: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        prepare()
        swipe_refresh.setOnRefreshListener {
            presenter.requestPhoto(userId)
            presenter.requestProfile(userId)
        }
    }

    private fun prepare() {
        setSupportActionBar(toolbar)
        val title = intent?.getStringExtra(TOOLBAR_TITLE).toString()
        supportActionBar?.apply {
            setTitle(title)
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        presenter = PostPresenter(this, this)
        userId = SharedPrefManager.getInstance(this).getUserId.toString()
        teacherId = intent?.getStringExtra(TEACHER_ID).toString()
        classId = intent?.getStringExtra(CLASS_ID).toString()
        className = intent?.getStringExtra(CLASS_NAME).toString()
        isEdited = intent?.getBooleanExtra(IS_EDITED, false)
        presenter.requestProfile(userId)
        presenter.requestPhoto(userId)

        if (isEdited == true) {
            getPostContent = intent?.getStringExtra(GET_POST).toString()
            input_post.setText(getPostContent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        post = input_post.text.toString().trim()
        when (item.itemId) {
            R.id.post -> {
                if (post != null) {
                    if (isEdited == true) {
                        getPostId = intent?.getStringExtra(POST_ID).toString()
                        presenter.updateData(getPostId, photoUrl, post, nip, username, userId, teacherId, classId, className)
                    } else {
                        presenter.postData(photoUrl, post, nip, username, userId, teacherId, classId, className)
                    }
                } else {
                    Toast.makeText(this, "Isi postingan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return true
    }

    override fun onSuccessPost(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSuccessUpdate(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun handleResponse(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProfileUser(username: String, nip: String) {
        this.username = username
        this.nip = nip
        tv_teacher_name.text = username
        tv_teacher_nip.text = nip
    }

    override fun showPhotoUser(photoUrl: String) {
        this.photoUrl = photoUrl
        GlideApp.with(this)
            .load(photoUrl)
            .placeholder(R.drawable.profile_placeholder)
            .into(img_profile)
    }

    override fun hideProgressBar() {
        swipe_refresh.isRefreshing = false
    }

    override fun showProgressBar() {
        swipe_refresh.isRefreshing = true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}