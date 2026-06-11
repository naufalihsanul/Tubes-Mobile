package com.example.pharmatic

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class KasirAdapter(
    private var listKasir: List<Kasir>,
    private val onDeleteClick: (Kasir) -> Unit
) : RecyclerView.Adapter<KasirAdapter.KasirViewHolder>() {

    class KasirViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNamaKasir)
        val tvUsername: TextView = view.findViewById(R.id.tvUsernameKasir)
        val ivFoto: android.widget.ImageView = view.findViewById(R.id.ivKasirItemPhoto)
        val btnEdit: ImageButton = view.findViewById(R.id.btnEditKasir)
        val btnHapus: ImageButton = view.findViewById(R.id.btnHapusKasir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KasirViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kasir, parent, false)
        return KasirViewHolder(view)
    }

    override fun onBindViewHolder(holder: KasirViewHolder, position: Int) {
        val kasir = listKasir[position]
        holder.tvNama.text = kasir.nama
        holder.tvUsername.text = "@${kasir.username}"
        
        if (!kasir.fotoUri.isNullOrEmpty()) {
            holder.ivFoto.setImageURI(Uri.parse(kasir.fotoUri))
            holder.ivFoto.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            holder.ivFoto.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            holder.ivFoto.setImageResource(R.drawable.grouping)
            // Restore default icon size if no photo
            holder.ivFoto.layoutParams.width = holder.itemView.context.resources.displayMetrics.density.toInt() * 28
            holder.ivFoto.layoutParams.height = holder.itemView.context.resources.displayMetrics.density.toInt() * 28
        }
        
        holder.btnEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditKasirActivity::class.java)
            intent.putExtra("KASIR", kasir)
            holder.itemView.context.startActivity(intent)
        }
        
        holder.btnHapus.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Hapus Kasir")
                .setMessage("Apakah Anda yakin ingin menghapus ${kasir.nama}?")
                .setPositiveButton("Hapus") { _, _ ->
                    onDeleteClick(kasir)
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun getItemCount(): Int = listKasir.size

    fun updateData(newList: List<Kasir>) {
        listKasir = newList
        notifyDataSetChanged()
    }
}
