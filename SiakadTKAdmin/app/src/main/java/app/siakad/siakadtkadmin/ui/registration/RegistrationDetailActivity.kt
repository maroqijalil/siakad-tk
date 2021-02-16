package app.siakad.siakadtkadmin.ui.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R

class RegistrationDetailActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvClass: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvParent: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvHP: TextView
    private lateinit var tvPrice: TextView
    private lateinit var btnProofPrice: CardView
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_detail)
    }

    private fun setupItemView() {
        tvName = findViewById(R.id.tv_registration_detail_nama)
        tvClass = findViewById(R.id.tv_registration_detail_kelas)
        tvGender = findViewById(R.id.tv_registration_detail_jenkel)
        tvParent = findViewById(R.id.tv_registration_detail_wali)
        tvAddress = findViewById(R.id.tv_registration_detail_alamat)
        tvHP = findViewById(R.id.tv_registration_detail_nohp)
        tvPrice = findViewById(R.id.tv_registration_detail_nobayar)
        btnProofPrice = findViewById(R.id.btn_registration_detail_buktibayar)
        btnCancel = findViewById(R.id.btn_registration_detail_batal)
        btnSave = findViewById(R.id.btn_registration_detail_simpan)
    }

    private fun setupView() {}
}