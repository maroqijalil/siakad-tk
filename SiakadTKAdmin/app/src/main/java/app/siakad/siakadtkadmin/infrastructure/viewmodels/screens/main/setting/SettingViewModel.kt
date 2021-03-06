package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.main.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

class SettingViewModel : ViewModel() {
  private val authRepository = AuthenticationRepository()

  fun logout() {
    authRepository.logout()
  }
}