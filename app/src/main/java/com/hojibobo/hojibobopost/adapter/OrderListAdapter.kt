package com.hojibobo.hojibobopost.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hojibobo.hojibobopost.base.BaseViewHolder
import com.hojibobo.hojibobopost.databinding.OrderListItemBinding
import com.hojibobo.hojibobopost.model.Order
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderListAdapter(
    var orderArrayList: ArrayList<Order>,
    var sentClickListener: SentClickListener
) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var orderListItemBinding =
            OrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(orderListItemBinding)
    }

    override fun getItemCount(): Int {
        return orderArrayList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }


    @SuppressLint("SimpleDateFormat")
    fun createOrderDate(dateString: String): String {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        try {
            val date = simpleDateFormat.parse(dateString)
            simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.KOREA)

            return simpleDateFormat.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return dateString
    }


    inner class OrderViewHolder(var orderListItemBinding: OrderListItemBinding) :
        BaseViewHolder(orderListItemBinding.root) {
        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {

            var order = orderArrayList.get(position)
            orderListItemBinding.orderNumber!!.text = order.order_number
            orderListItemBinding.orderDate!!.text = createOrderDate(order.date_created!!)

            orderListItemBinding.orderAddress.text = order.address.toString()
            orderListItemBinding.receiverName.text = order.name
            orderListItemBinding.receiverPhone.text = order.phone

            orderListItemBinding.orderSent.setOnClickListener {
                sentClickListener.sentClick(position)
            }


        }

    }

    interface SentClickListener {
        fun sentClick(position: Int)
    }
}