package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailUser (
    @get:Exclude
    var userDetailId: String? = null,
    var userId: String? = null,
    var tanggalLahir: String? = null,
    var tahunAjaran: String? = null,
    var namaOrtu: String? = null,
    var fotoSiswa: String? = null,
    var fotoBayarAwal: String? = null,
    var kelas: String? = null
): Parcelable