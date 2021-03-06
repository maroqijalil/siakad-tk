package app.siakad.siakadtk.presentation.screens.announcement.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import kotlinx.android.synthetic.main.item_row_pengumuman.view.*

class AnnouncementViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertAnnouncement(item: PengumumanModel) {
        with(itemView) {
            tv_item_pengumuman_title.text = item.judul
            tv_item_pengumuman_desc.text = item.keterangan
        }
    }
}