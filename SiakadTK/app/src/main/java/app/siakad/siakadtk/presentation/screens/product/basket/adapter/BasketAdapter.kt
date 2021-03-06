package app.siakad.siakadtk.presentation.screens.product.basket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.infrastructure.data.DetailKeranjang

class BasketAdapter(): RecyclerView.Adapter<BasketViewHolder>() {
    private  val basketList: ArrayList<DetailKeranjangModel> = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): BasketViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_row_basket,
            viewGroup,
            false
        )
        return BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.insertKeranjang(basketList[position], position)
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    fun changeDataList(data: ArrayList<DetailKeranjangModel>) {
        if (basketList.size > 0)
            basketList.clear()

        basketList.addAll(data)
        this.notifyDataSetChanged()
    }
}