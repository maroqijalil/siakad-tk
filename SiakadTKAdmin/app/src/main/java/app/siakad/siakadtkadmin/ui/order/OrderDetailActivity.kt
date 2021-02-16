package app.siakad.siakadtkadmin.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvClass: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvHP: TextView
    private lateinit var tvOrderNum: TextView
    private lateinit var tvOrderTotal: TextView
    private lateinit var ivAccAll: ImageView
    private lateinit var ivRejectAll: ImageView
    private lateinit var rvOrderList: RecyclerView
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        tvName = findViewById(R.id.tv_registration_detail_nama)
        tvClass = findViewById(R.id.tv_order_detail_kelas)
        tvAddress = findViewById(R.id.tv_order_detail_alamat)
        tvHP = findViewById(R.id.tv_order_detail_nohp)
        tvOrderNum = findViewById(R.id.tv_order_detail_jumlah)
        tvOrderTotal = findViewById(R.id.tv_order_detail_total)
        ivAccAll = findViewById(R.id.iv_order_detail_acc_all)
        ivRejectAll = findViewById(R.id.iv_order_detail_reject_all)
        rvOrderList = findViewById(R.id.rv_order_detail_daftar_pesanan)
        btnCancel = findViewById(R.id.btn_order_detail_batal)
        btnSave = findViewById(R.id.btn_order_detail_simpan)
    }

    private fun setupView() {

    }
}