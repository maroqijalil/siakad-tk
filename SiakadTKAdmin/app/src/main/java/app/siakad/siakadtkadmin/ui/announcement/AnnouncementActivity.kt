package app.siakad.siakadtkadmin.ui.announcement

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.notification.NotificationActivity

class AnnouncementActivity : AppCompatActivity() {

    private val pageTitle = "Pengumuman"

    private lateinit var toolbar: Toolbar
    private lateinit var tvNumNotif: TextView
    private lateinit var btnSeeAllNotif: TextView
    private lateinit var tvNumAnnounce: TextView
    private lateinit var ivAddAnnounce: ImageView
    private lateinit var rvAnnounce: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvNumNotif = findViewById(R.id.tv_announcement_notifikasi)
        btnSeeAllNotif = findViewById(R.id.btn_announcement_lihat_semua_notifikasi)
        tvNumAnnounce = findViewById(R.id.tv_announcement_jumlah_pengumuman)
        ivAddAnnounce = findViewById(R.id.iv_announcement_tambah_announ)
        rvAnnounce = findViewById(R.id.rv_announcement_daftar_pengumuman)
    }

    private fun setupView() {
        setupAppBar()

        ivAddAnnounce.setOnClickListener{
            val intent = Intent(this@AnnouncementActivity, AnnouncementAddActivity::class.java)
            startActivity(intent)
        }

        btnSeeAllNotif.setOnClickListener{
            val intent = Intent(this@AnnouncementActivity, NotificationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObserver() {
    }

    private fun initRVADataList() {
    }
}