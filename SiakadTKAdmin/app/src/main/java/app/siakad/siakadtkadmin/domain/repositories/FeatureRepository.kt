package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.models.FiturModel

class FeatureRepository() {
    private var features = MutableLiveData<ModelContainer<ArrayList<FiturModel>>>()
    private var updateState = MutableLiveData<ModelContainer<String>>()

    private val featureDB = FirebaseRef(
        FirebaseRef.FITUR_REF
    ).getRef()

    fun updateData() {
    }

    fun getUpdateState(): LiveData<ModelContainer<String>> {
        return updateState
    }
}