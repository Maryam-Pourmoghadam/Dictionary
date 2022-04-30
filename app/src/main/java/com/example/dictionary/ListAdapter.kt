package com.example.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.database.Word


class ListAdapter(var dataset: List<Word>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    lateinit var itemlistener:onItemClickListener
    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        itemlistener=listener
    }

    class ListViewHolder(itemView: View, listener:onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val persianTv = itemView.findViewById<TextView>(R.id.tv_persian_word)
        val englishTv = itemView.findViewById<TextView>(R.id.tv_english_word)
        val isFavoriteIcon = itemView.findViewById<TextView>(R.id.tv_icon_star)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view,itemlistener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.englishTv.text = dataset[position].word
        holder.persianTv.text = dataset[position].meaning
        if (dataset[position].isFavorite) {
            holder.isFavoriteIcon.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_star_24_yellow,
                0,
                0,
                0
            )
        }
    }

    override fun getItemCount(): Int = dataset.size
}




