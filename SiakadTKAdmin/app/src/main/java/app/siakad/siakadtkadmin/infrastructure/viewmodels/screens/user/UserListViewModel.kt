package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserListViewModel(private val context: Context) :
  ViewModel(),
  UserListListener {
  private val userListLiveData = MutableLiveData<ArrayList<PenggunaModel>>()
  private val userRepository = UserRepository()

  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  private val dataPenggunaList = arrayListOf<PenggunaModel>()

  fun setUserType(verified: Boolean) {
    if (dataPenggunaList.isEmpty()) {
      vmCoroutineScope.launch {
        userRepository.initGetUserListListener(this@UserListViewModel, verified)
      }
    }
  }

  override fun addUserItem(pengguna: ModelContainer<PenggunaModel>) {
    if (pengguna.status == ModelState.SUCCESS) {
      if (pengguna.data != null) {
        dataPenggunaList.add(pengguna.data!!)
        userListLiveData.postValue(dataPenggunaList)
      }
    } else if (pengguna.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun updateUserItem(pengguna: ModelContainer<PenggunaModel>) {
    if (pengguna.status == ModelState.SUCCESS) {
      if (pengguna.data != null) {
        dataPenggunaList.forEachIndexed { index, item ->
          if (item.userId == pengguna.data?.userId) {
            dataPenggunaList[index] = pengguna.data!!
          }
        }
        userListLiveData.postValue(dataPenggunaList)
      }
    } else if (pengguna.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun removeUserItem(pengguna: ModelContainer<PenggunaModel>) {
    if (pengguna.status == ModelState.SUCCESS) {
      if (pengguna.data != null) {
        var target = 0
        dataPenggunaList.forEachIndexed forE@{ index, item ->
          if (item.userId == pengguna.data?.userId) {
            target = index
            return@forE
          }
        }
        dataPenggunaList.removeAt(target)
        userListLiveData.postValue(dataPenggunaList)
      }
    } else if (pengguna.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  fun getUserList(): LiveData<ArrayList<PenggunaModel>> {
    return userListLiveData
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }

  override fun onCleared() {
    super.onCleared()
    userRepository.removeEventListener()
  }
}