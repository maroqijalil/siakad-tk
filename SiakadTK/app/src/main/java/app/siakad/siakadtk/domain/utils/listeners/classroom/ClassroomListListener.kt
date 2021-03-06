package app.siakad.siakadtk.domain.utils.listeners.classroom

import app.siakad.siakadtk.domain.models.KelasModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface ClassroomListListener {
    fun setClassroomList(kelasList: ModelContainer<ArrayList<KelasModel>>)
}