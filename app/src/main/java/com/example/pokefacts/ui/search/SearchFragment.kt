package com.example.pokefacts.ui.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonResults
import com.example.pokefacts.R
import com.example.pokefacts.databinding.FragmentSearchBinding
import com.example.pokefacts.ui.common.PokeRecyclerViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

private var binding: FragmentSearchBinding? = null
private lateinit var viewBinding: FragmentSearchBinding
private var allPokemonNamesList = PokemonResults(mutableListOf())

class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var adapter: PokeRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = FragmentSearchBinding.inflate(inflater, container, false)
        }
        viewBinding = binding!!
        setupObservers()
        setupViews()
        return binding?.root
    }

    private fun setupObservers() {
        viewModel.myPokemonNamesList.observe(viewLifecycleOwner, { result ->
            if (result is SearchViewModel.Result.Success) allPokemonNamesList = result.value
        })
        viewModel.mySearchedPokemon.observe(viewLifecycleOwner, { result ->
            when (result) {
                is SearchViewModel.Result.Success -> onSearchLoadingSuccess(result.value)
                is SearchViewModel.Result.Failure -> Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT)
                    .show()
                SearchViewModel.Result.Loading -> onSearchLoading()
            }
        })
    }

    private fun onSearchLoading() {
        with(viewBinding) {
            shimmerLayout.startShimmer()
            shimmerLayout.visibility = View.VISIBLE
            emptySearchLayout.visibility = View.GONE
        }
    }

    private fun onSearchLoadingSuccess(pokemonList: MutableList<Pokemon>) {
        with(viewBinding) {
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            if (searchEdittext.text!!.isNotEmpty()) {
                emptySearchLayout.visibility = View.GONE
                adapter.submitList(pokemonList)
            }
        }
    }

    private fun setupViews() {
        with(viewBinding) {
            setupAdapter()
            setupRecyclerView(adapter)
            emptySearchLayout.visibility = View.VISIBLE
            searchEdittext.setOnEditorActionListener { userEntryTextView, actionId, _ ->
                handlePokemonSearchAction(userEntryTextView, actionId)
                return@setOnEditorActionListener true
            }
            searchEdittext.doOnTextChanged { text, _, _, _ ->
                handleSearchBarTextChanged(text)
            }
            cancelSearchButton.setOnClickListener {
                searchEdittext.text!!.clear()
                adapter.submitList(mutableListOf())
            }
        }
    }

    private fun setupAdapter() {
        adapter = PokeRecyclerViewAdapter(clickListener = {
            val action = SearchFragmentDirections.actionSearchFragmentToInfoFragment(it)
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

    private fun handlePokemonSearchAction(userEntryTextView: TextView, actionId: Int) {
        hideKeyboard()
        if (allPokemonNamesList.results.isEmpty()) viewModel.getAllPokemonNames()
        viewBinding.searchEdittext.clearFocus()
        val searchResultsPokemonList = mutableListOf<Pokemon>()
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val searchEntry = userEntryTextView.text.toString().trim().lowercase()
            adapter.submitList(mutableListOf())
            if (searchEntry.isNotEmpty()) {
                viewModel.list.clear()
                allPokemonNamesList.results.forEach {
                    if (it.name == searchEntry || it.name.contains(searchEntry)) {
                        val trimmedUrl = it.url?.dropLast(1)
                        it.id = trimmedUrl!!.substring(trimmedUrl.lastIndexOf("/") + 1).toInt()
                        searchResultsPokemonList.add(it)
                    }
                }
                if (searchResultsPokemonList.isEmpty()) viewBinding.noResultsSearchLayout.visibility = View.VISIBLE
                else viewBinding.noResultsSearchLayout.visibility = View.GONE
                viewModel.getPokemon(searchResultsPokemonList)
            }
        }
    }

    private fun handleSearchBarTextChanged(text: CharSequence?) {
        with(viewBinding) {
            if (text!!.isNotEmpty()) {
                cancelSearchButton.visibility = View.VISIBLE
            } else {
                cancelSearchButton.visibility = View.GONE
                emptySearchLayout.visibility = View.VISIBLE
                noResultsSearchLayout.visibility = View.GONE
                viewModel.cancelJobIfRunning()
                adapter.submitList(mutableListOf())
            }
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
