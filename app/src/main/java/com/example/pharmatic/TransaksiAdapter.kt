package com.example.pharmatic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.obat.Obat

class TransaksiAdapter(
    private var listObat: List<Obat>,
    private val onTambahClick: (Obat) -> Unit
) : RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {

    class TransaksiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNamaObat)
        val tvHarga: TextView = view.findViewById(R.id.tvHargaObat)
        val btnTambah: Button = view.findViewById(R.id.btnTambah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi, parent, false)
        return TransaksiViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        val obat = listObat[position]
        holder.tvNama.text = obat.nama
        holder.tvHarga.text = "Rp ${obat.harga}"
        holder.btnTambah.setOnClickListener {
            onTambahClick(obat)
        }
    }

    override fun getItemCount(): Int = listObat.size

    fun updateData(newList: List<Obat>) {
        listObat = newList
        notifyDataSetChanged()
    }
}
