package app.siakad.siakadtkadmin.presentation.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.announcement.AnnouncementActivity
import app.siakad.siakadtkadmin.presentation.view.date.DatePickerHelper

class NotificationAddActivity : AppCompatActivity() {

    private val pageTitle = "Tambah Notifikasi"

    private lateinit var toolbar: Toolbar
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var ivDate: ImageView
    private lateinit var etDate: EditText
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    private lateinit var datePicker: DatePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_add)

        setupItemView()
        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@NotificationAddActivity,
                        NotificationActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        etTitle = findViewById(R.id.et_notification_add_judul)
        etContent = findViewById(R.id.et_notification_add_isi)
        ivDate = findViewById(R.id.iv_notification_add_tanggal)
        etDate = findViewById(R.id.et_notification_add_tanggal)
        btnCancel = findViewById(R.id.btn_notification_add_batal)
        btnSave = findViewById(R.id.btn_notification_add_simpan)

        datePicker = DatePickerHelper(supportFragmentManager)
    }

    private fun setupView() {
        setupAppBar()

        ivDate.setOnClickListener {
            datePicker.showDialog()
        }
        etDate.setOnClickListener {
            datePicker.showDialog()
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}