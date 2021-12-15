package com.arjuj.pokefacts.ui.info

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.model.Type
import com.arjuj.pokefacts.R
import com.arjuj.pokefacts.databinding.FragmentInfoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

private lateinit var viewBinding: FragmentInfoBinding

class InfoFragment : Fragment() {

    private val viewModel by viewModel<InfoViewModel>()
    private var dominantColor = Color.GRAY

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.setShowHideAnimationEnabled(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentInfoBinding.inflate(inflater, container, false)
        arguments?.let { bundle ->
            setData(InfoFragmentArgs.fromBundle(bundle).pokemon)
        }
        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        activity?.window?.statusBarColor = dominantColor
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)?.visibility =
            View.GONE
        activity?.window?.navigationBarColor = dominantColor
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.bg_color)
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)?.visibility =
            View.VISIBLE
        activity?.window?.navigationBarColor =
            ContextCompat.getColor(requireContext(), R.color.bg_color)
    }

    private fun setData(pokemon: Pokemon) {
        with(viewBinding) {
            pokeId.text = requireContext().getString(R.string.pokemon_number_format, pokemon.id)
            pokeName.text = pokemon.name
            pokeGenera.text = pokemon.genera
            pokeInfoDesc.text = pokemon.description
            pokeCaptureRate.text = pokemon.capture_rate.toString()
            pokeXp.text = pokemon.base_experience.toString()
            pokeHeight.text = getString(R.string.height_format, (pokemon.height.times(10)))
            pokeWeight.text = getString(R.string.weight_format, (pokemon.weight.div(10.0)))
            pokeHp.text = pokemon.stats[0].base_stat.toString()
            pokeAttack.text = pokemon.stats[1].base_stat.toString()
            pokeDefense.text = pokemon.stats[2].base_stat.toString()
            pokeSpecialAttack.text = pokemon.stats[3].base_stat.toString()
            pokeSpecialDefense.text = pokemon.stats[4].base_stat.toString()
            pokeSpeed.text = pokemon.stats[5].base_stat.toString()
            pokeInfoTypeOne.text = pokemon.types[0].type.name
            pokeBack.setOnClickListener { activity?.onBackPressed() }
            setPokemonTypes(pokemon.types)
            if (viewModel.isPokemonFavorite(pokemon.id)) setFavoriteIcon(pokeFav)
            loadImage(pokeInfoImage, pokemon.sprites.other.official_artwork.front_default)
            pokeFav.setOnClickListener { favoriteButtonClickListener(pokeFav, pokemon) }
            dominantColor = pokemon.dominant_color!!
            pokeScrollView.setBackgroundColor(dominantColor)
            activity?.window?.statusBarColor = dominantColor
        }
    }

    private fun favoriteButtonClickListener(pokeFav : ImageView, pokemon: Pokemon){
        if(pokeFav.tag == resources.getString(R.string.favorite_tag)){
            viewModel.deleteFavoritePokemon(pokemon)
            removeFavoriteIcon(pokeFav)
        } else{
            viewModel.addFavoritePokemon(pokemon)
            setFavoriteIcon(pokeFav)
        }
    }

    private fun setFavoriteIcon(imageView: ImageView) {
        imageView.setImageResource(R.drawable.favorite)
        imageView.tag = getString(R.string.favorite_tag)
    }

    private fun removeFavoriteIcon(imageView: ImageView) {
        imageView.setImageResource(R.drawable.favorite_border)
        imageView.tag = getString(R.string.not_favorite_tag)
    }

    private fun setPokemonTypes(types : List<Type>){
        with(viewBinding){
            if(types.size>1){
                pokeInfoTypeTwo.text = types[1].type.name
                pokeInfoTypeTwo.visibility = View.VISIBLE
            } else{
                pokeInfoTypeTwo.visibility = View.GONE
            }
        }
    }

    private fun loadImage(pokeInfoImage: ImageView, imageUrl: String) {
        Glide.with(requireContext().applicationContext)
            .asBitmap()
            .load(imageUrl)
            .centerCrop()
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
                    return false
                }
            })
            .into(pokeInfoImage)
    }
}