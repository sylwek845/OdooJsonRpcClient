package com.odoo.customers

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.odoo.common.utils.recycler.RecyclerBaseAdapter
import com.odoo.customers.databinding.ItemViewCustomerBinding

class CustomerAdapter(
    val fragment: CustomerFragment,
    items: ArrayList<Any>
) : RecyclerBaseAdapter(items, fragment.binding.rv) {

    companion object {
        const val TAG: String = "CustomerAdapter"

        private const val VIEW_TYPE_ITEM = 0
    }

    private val rowItems: ArrayList<com.odoo.customers.entities.CustomerResponse> = ArrayList(
            items.filterIsInstance<com.odoo.customers.entities.CustomerResponse>()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = ItemViewCustomerBinding.inflate(
                        inflater,
                        parent,
                        false
                )
                return CustomerViewHolder(binding)
            }
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(baseHolder: RecyclerView.ViewHolder, basePosition: Int) {
        super.onBindViewHolder(baseHolder, basePosition)
        val position = baseHolder.adapterPosition
        when (getItemViewType(basePosition)) {
            VIEW_TYPE_ITEM -> {
                val holder = baseHolder as com.odoo.customers.CustomerViewHolder
                val item = items[position] as com.odoo.customers.entities.CustomerResponse
                val binding = holder.binding
               // binding.customer = item
                if (!binding.root.hasOnClickListeners()) {
                    binding.root.setOnClickListener {
                        // val clickedPosition = holder.adapterPosition
                        // val clickedItem = items[clickedPosition] as CustomerResponse
                    }
                }
            }
        }
    }

    val rowItemCount: Int get() = rowItems.size

    override fun getItemViewType(position: Int): Int {
        val o = items[position]
        if (o is com.odoo.customers.entities.CustomerResponse) {
            return VIEW_TYPE_ITEM
        }
        return super.getItemViewType(position)
    }

    private fun updateRowItems() {
        updateSearchItems()
        rowItems.clear()
        rowItems.addAll(ArrayList(
                items.filterIsInstance<com.odoo.customers.entities.CustomerResponse>()))
    }

    fun addRowItems(rowItems: ArrayList<com.odoo.customers.entities.CustomerResponse>) {
        this.rowItems.addAll(rowItems)
        addAll(rowItems.toMutableList<Any>() as ArrayList<Any>)
    }

    override fun clear() {
        rowItems.clear()
        super.clear()
    }
}
