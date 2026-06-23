package com.example.pharmatic.suplier

import com.example.pharmatic.R

import com.example.pharmatic.model.Suplier

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class SuplierAdapter(
    private var listSuplier: List<Suplier>,
    private val onDeleteClick: (Suplier) -> Unit
) : RecyclerView.Adapter<SuplierAdapter.SuplierViewHolder>() {

    class SuplierViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama: TextView = itemView.findViewById(R.id.tvNamaSuplier)
        val alamat: TextView = itemView.findViewById(R.id.tvAlamatSuplier)
        val telp: TextView = itemView.findViewById(R.id.tvTelpSuplier)
        val ivLogo: android.widget.ImageView = itemView.findViewById(R.id.ivLogoSuplierItem)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEditSuplier)
        val btnHapus: ImageButton = itemView.findViewById(R.id.btnHapusSuplier)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuplierViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_suplier, parent, false)
        return SuplierViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuplierViewHolder, position: Int) {
        val suplier = listSuplier[position]
        holder.nama.text = suplier.nama
        holder.alamat.text = suplier.alamat
        holder.telp.text = suplier.telepon

        // Muat logo suplier dengan aman
        var logoLoaded = false
        if (!suplier.logoUri.isNullOrEmpty()) {
            try {
                val uri = Uri.parse(suplier.logoUri)
                val file = java.io.File(uri.path ?: "")
                if (file.exists()) {
                    holder.ivLogo.setImageURI(null)
                    holder.ivLogo.setImageURI(uri)
                    holder.ivLogo.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    holder.ivLogo.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                    logoLoaded = true
                }
            } catch (_: Exception) { }
        }

        if (!logoLoaded) {
            holder.ivLogo.setImageResource(R.drawable.delivery)
            holder.ivLogo.layoutParams.width = holder.itemView.context.resources.displayMetrics.density.toInt() * 28
            holder.ivLogo.layoutParams.height = holder.itemView.context.resources.displayMetrics.density.toInt() * 28
        }

        holder.btnEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditSuplierActivity::class.java)
            intent.putExtra("SUPLIER", suplier)
            holder.itemView.context.startActivity(intent)
        }

        holder.btnHapus.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Hapus Suplier")
                .setMessage("Apakah Anda yakin ingin menghapus ${suplier.nama}?")
                .setPositiveButton("Hapus") { _, _ ->
                    onDeleteClick(suplier)
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun getItemCount(): Int = listSuplier.size

    fun updateData(newList: List<Suplier>) {
        listSuplier = newList
        notifyDataSetChanged()
    }
}
