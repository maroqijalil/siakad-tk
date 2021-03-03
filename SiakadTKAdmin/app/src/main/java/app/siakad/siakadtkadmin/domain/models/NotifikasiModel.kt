package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class NotifikasiModel (
    var notifikasiId: String? = null,
    var userId: String? = null,
    var judul: String? = null,
    var keterangan: String? = null,
    var tanggal: String? = null
): Parcelable