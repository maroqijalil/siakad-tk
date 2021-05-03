package app.siakad.siakadtk.presentation.screens.registration.form

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.infrastructure.viewmodels.screens.register.RegisterViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.registration.RegistrationFormViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.register.RegisterActivity
import app.siakad.siakadtk.presentation.screens.registration.RegistrationActivity
import app.siakad.siakadtk.presentation.views.date.DateListener
import app.siakad.siakadtk.presentation.views.date.DatePickerFragment
import com.androidbuffer.kotlinfilepicker.KotConstants
import com.androidbuffer.kotlinfilepicker.KotRequest
import java.text.SimpleDateFormat
import java.util.*


class RegistrationFormActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, DateListener {
    private val pageTitle = "Form Pendaftaran"

    private lateinit var toolbar: Toolbar
    private lateinit var etName: EditText
    private lateinit var etBornDate: EditText
    private lateinit var spGender: Spinner
    private lateinit var spClass: Spinner
    private lateinit var etParentName: EditText
    private lateinit var etAddress: EditText
    private lateinit var spTahunAjaran: Spinner
    private lateinit var etPhoneNumber: EditText
//    private lateinit var etTotalPayment: EditText
    private lateinit var btnUploadBukti: Button
    private lateinit var btnCancel: TextView
    private lateinit var btnSimpan: TextView

    private lateinit var datePicker: DatePickerFragment
    private lateinit var calendar: Calendar

    private lateinit var vmRegistrationForm: RegistrationFormViewModel

    private var paymentImage: Uri? = null

    companion object {
        const val PICK_PHOTO_REQUEST = 1000
        const val PERMISSION_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_form)
        setupItemView()
        setupView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Akses ditolak", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == RegisterActivity.PICK_PHOTO_REQUEST) {
            val imageUri: Uri = data?.data!!
            btnUploadBukti.text = imageUri.toString()
            paymentImage = imageUri
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            PICK_PHOTO_REQUEST
        )
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
        etName = findViewById(R.id.et_registrationform_nama)
        etBornDate = findViewById(R.id.et_registrationform_ttl)
        spGender = findViewById(R.id.sp_registrationform_jenis_kelamin)
        spClass = findViewById(R.id.sp_registrationform_kelas)
        etParentName = findViewById(R.id.et_registrationform_nama_ortu)
        etAddress = findViewById(R.id.et_registrationform_alamat)
        spTahunAjaran = findViewById(R.id.sp_registrationform_tahun_ajaran)
        etPhoneNumber = findViewById(R.id.et_registrationform_no_hp_ortu)
//        etTotalPayment = findViewById(R.id.et_registrationform_nominal)
        btnUploadBukti = findViewById(R.id.btn_registrationform_upload_bukti_bayar)
        btnCancel = findViewById(R.id.btn_registrationform_batal)
        btnSimpan = findViewById(R.id.btn_registrationform_simpan)

        datePicker = DatePickerFragment()
        calendar = Calendar.getInstance()

        setupAdapterListener()
        spGender.onItemSelectedListener = this
        spClass.onItemSelectedListener = this
        spTahunAjaran.onItemSelectedListener = this

        vmRegistrationForm =
            ViewModelProvider(this, ViewModelFactory(this, this)).get(RegistrationFormViewModel::class.java)

        val obsRegistrationGetUser = Observer<Pengguna> {
            etName.setText(it.nama)
            etBornDate.setText(it.detail!!.tanggalLahir)
            etParentName.setText(it.detail!!.namaOrtu)
            etAddress.setText(it.alamat)
            etPhoneNumber.setText(it.noHP)
        }

        vmRegistrationForm.getUserData()
            .observe(this, obsRegistrationGetUser)
    }

    private fun setupAdapterListener() {
        ArrayAdapter.createFromResource(
            this, R.array.list_jenis_kelamin,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spGender.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this, R.array.list_kelas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spClass.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this, R.array.list_ajaran,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spTahunAjaran.adapter = adapter
        }
    }

    private fun setupView() {
        setupAppBar()
        etBornDate.setOnClickListener {
            var arg = Bundle()

            arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
            arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
            arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
            datePicker.arguments = arg

            datePicker.show(supportFragmentManager, null)
        }
        btnUploadBukti.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(
                        permissions,
                        PERMISSION_REQUEST
                    );
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }
        btnCancel.setOnClickListener{
            navigateBack()
        }
        btnSimpan.setOnClickListener {
            if (validateInput()) {
                vmRegistrationForm.setData(
                    etName.text.toString(),
                    spClass.selectedItem.toString(),
                    etParentName.text.toString(),
                    spGender.selectedItem.toString(),
                    etBornDate.text.toString(),
                    etAddress.text.toString(),
                    etPhoneNumber.text.toString(),
                    spTahunAjaran.selectedItem.toString(),
                    0,
                    paymentImage,
                )
                navigateBack()
            }
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupDate() {
        var date =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
        etBornDate.text = SpannableStringBuilder(date)
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@RegistrationFormActivity,
                RegistrationActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    private fun validateInput(): Boolean {
        var returnState = true

//        showToast(spGender.selectedItem.toString() + " " + spClass.selectedItem.toString() + " " + spTahunAjaran.selectedItem.toString())

        if (etName.text.isEmpty()) {
            etName.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etBornDate.text.isEmpty()) {
            etBornDate.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etParentName.text.isEmpty()) {
            etParentName.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etAddress.text.isEmpty()) {
            etAddress.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etPhoneNumber.text.isEmpty()) {
            etPhoneNumber.error = getString(R.string.empty_input)
            returnState = false
        }

        return returnState
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}