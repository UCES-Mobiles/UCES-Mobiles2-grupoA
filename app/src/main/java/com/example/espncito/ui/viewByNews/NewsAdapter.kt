package com.example.espncito.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appparcial2.model.Headline
import com.example.espncito.R

class NewsAdapter(
    private var newsList: List<Headline>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivNew)
        val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val headline = newsList[position]

        // Set title
        holder.titleTextView.text = headline.title

        // Set description
        holder.descriptionTextView.text = headline.description ?: ""

        // Load image with grey placeholder
        val imageUrl = headline.images?.firstOrNull()?.url
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(ColorDrawable(Color.LTGRAY)) // placeholder gris claro
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = newsList.size

    // Actualizar la lista de noticias
    fun setData(newList: List<Headline>) {
        newsList = newList
        notifyDataSetChanged()
    }
}
