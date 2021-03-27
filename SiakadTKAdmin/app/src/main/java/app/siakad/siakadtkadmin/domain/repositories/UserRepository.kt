package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.model.UserRoleModel
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class UserRepository() {
    private var userState = MutableLiveData<ModelContainer<PenggunaModel>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val userDB = FirebaseRef(
        FirebaseRef.USER_REF
    ).getRef()

    fun initGetUserListListener(listener: UserListListener, verified: Boolean = true) {
        userDB.orderByChild("role").equalTo(UserRoleModel.SISWA.str)
            .addChildEventListener(object: ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val dataRef = arrayListOf<PenggunaModel>()

                forloop@ for (dataSS in snapshot.children) {
                    when (dataSS.value) {
                        is String -> {
                            val data: PenggunaModel? = snapshot.getValue(PenggunaModel::class.java)
                            if (data != null) {
                                if (data.status == verified) {
                                    data.userId = snapshot.key.toString()
                                    dataRef.add(data)

                                    listener.setUserList(
                                        ModelContainer(
                                            status = ModelState.SUCCESS,
                                            data = dataRef
                                        )
                                    )
                                    break@forloop
                                }
                            }
                        }
                        is PenggunaModel -> {
                            val data: PenggunaModel? = dataSS.getValue(PenggunaModel::class.java)
                            if (data != null) {
                                if (data.status == verified) {
                                    data.userId = dataSS.key.toString()
                                    dataRef.add(data)

                                    listener.setUserList(
                                        ModelContainer(
                                            status = ModelState.SUCCESS,
                                            data = dataRef
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}
        })
    }

    fun getUserByEmail(email: String) {
        userDB.orderByChild("email").equalTo(email).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(PenggunaModel::class.java)

                if (user != null) {
                    user.userId = snapshot.key.toString()
                    userState.postValue(ModelContainer.getSuccesModel(user))
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}
        })
    }

    fun insertData(pengguna: PenggunaModel) {
        val newKey = userDB.push().key.toString()
        val newData = PenggunaModel(
            userId = newKey,
            alamat = pengguna.alamat,
            email = pengguna.email,
            nama = pengguna.nama,
            noHP = pengguna.noHP,
            passwd = pengguna.passwd,
            role = UserRoleModel.ADMIN.str
        )

        userDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getUser(): LiveData<ModelContainer<PenggunaModel>> {
        return userState
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}