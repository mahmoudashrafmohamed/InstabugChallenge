package com.mahmoudashraf.instabugchallenge.words.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudashraf.instabugchallenge.R
import com.mahmoudashraf.instabugchallenge.words.model.WordUIModel

class WordsAdapter : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    private val words = mutableListOf<WordUIModel>()

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val wordTextView: TextView
        private val wordCountTextView: TextView

        init {
            wordTextView = itemView.findViewById(R.id.tv_word)
            wordCountTextView = itemView.findViewById(R.id.tv_word_count)
        }

        fun bind(wordUIModel: WordUIModel) {
            wordTextView.text = wordUIModel.name
            wordCountTextView.text = wordUIModel.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int {
        return words.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<WordUIModel>) {
        words.clear()
        words.addAll(list)
        notifyDataSetChanged()
    }

}