package com.example.pharmatic

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.obat.Obat

class ObatAdminAdapter(
    private var listObat: List<Obat>,
    private val onDeleteClick: (Obat) -> Unit = {}
) : RecyclerView.Adapter<ObatAdminAdapter.ObatViewHolder>() {

    class ObatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama: TextView = itemView.findViewById(R.id.tvNamaObat)
        val stok: TextView = itemView.findViewById(R.id.tvStokObat)
        val harga: TextView = itemView.findViewById(R.id.tvHargaObat)
        val thumbnail: ImageView = itemView.findViewById(R.id.ivThumbnailAdmin)
        val expiredDate: TextView = itemView.findViewById(R.id.tvExpiredDateAdmin)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEditObat)
        val btnHapus: ImageButton = itemView.findViewById(R.id.btnHapusObat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_obat_admin, parent, false)
        return ObatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        val obat = listObat[position]
        holder.nama.text = obat.nama
        holder.stok.text = "Stok: ${obat.stok}"
        holder.harga.text = "Rp ${obat.harga}"
        
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

        holder.btnEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditObatActivity::class.java)
            intent.putExtra("OBAT", obat)
            holder.itemView.context.startActivity(intent)
        }

        holder.btnHapus.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Hapus Obat")
                .setMessage("Apakah Anda yakin ingin menghapus ${obat.nama}?")
                .setPositiveButton("Hapus") { _, _ ->
                    onDeleteClick(obat)
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun getItemCount(): Int = listObat.size

    fun updateData(newList: List<Obat>) {
        listObat = newList
        notifyDataSetChanged()
    }
}
