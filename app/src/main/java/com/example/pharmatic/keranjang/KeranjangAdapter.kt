package com.example.pharmatic.keranjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.R

class KeranjangAdapter(
    private val listKeranjang: List<KeranjangItem>
) : RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvNama: TextView =
            itemView.findViewById(R.id.tvNamaObat)

        val tvQty: TextView =
            itemView.findViewById(R.id.tvQty)

        val tvHarga: TextView =
            itemView.findViewById(R.id.tvHarga)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keranjang, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listKeranjang.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = listKeranjang[position]

        holder.tvNama.text = item.nama
        holder.tvQty.text = item.qty.toString()
        holder.tvHarga.text = "Rp ${item.subtotal}"
    }
}
