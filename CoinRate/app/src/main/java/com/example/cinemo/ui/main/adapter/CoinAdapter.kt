package com.example.cinemo.ui.main.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemo.databinding.ItemCoinBinding
import com.example.cinemo.ui.main.model.CoinItemModel
import com.example.cinemo.ui.main.view_holder.CoinViewHolder
import kotlinx.parcelize.Parcelize


object CoinItemType {
    const val TYPE_COIN_ITEM = 0
}

sealed class CoinItem(val type: Int) : Parcelable {

    @Parcelize
    @Keep
    data class CoinItemDetail(
        val coinItem: CoinItemModel
    ): CoinItem(CoinItemType.TYPE_COIN_ITEM)
}

class CoinAdapter(
    private val items: List<CoinItem>,
    private val coinCardClicked: (coinSelected: CoinItemModel) -> Unit,
    private val isHistory: Boolean = false
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        CoinItemType.TYPE_COIN_ITEM -> CoinViewHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else -> throw NullPointerException("View Type $viewType doesn't match with any existing type")
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items.getOrNull(position)?.let { item ->
            when {
                holder is CoinViewHolder && item is CoinItem.CoinItemDetail -> holder.bind(item, coinCardClicked, isHistory)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = items[position].type

}