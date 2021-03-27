package app.siakad.siakadtkadmin.presentation.screens.user.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.presentation.screens.user.listener.UserClickListener
import kotlinx.android.synthetic.main.item_user.view.*

class UserListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
    fun insertUser(item: Siswa) {
        with(itemView) {
            tv_item_user_judul.text = item.nama
            tv_item_user_email.text = item.email
            tv_item_user_kelas.text

            ll_item_user.setOnClickListener {
                (v.context as UserClickListener).navigateToUserDetail(item)
            }
        }
    }
}