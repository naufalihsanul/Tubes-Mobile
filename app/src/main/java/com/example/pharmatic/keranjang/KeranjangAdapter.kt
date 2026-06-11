package com.example.pharmatic.keranjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.R

class KeranjangAdapter(
    private var listKeranjang: List<KeranjangItem>,
    private val onUpdate: () -> Unit
) : RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaObat)
        val tvQty: TextView = itemView.findViewById(R.id.tvQty)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
        val btnTambah: android.widget.ImageView = itemView.findViewById(R.id.btnTambah)
        val btnKurang: android.widget.ImageView = itemView.findViewById(R.id.btnKurang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keranjang, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listKeranjang.size

    fun updateData(newList: List<KeranjangItem>) {
        listKeranjang = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listKeranjang[position]

        holder.tvNama.text = item.nama
        holder.tvQty.text = item.qty.toString()
        holder.tvHarga.text = "Rp ${item.subtotal}"

        holder.btnTambah.setOnClickListener {
            KeranjangManager.tambahObatByName(item.nama)
            onUpdate()
        }

        holder.btnKurang.setOnClickListener {
            KeranjangManager.kurangObat(item.nama)
            onUpdate()
        }
    }
}
