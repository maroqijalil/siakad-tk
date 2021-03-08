package app.siakad.siakadtkadmin.domain

data class ModelContainer<T>(
    var status: ModelState,
    var data: T? = null
) {
    companion object {
        fun<K> getSuccesModel(item: K): ModelContainer<K> {
            return ModelContainer(
                status = ModelState.SUCCESS,
                data = item
            )
        }

        fun<K> getFailModel(): ModelContainer<K> {
            return ModelContainer(
                status = ModelState.ERROR,
            )
        }
    }
}