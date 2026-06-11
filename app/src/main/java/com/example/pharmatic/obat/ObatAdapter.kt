package com.example.pharmatic.obat

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.R
import com.example.pharmatic.keranjang.KeranjangManager

class ObatAdapter(
    private var listObat: List<Obat>
) : RecyclerView.Adapter<ObatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.tvNama)
        val harga: TextView = itemView.findViewById(R.id.tvHarga)
        val stok: TextView = itemView.findViewById(R.id.tvStok)
        val jenis: TextView = itemView.findViewById(R.id.tvJenisObat)
        val thumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)
        val expiredDate: TextView = itemView.findViewById(R.id.tvExpiredDate)
        val btnTambah: Button = itemView.findViewById(R.id.btnTambah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_obat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listObat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obat = listObat[position]

        holder.nama.text = obat.nama
        holder.harga.text = "Rp ${obat.harga}"
        holder.stok.text = "Stok : ${obat.stok}"
        holder.jenis.text = obat.jenis

        if (obat.expiredDate.isNullOrEmpty()) {
            holder.expiredDate.text = "ED: -"
        } else {
            holder.expiredDate.text = "ED: ${obat.expiredDate}"
        }

        if (obat.imageUri.isNullOrEmpty()) {
            holder.thumbnail.setImageResource(R.drawable.drugs)
        } else {
            try {
                holder.thumbnail.setImageURI(Uri.parse(obat.imageUri))
            } catch (e: Exception) {
                holder.thumbnail.setImageResource(R.drawable.drugs)
            }
        }

        holder.btnTambah.setOnClickListener {
            KeranjangManager.daftarKeranjang.add(obat)
            KeranjangManager.saveCart(holder.itemView.context)

            Toast.makeText(
                holder.itemView.context,
                "✅ 1 ${obat.nama} ditambahkan ke keranjang",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun updateData(dataBaru: List<Obat>) {
        listObat = dataBaru
        notifyDataSetChanged()
    }
}
