package com.example.pharmatic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailHistoryAdapter : RecyclerView.Adapter<DetailHistoryAdapter.ViewHolder>() {

    private var listDetail = emptyList<TransaksiDetail>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaObat: TextView = view.findViewById(R.id.tvDetailNamaObat)
        val tvQtyHarga: TextView = view.findViewById(R.id.tvDetailQtyHarga)
        val tvSubtotal: TextView = view.findViewById(R.id.tvDetailSubtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detail = listDetail[position]
        holder.tvNamaObat.text = detail.namaObat
        
        val hargaSatuan = "Rp %,d".format(detail.hargaSatuan).replace(',', '.')
        holder.tvQtyHarga.text = "${detail.qty} x $hargaSatuan"
        
        holder.tvSubtotal.text = "Rp %,d".format(detail.subtotal).replace(',', '.')
    }

    override fun getItemCount(): Int = listDetail.size

    fun updateData(newList: List<TransaksiDetail>) {
        listDetail = newList
        notifyDataSetChanged()
    }
}
