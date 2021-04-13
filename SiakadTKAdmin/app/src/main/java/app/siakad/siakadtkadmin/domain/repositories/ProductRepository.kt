package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListener
import app.siakad.siakadtkadmin.infrastructure.data.product.Buku
import app.siakad.siakadtkadmin.infrastructure.data.product.Seragam
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProductRepository() {
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val uniformDB = FirebaseRef(
        FirebaseRef.SERAGAM_REF
    ).getRef()

    private val bookDB = FirebaseRef(
        FirebaseRef.BUKU_REF
    ).getRef()

    fun initGetUniformEventListener(listener: ProductListListener) {
        uniformDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<SeragamModel>()

                for (dataSS in snapshot.children) {
                    val data: SeragamModel? = dataSS.getValue(SeragamModel::class.java)
                    data?.produkId = dataSS.key.toString()
                    dataRef.add(data!!)

                    listener.setUniformList(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = dataRef
                        )
                    )
                }
            }
        })
    }

    fun initGetBookEventListener(listener: ProductListListener) {
        bookDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<BukuModel>()

                for (dataSS in snapshot.children) {
                    val data: BukuModel? = dataSS.getValue(BukuModel::class.java)
                    dataRef.add(data!!)

                    listener.setBookList(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = dataRef
                        )
                    )
                }
            }
        })
    }

    fun insertDataSeragam(listener: ProductListener, data: Seragam) {
        val newKey = uniformDB.push().key.toString()
        val newData =
            SeragamModel(
                produkId = newKey,
                adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
                fotoProduk = data.fotoProduk,
                jenisKelamin = data.jenisKelamin,
                namaProduk = data.namaProduk,
                detailSeragam = data.detailSeragam,
                jumlah = data.jumlah
            )

        uniformDB.child(newKey).setValue(newData).addOnSuccessListener {
            listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyInsertDataStatus(ModelContainer.getFailModel())
        }
    }

    fun insertDataBuku(listener: ProductListener, data: Buku) {
        val newKey = bookDB.push().key.toString()
        val newData = BukuModel(
            produkId = newKey,
            adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            fotoProduk = data.fotoProduk,
            namaProduk = data.namaProduk,
            harga = data.harga,
            jumlah = data.jumlah
        )

        bookDB.child(newKey).setValue(newData).addOnSuccessListener {
            listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyInsertDataStatus(ModelContainer.getFailModel())
        }
    }
}