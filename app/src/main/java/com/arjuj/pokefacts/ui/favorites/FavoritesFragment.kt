package com.arjuj.pokefacts.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.arjuj.domain.model.Pokemon
import com.arjuj.pokefacts.R
import com.arjuj.pokefacts.databinding.FragmentFavoritesBinding
import com.arjuj.pokefacts.ui.common.PokeRecyclerViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

private var binding: FragmentFavoritesBinding? = null
private lateinit var viewBinding: FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private val viewModel by viewModel<FavoritesViewModel>()
    private lateinit var adapter: PokeRecyclerViewAdapter

    override fun onResume() {
        super.onResume()
        viewModel.getFavoritePokemonList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        }
        viewBinding = binding!!
        setupObservers()
        setupAdapter()
        setupRecyclerView(adapter)
        return binding?.root
    }

    private fun setupObservers() {
        viewModel.myFavoritePokemons.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            if (it.isEmpty()) viewBinding.emptyFavoritesLayout.visibility = View.VISIBLE
            else viewBinding.emptyFavoritesLayout.visibility = View.GONE
        })
    }

    private fun setupAdapter() {
        adapter = PokeRecyclerViewAdapter(clickListener = {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToInfoFragment(it)
            Navigation.findNavController(requireView()).navigate(action)
        }, favoriteButtonClickListener = { pokemon: Pokemon, isSelected: Boolean ->
            if (isSelected) {
                viewModel.deleteFavoritePokemon(pokemon)
            } else {
                viewModel.addFavoritePokemon(pokemon)
            }
        }, false, isPokemonFavorite = {
            return@PokeRecyclerViewAdapter viewModel.isPokemonFavorite(it)
        }, null)
    }

    private fun setupRecyclerView(adapter: PokeRecyclerViewAdapter) {
        with(viewBinding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.grid_column_count))
            recyclerView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.bg_color
                )
            )
            recyclerView.hasFixedSize()
        }
    }
}