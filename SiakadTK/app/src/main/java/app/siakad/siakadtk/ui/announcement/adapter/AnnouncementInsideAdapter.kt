package app.siakad.siakadtk.ui.announcement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.data.model.Announcement
import app.siakad.siakadtk.ui.announcement.adapter.AnnouncementViewHolder

class AnnouncementInsideAdapter(val announcementList: ArrayList<Announcement>) : RecyclerView.Adapter<AnnouncementInsideViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AnnouncementInsideViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_pengumuman_inside, viewGroup, false)
        return AnnouncementInsideViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnnouncementInsideViewHolder, position: Int) {
        val announcement = announcementList[position]
        holder.insertAnnouncement(announcement)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }
}