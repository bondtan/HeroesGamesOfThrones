package com.tbondarenko.heroesgamesofthrones.ui.listfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbondarenko.heroesgamesofthrones.R
import com.tbondarenko.heroesgamesofthrones.databinding.HeroItemBinding
import com.tbondarenko.heroesgamesofthrones.domain.model.HeroDomain
import com.tbondarenko.heroesgamesofthrones.glide.load

class HeroesAdapter(private val onItemClicked: (HeroDomain, ImageView) -> Unit) :
    ListAdapter<HeroDomain, HeroesAdapter.HeroViewHolder>(DiffCallBack) {

    private var heroesList = mutableListOf<HeroDomain>()

    fun add(list: List<HeroDomain>) {
        heroesList = list.toMutableList()
        submitList(heroesList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val viewHolder = HeroViewHolder(
            HeroItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val imageView = viewHolder.itemView.findViewById<ImageView>(R.id.image_url_hero)
            onItemClicked(getItem(position),imageView)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HeroViewHolder(private val binding: HeroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: HeroDomain) {
            with(binding) {
                firstName.text = hero.firstName
                lastName.text = hero.lastName
                imageUrlHero.load(hero.imageUrl)
                ViewCompat.setTransitionName(imageUrlHero, "shared_image${hero.id}")
            }
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<HeroDomain>() {
            override fun areItemsTheSame(oldItem: HeroDomain, newItem: HeroDomain): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HeroDomain, newItem: HeroDomain): Boolean {
                return oldItem == newItem
            }
        }
    }
}