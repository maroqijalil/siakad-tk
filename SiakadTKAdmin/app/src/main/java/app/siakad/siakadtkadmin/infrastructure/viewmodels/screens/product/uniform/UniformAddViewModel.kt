package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product.uniform

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.db.storage.FirebaseStrg
import app.siakad.siakadtkadmin.domain.models.product.DetailSeragamModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.domain.repositories.ProductRepository
import app.siakad.siakadtkadmin.domain.storage.WholeStorage
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListener
import app.siakad.siakadtkadmin.domain.utils.listeners.storage.StorageListener
import app.siakad.siakadtkadmin.infrastructure.data.product.Seragam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UniformAddViewModel(private val context: Context) :
    ViewModel(), ProductListener, StorageListener {
    private val productRepository = ProductRepository()
    private val fbStorage = WholeStorage(FirebaseStrg.SERAGAM_REF)
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var seragam = SeragamModel()
    private var updateImageUri: Uri? = null
    private var isUpdateData: Boolean = false

    override fun notifyInsertDataStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_set_data))
        }
    }

    override fun notifyUploadStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            seragam.fotoProduk = status.data!!
            if (isUpdateData) {
                vmCoroutineScope.launch {
                    productRepository.updateDataSeragam(this@UniformAddViewModel, seragam)
                }
            } else {
                vmCoroutineScope.launch {
                    productRepository.insertDataSeragam(this@UniformAddViewModel, seragam)
                }
            }
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_upoad_img))
        }
    }

    override fun notifyDeleteStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            fbStorage.uploadImage(
                this@UniformAddViewModel, updateImageUri!!,
                System.currentTimeMillis().toString() + "." + getFileExtension(updateImageUri!!)
            )
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_upoad_img))
        }
    }

    fun insertUniform(
        gender: String,
        name: String,
        imageUri: Uri?,
        total: Int,
        sizeList: ArrayList<DetailSeragamModel>
    ) {
        seragam = SeragamModel(
            namaProduk = name,
            jenisKelamin = gender,
            jumlah = total,
            detailSeragam = sizeList
        )

        if (imageUri != null) {
            vmCoroutineScope.launch {
                fbStorage.uploadImage(
                    this@UniformAddViewModel, imageUri,
                    System.currentTimeMillis().toString() + "." + getFileExtension(imageUri)
                )
            }
        } else {
            vmCoroutineScope.launch {
                productRepository.insertDataSeragam(this@UniformAddViewModel, seragam)
            }
        }
    }

    fun updateUniform(
        gender: String,
        name: String,
        imageUri: Uri?,
        total: Int,
        sizeList: ArrayList<DetailSeragamModel>,
        data: SeragamModel
    ) {
        data.namaProduk = name
        data.jenisKelamin = gender
        data.jumlah = total
        data.detailSeragam = sizeList
        seragam = data

        isUpdateData = true

        if (imageUri != null) {
            updateImageUri = imageUri
            vmCoroutineScope.launch {
                fbStorage.deleteImage(
                    this@UniformAddViewModel, data.fotoProduk
                )
            }
        } else {
            vmCoroutineScope.launch {
                productRepository.updateDataSeragam(this@UniformAddViewModel, seragam)
            }
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri))
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}