package com.example.binance.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.binance.databinding.ListItemCurrencyBinding
import com.example.binance.domainmodel.Currency

class CurrencyRecyclerViewAdapter(private val clickListener: CurrencyListener) :
    ListAdapter<Currency, CurrencyRecyclerViewAdapter.ViewHolder>(CurrencyDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Currency, clickListener: CurrencyListener) {
            binding.apply {
                currency = item
                this.clickListener = clickListener
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCurrencyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class CurrencyDiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }

    class CurrencyListener(val clickListener: (currency: Currency) -> Unit) {
        fun onClick(currency: Currency) = clickListener(currency)
    }
}
