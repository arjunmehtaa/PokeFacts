package com.example.pokefacts.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.utils.Constants
import com.example.domain.model.Pokemon
import com.example.pokefacts.ui.common.PokeRecyclerViewAdapter
import com.example.pokefacts.R
import com.example.pokefacts.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private var viewBinding: FragmentHomeBinding? = null
private lateinit var recyclerView: RecyclerView
var pokemonsDisplayed: Int = 0
var loading: Boolean = false
var failureFlag = false

class HomeFragment : Fragment() {

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: PokeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (viewBinding == null) {
            viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        }
        adapter = PokeRecyclerViewAdapter(clickListener = {
            val action = HomeFragmentDirections.actionHomeFragmentToInfoFragment(it)
            findNavController(requireView()).navigate(action)
        }, favoriteButtonClickListener = { pokemon: Pokemon, isSelected: Boolean ->
            if (isSelected) {
                viewModel.deleteFavoritePokemon(pokemon)
            } else {
                viewModel.addFavoritePokemon(pokemon)
            }
        }, true, isPokemonFavorite = {
            return@PokeRecyclerViewAdapter viewModel.isPokemonFavorite(it)
        })
        viewBinding?.shimmerLayout?.startShimmer()
        viewBinding?.shimmerLayout?.visibility = View.VISIBLE
        setRecycler(adapter)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.myPokemon.observe(viewLifecycleOwner, { result ->
            when (result) {
                is HomeViewModel.Result.Failure -> onLoadingFailure()
                HomeViewModel.Result.Loading -> loading = true
                is HomeViewModel.Result.Success -> onLoadingSuccess(result.value, adapter)
            }
        })
    }

    private fun onLoadingSuccess(pokemonList: MutableList<Pokemon>, adapter: PokeRecyclerViewAdapter) {
        viewBinding?.shimmerLayout?.stopShimmer()
        viewBinding?.shimmerLayout?.visibility = View.GONE
        viewBinding?.noInternetLayout?.visibility = View.GONE
        if(pokemonList.size > pokemonsDisplayed){
            pokemonsDisplayed += if (pokemonsDisplayed == Constants.POKEMONS_BEFORE_LAST_GROUP)
                Constants.LAST_GROUP_LOAD_LIMIT else Constants.POKEMONS_LOAD_LIMIT
        }
        adapter.submitList(pokemonList.toMutableList())
        loading = false
    }

    private fun onLoadingFailure() {
        if(pokemonsDisplayed == 0) viewBinding?.noInternetLayout?.visibility = View.VISIBLE
        else Toast.makeText(context, resources.getString(R.string.check_internet_connection),Toast.LENGTH_SHORT).show()
        viewBinding?.shimmerLayout?.stopShimmer()
        viewBinding?.shimmerLayout?.visibility = View.GONE
        failureFlag = true
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        if(failureFlag) {
                            viewModel.getPokemon(Constants.POKEMONS_LOAD_LIMIT, pokemonsDisplayed)
                            failureFlag = false
                        }
                    }
                })
            }
        }
    }

    private fun setRecycler(adapter: PokeRecyclerViewAdapter) {
        recyclerView = viewBinding?.recyclerView!!
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg_color))
        recyclerView.hasFixedSize()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                callGetPokemon()
            }
        })
    }

    private fun callGetPokemon() {
        if (!recyclerView.canScrollVertically(1) && !loading && pokemonsDisplayed >= Constants.POKEMONS_LOAD_LIMIT) {
            if (pokemonsDisplayed < Constants.POKEMONS_BEFORE_LAST_GROUP) {
                viewModel.getPokemon(Constants.POKEMONS_LOAD_LIMIT, pokemonsDisplayed)
            } else if (pokemonsDisplayed == Constants.POKEMONS_BEFORE_LAST_GROUP) {
                viewModel.getPokemon(Constants.LAST_GROUP_LOAD_LIMIT, pokemonsDisplayed)
            }
        }
    }
}