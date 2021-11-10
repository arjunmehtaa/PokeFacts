package com.example.pokefacts.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Pokemon
import com.example.pokefacts.ui.common.PokeRecyclerViewAdapter
import com.example.pokefacts.R
import com.example.pokefacts.databinding.FragmentFavoritesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private var viewBinding: FragmentFavoritesBinding? = null

class FavoritesFragment : Fragment() {

    private val viewModel by viewModel<FavoritesViewModel>()
    private lateinit var toolbarTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokeRecyclerViewAdapter

    override fun onResume() {
        super.onResume()
        toolbarTextView = requireActivity().findViewById(R.id.toolbar_title)
        toolbarTextView.text = getString(R.string.favorites)
        viewModel.getFavoritePokemonList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewBinding == null) {
            viewBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        }
        viewModel.myFavoritePokemons.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        adapter = PokeRecyclerViewAdapter(clickListener = {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToInfoFragment(it)
            Navigation.findNavController(requireView()).navigate(action)
        }, favoriteButtonClickListener = { pokemon: Pokemon, isSelected : Boolean ->
            if(isSelected){
                viewModel.deleteFavoritePokemon(pokemon)
            } else{
                viewModel.addFavoritePokemon(pokemon)
            }
        }, false, isPokemonFavorite = {
            return@PokeRecyclerViewAdapter viewModel.isPokemonFavorite(it)
        })

        setRecycler(adapter)
        return viewBinding?.root
    }

    override fun onStop() {
        super.onStop()
        toolbarTextView.text = getString(R.string.app_name)
    }

    private fun setRecycler(adapter: PokeRecyclerViewAdapter) {
        recyclerView = viewBinding?.recyclerView!!
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg_color))
        recyclerView.hasFixedSize()
    }
}