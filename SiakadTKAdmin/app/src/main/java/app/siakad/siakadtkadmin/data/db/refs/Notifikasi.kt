package app.siakad.siakadtkadmin.data.db.refs

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notifikasi (
    @get:Exclude
    var notifikasiId: String? = null,
    var adminId: String? = null,
    var userId: String? = null,
    var judul: String? = null,
    var keterangan: String? = null,
    var tanggal: String? = null
): Parcelable