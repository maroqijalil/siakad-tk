package app.siakad.siakadtk.domain.utils.listeners.registration

import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface RegistrationListener {
    fun addDataDafulUser(user: ModelContainer<DaftarUlangModel>)
    fun addDataUser(user: ModelContainer<PenggunaModel>)
    fun notifyUserDetailChangeStatus(status: ModelContainer<String>)
}