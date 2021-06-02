package com.inertia.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.databinding.ItemBencanaBinding
import com.inertia.ui.detail.DetailReportActivity

class BencanaAdapter : RecyclerView.Adapter<BencanaAdapter.BencanaViewHolder>() {

    private val listBencana = ArrayList<BencanaEntity>()
    var onItemClick: ((BencanaEntity) -> Unit)? = null

    fun setData(data: List<BencanaEntity>) {
        listBencana.clear()
        listBencana.addAll(data)
        notifyDataSetChanged()
    }

    inner class BencanaViewHolder(private val binding: ItemBencanaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bencana: BencanaEntity) {
            with(binding) {
                tvNamaBencana.text = bencana.namaBencana
                tvLokasiBencana.text = bencana.jenisBencana
                Glide.with(binding.root).load(bencana.linkFoto).into(binding.imgItem)


                itemView.setOnClickListener {
                    onItemClick?.invoke(listBencana[adapterPosition])

                    onItemClick = { selectedData ->
                        val intent = Intent(itemView.context, DetailReportActivity::class.java)
                        intent.putExtra(DetailReportActivity.EXTRA_REPORT, selectedData)
                        it.context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BencanaViewHolder {
        val binding = ItemBencanaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BencanaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BencanaViewHolder, position: Int) {
        holder.bind(listBencana[position])
    }

    override fun getItemCount() = listBencana.size
}