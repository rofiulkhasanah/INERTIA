package com.inertia.ui.terdampak

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.inertia.data.datasource.local.entity.TerdampakEntity
import com.inertia.databinding.ItemTerdampakBinding
import com.inertia.ui.detailassessment.DetailAssessmentActivity

class TerdampakAdapter : RecyclerView.Adapter<TerdampakAdapter.TerdampakViewHolder>() {

    private val listTerdampak = ArrayList<TerdampakEntity>()
    var onItemClick: ((TerdampakEntity) -> Unit)? = null

    fun setData(data: List<TerdampakEntity>) {
        listTerdampak.clear()
        listTerdampak.addAll(data)
        notifyDataSetChanged()
    }

    inner class TerdampakViewHolder(private val binding: ItemTerdampakBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(terdampak: TerdampakEntity) {
            with(binding) {
                tvNamaBencana.text = terdampak.namaBencana
                tvAlamat.text = terdampak.alamat

                itemView.setOnClickListener {
                    onItemClick?.invoke(listTerdampak[adapterPosition])

                    onItemClick = { selectedData ->
                        val intent = Intent(itemView.context, DetailAssessmentActivity::class.java)
                        intent.putExtra(DetailAssessmentActivity.idKasus, selectedData.idKasus)
                        Toast.makeText(itemView.context, "${DetailAssessmentActivity.idKasus} : ${selectedData.idKasus}", Toast.LENGTH_SHORT).show()
                        it.context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerdampakViewHolder {
        val binding =
            ItemTerdampakBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TerdampakViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TerdampakViewHolder, position: Int) {
        holder.bind(listTerdampak[position])
    }

    override fun getItemCount(): Int = listTerdampak.size
}