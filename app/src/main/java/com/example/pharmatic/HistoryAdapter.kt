package com.example.pharmatic

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private var listHistory: List<Transaksi>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tvIdTransaksi)
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggal)
        val tvTotal: TextView = view.findViewById(R.id.tvTotalHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val transaksi = listHistory[position]
        holder.tvId.text = transaksi.idTransaksi
        holder.tvTanggal.text = transaksi.tanggal
        holder.tvTotal.text = "Rp %,d".format(transaksi.totalHarga).replace(',', '.')
        
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailHistoryActivity::class.java)
            intent.putExtra("TRANSAKSI", transaksi)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listHistory.size

    fun updateData(newList: List<Transaksi>) {
        listHistory = newList
        notifyDataSetChanged()
    }
}
