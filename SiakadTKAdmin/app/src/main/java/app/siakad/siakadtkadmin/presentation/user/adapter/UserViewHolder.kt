package app.siakad.siakadtkadmin.presentation.user.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.SiswaModel
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun insertOrder(item: SiswaModel) {
        with(itemView) {
            iv_item_user
//            item.fotoSiswa
//            tv_item_user_judul.text = item.namaSiswa
//            tv_item_user_email.text = item.emailSiswa
//            tv_item_user_tanggal.text = item.tanggalRegisAkun
        }
    }
}