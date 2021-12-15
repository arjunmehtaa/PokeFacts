package com.arjuj.pokefacts.ui.common

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arjuj.domain.model.Pokemon
import com.arjuj.pokefacts.R
import com.arjuj.pokefacts.databinding.PokemonItemBinding
import com.arjuj.pokefacts.databinding.ShimmerProgressAnimationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class PokeRecyclerViewAdapter(
    private val clickListener: (Pokemon) -> Unit,
    private val favoriteButtonClickListener: (Pokemon, Boolean) -> Unit,
    var showLoadingAnimation: Boolean,
    private val isPokemonFavorite: (Int) -> Boolean,
    private val lastPosition: Int?
) : ListAdapter<Pokemon, PokeRecyclerViewAdapter.PokemonViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view: View? = null
        if (viewType == 1) {
            val itemBinding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PokemonViewHolder(itemBinding)
        } else if (viewType == 2 || viewType == 3) {
            val itemBinding =
                ShimmerProgressAnimationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PokemonViewHolder(itemBinding)
        }
        return PokemonViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        if (holder.itemViewType == 3) {
            holder.itemView.layoutParams.height = 0
            holder.itemView.visibility = View.GONE
        }
        if (holder.itemViewType == 1) {
            if (getItem(position) != null) holder.bind(getItem(position))
        }
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var pokemonItemBinding: PokemonItemBinding
        private lateinit var shimmerProgressAnimationBinding: ShimmerProgressAnimationBinding

        constructor(itemBinding: PokemonItemBinding) : this(itemBinding.root) {
            this.pokemonItemBinding = itemBinding
        }

        constructor(itemBinding: ShimmerProgressAnimationBinding) : this(itemBinding.root) {
            this.shimmerProgressAnimationBinding = itemBinding
        }

        private var dominantColor: Int = Color.GRAY

        fun bind(pokemon: Pokemon) {
            with(pokemonItemBinding) {
                if (pokemon.dominant_color != null) {
                    cardView.setCardBackgroundColor(pokemon.dominant_color!!)
                } else {
                    cardView.setCardBackgroundColor(dominantColor)
                }
                setPokemonImages(pokemon)
                favoriteButton.isChecked = isPokemonFavorite(pokemon.id)
                favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                    if(favoriteButton.isPressed) favoriteButtonClickListener(pokemon, !isChecked)
                }
                pokemonNumber.text = pokemonName.context.getString(R.string.pokemon_number_format, pokemon.id)
                pokemon.name = pokemon.name.replaceFirstChar { it.uppercase() }
                pokemonName.text = pokemon.name
                pokeInfoTypeOne.text = pokemon.types[0].type.name
                if (pokemon.types.size > 1) {
                    pokeInfoTypeTwo.visibility = View.VISIBLE
                    pokeInfoTypeTwo.text = pokemon.types[1].type.name
                } else pokeInfoTypeTwo.visibility = View.GONE
                cardView.setOnClickListener {
                    pokemon.dominant_color = dominantColor
                    clickListener(pokemon)
                }
            }
        }

        private fun setPokemonImages(pokemon: Pokemon) {
            Glide.with(itemView.context.applicationContext)
                .asBitmap()
                .load(pokemon.sprites.other.official_artwork.front_default)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (resource != null) {
                            setBgColor(resource)
                        }
                        return false
                    }
                })
                .centerCrop()
                .into(pokemonItemBinding.pokemonImage)
        }

        private fun setBgColor(resource: Bitmap) {
            Palette.Builder(resource).generate {
                it?.let { palette ->
                    when (pokemonItemBinding.pokemonImage.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> {
                            dominantColor = palette.getMutedColor(Color.GRAY)
                        }
                        Configuration.UI_MODE_NIGHT_NO -> {
                            dominantColor = palette.getLightMutedColor(Color.GRAY)
                        }
                    }
                    pokemonItemBinding.cardView.setCardBackgroundColor(dominantColor)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (lastPosition != null) {
            if (position == lastPosition) 3 else if (position == currentList.size) 2 else 1
        } else {
            if (position == currentList.size) 2 else 1
        }
    }

    override fun getItemCount(): Int {
        if (super.getItemCount() == 0) {
            return super.getItemCount()
        } else if (showLoadingAnimation) {
            return super.getItemCount() + 1
        }
        return super.getItemCount()
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem
        }
    }
}
