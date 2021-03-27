package app.siakad.siakadtkadmin.presentation.screens.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.notification.NotificationAddViewModel
import app.siakad.siakadtkadmin.presentation.views.date.DateListener
import app.siakad.siakadtkadmin.presentation.views.date.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationAddActivity : AppCompatActivity(), DateListener {

    private val pageTitle = "Tambah Notifikasi"

    private lateinit var toolbar: Toolbar
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var ivDate: ImageView
    private lateinit var etDate: EditText
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    private lateinit var atvSiswa: AutoCompleteTextView
    private lateinit var atvKelas: AutoCompleteTextView

    private lateinit var btnSiswa: Button
    private lateinit var btnKelas: Button
    private lateinit var layoutSiswa: LinearLayout
    private lateinit var layoutKelas: LinearLayout

    private lateinit var siswaListAdapter: ArrayAdapter<Siswa>
    private lateinit var kelasListAdapter: ArrayAdapter<Siswa>

    private lateinit var datePicker: DatePickerFragment
    private lateinit var calendar: Calendar

    private lateinit var vmNotificationAdd: NotificationAddViewModel
    private lateinit var userObserver: Observer<ArrayList<Siswa>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_add)

        setupItemView()
        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navigateBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDataSet(year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        setupDate()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        etTitle = findViewById(R.id.et_notification_add_judul)
        etContent = findViewById(R.id.et_notification_add_isi)
        ivDate = findViewById(R.id.iv_notification_add_tanggal)
        etDate = findViewById(R.id.et_notification_add_tanggal)
        btnCancel = findViewById(R.id.btn_notification_add_batal)
        btnSave = findViewById(R.id.btn_notification_add_simpan)

        atvSiswa = findViewById(R.id.et_notification_add_nama_siswa)
        atvKelas = findViewById(R.id.et_notification_add_nama_kelas)
        btnSiswa = findViewById(R.id.btn_notification_add_siswa)
        btnKelas = findViewById(R.id.btn_notification_add_kelas)
        layoutSiswa = findViewById(R.id.ll_notification_add_siswa)
        layoutKelas = findViewById(R.id.ll_notification_add_kelas)

        datePicker = DatePickerFragment()
        calendar = Calendar.getInstance()

        siswaListAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayListOf())
        kelasListAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayListOf())

        vmNotificationAdd = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(NotificationAddViewModel::class.java)
    }

    private fun setupView() {
        setupAppBar()
        setupDate()
        setupAutoCompleteView()
        setupObserver()

        ivDate.setOnClickListener {
            var arg = Bundle()

            arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
            arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
            arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
            datePicker.arguments = arg

            datePicker.show(supportFragmentManager, null)
        }
        etDate.setOnClickListener {
            var arg = Bundle()

            arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
            arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
            arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
            datePicker.arguments = arg

            datePicker.show(supportFragmentManager, null)
        }

        btnCancel.setOnClickListener {
            navigateBack()
        }

        btnSave.setOnClickListener {
            if (validateInput()) {
                vmNotificationAdd.setData(
                    etTitle.text.toString(),
                    etContent.text.toString(),
                    etDate.text.toString()
                )
            }
        }

        btnSiswa.setOnClickListener {
            layoutSiswa.visibility = View.VISIBLE
            layoutKelas.visibility = View.GONE
        }

        btnKelas.setOnClickListener {
            layoutKelas.visibility = View.VISIBLE
            layoutSiswa.visibility = View.GONE
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupDate() {
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
        etDate.text = SpannableStringBuilder(date)
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@NotificationAddActivity,
                NotificationActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    private fun validateInput(): Boolean {
        var returnState = true

        if (etTitle.text.isEmpty()) {
            etTitle.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etContent.text.isEmpty()) {
            etContent.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etDate.text.isEmpty()) {
            etDate.error = getString(R.string.empty_input)
            returnState = false
        }

        if (layoutSiswa.visibility == View.VISIBLE) {
            if (atvSiswa.text.isEmpty()) {
                atvSiswa.error = getString(R.string.empty_input)
                returnState = false
            }
        }

        if (layoutKelas.visibility == View.VISIBLE) {
            if (atvKelas.text.isEmpty()) {
                atvKelas.error = getString(R.string.empty_input)
                returnState = false
            }
        }

        return returnState
    }

    private fun setupAutoCompleteView() {
        atvSiswa.setAdapter(siswaListAdapter)
        atvKelas.setAdapter(kelasListAdapter)

        atvSiswa.setOnItemClickListener { adapter, _, position, _ ->
            val siswa: Siswa = adapter.getItemAtPosition(position) as Siswa
            showToast(siswa.nama)
        }

        atvSiswa.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(str: Editable?) {}

            override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        atvSiswa.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(str: Editable?) {}

            override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupObserver() {
        userObserver = Observer {userList ->
            siswaListAdapter.addAll(userList)
            atvSiswa.setAdapter(siswaListAdapter)
        }

        vmNotificationAdd.getUserList().observe(this, userObserver)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}