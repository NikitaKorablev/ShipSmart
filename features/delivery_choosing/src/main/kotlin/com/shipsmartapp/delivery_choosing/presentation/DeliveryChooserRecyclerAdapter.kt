package com.shipsmartapp.delivery_choosing.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.delivery_network.data.DeliveryData
import com.delivery_choosing.databinding.DeliveryCompanyListItemBinding
import com.delivery_choosing.R

class DeliveryChooserRecyclerAdapter
: RecyclerView.Adapter<DeliveryChooserRecyclerAdapter.DeliveryChooserHolder> () {

    class DeliveryChooserHolder(val binding: DeliveryCompanyListItemBinding):
        RecyclerView.ViewHolder(binding.root)

    var companyList: List<DeliveryData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryChooserHolder {
        val binding = DeliveryCompanyListItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return DeliveryChooserHolder(binding)
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DeliveryChooserHolder, position: Int) {
        val company = companyList[position]

        holder.binding.name.text = company.name
        holder.binding.cost.text = "${company.cost} руб."
        holder.binding.deliveryTime.text = "${company.deliveryTime} рабочих дня"
        holder.binding.image.setBackgroundResource(company.img)
    }
}