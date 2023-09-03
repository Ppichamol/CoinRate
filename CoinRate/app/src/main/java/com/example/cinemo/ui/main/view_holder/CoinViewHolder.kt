package com.example.cinemo.ui.main.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemo.databinding.ItemCoinBinding
import com.example.cinemo.R
import com.example.cinemo.ui.main.adapter.CoinItem
import com.example.cinemo.ui.main.model.CoinItemModel

class CoinViewHolder (private val binding: ItemCoinBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: CoinItem.CoinItemDetail,
        onItemCoinClicked: (coinSelected: CoinItemModel) -> Unit,
        isHistory: Boolean
    ) {
        binding.currencyType.text = String.format("BTC/%s (%s)", item.coinItem.code, item.coinItem.description)
        binding.price.text = String.format("%s %s", item.coinItem.rate, item.coinItem.symbol)
        binding.time.text = item.coinItem.time
        if (isHistory) {
            binding.moreDetail.visibility = View.INVISIBLE
        } else {
            binding.moreDetail.visibility = View.VISIBLE
        }
        binding.moreDetail.setOnClickListener {
                onItemCoinClicked.invoke(item.coinItem)
        }
        setImage(item.coinItem.code)

    }

    private fun setImage(currency: String) {
        when (currency) {
            "USD" -> { binding.flag.setImageResource(R.drawable.united_states_flag_icon_round) }
            "GBP" -> { binding.flag.setImageResource(R.drawable.united_kingdom_flag_icon_round) }
            "EUR" -> { binding.flag.setImageResource(R.drawable.european_union_svgrepo_com) }
        }
    }
}