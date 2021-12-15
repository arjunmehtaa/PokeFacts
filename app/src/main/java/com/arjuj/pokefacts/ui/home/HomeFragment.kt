package com.arjuj.pokefacts.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjuj.data.utils.Constants
import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.model.Type
import com.arjuj.domain.model.TypeX
import com.arjuj.pokefacts.R
import com.arjuj.pokefacts.databinding.FragmentHomeBinding
import com.arjuj.pokefacts.ui.common.PokeRecyclerViewAdapter
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

private var binding: FragmentHomeBinding? = null
private lateinit var viewBinding: FragmentHomeBinding
private lateinit var currentType: String
var pokemonsDisplayed: Int = 0
var loading: Boolean = false
var failureFlag = false
var allTypesList: MutableList<Pokemon> = mutableListOf()
var toLoadList: MutableList<Pokemon> = mutableListOf()
var pagingFlag: Boolean = true

class HomeFragment : Fragment() {

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: PokeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonsDisplayed = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
        }
        viewBinding = binding!!
        setupViews()
        setupObservers()
        return binding?.root
    }

    private fun onLoadingSuccess(pokemonList: MutableList<Pokemon>, adapter: PokeRecyclerViewAdapter) {
        with(viewBinding) {
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            noInternetLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
        verifyPokemonListType(pokemonList)
        adapter.submitList(pokemonList.toMutableList())
        pokemonsDisplayed = pokemonList.size
        loading = false
    }

    private fun onLoadingFailure() {
        with(viewBinding) {
            if (pokemonsDisplayed == 0) noInternetLayout.visibility = View.VISIBLE
            else Toast.makeText(context, resources.getString(R.string.check_internet_connection), Toast.LENGTH_SHORT)
                .show()
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
        }
        failureFlag = true
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        onConnectionRestored()
                    }
                })
            }
        }
    }

    private fun setupObservers() {
        viewModel.myPokemon.observe(viewLifecycleOwner, { result ->
            when (result) {
                is HomeViewModel.Result.Failure -> onLoadingFailure()
                HomeViewModel.Result.Loading -> loading = true
                is HomeViewModel.Result.Success -> onLoadingSuccess(result.value, adapter)
            }
        })
        viewModel.myTypePokemon.observe(viewLifecycleOwner, { result ->
            if (result is HomeViewModel.Result.Success && allTypesList.isEmpty()) {
                toLoadList.clear()
                result.value.results.forEach {
                    val trimmedUrl = it.pokemon.url?.dropLast(1)
                    it.pokemon.id = trimmedUrl!!.substring(trimmedUrl.lastIndexOf("/") + 1).toInt()
                    if (it.pokemon.id <= Constants.TOTAL_POKEMONS) allTypesList.add(it.pokemon)
                }
                setupAdapter(allTypesList.size)
                for (i in 0 until Constants.POKEMONS_LOAD_LIMIT) {
                    toLoadList.add(result.value.results[i].pokemon)
                }
                viewModel.getPokemon(toLoadList)
            }
        })
        viewModel.myPokemonNamesList.observe(viewLifecycleOwner, { result ->
            if (result is HomeViewModel.Result.Success && allTypesList.isEmpty()) {
                toLoadList.clear()
                result.value.results.forEach {
                    val trimmedUrl = it.url?.dropLast(1)
                    it.id = trimmedUrl!!.substring(trimmedUrl.lastIndexOf("/") + 1).toInt()
                    if (it.id <= Constants.TOTAL_POKEMONS) allTypesList.add(it)
                }
                setupAdapter(allTypesList.size)
                for (i in 0 until Constants.POKEMONS_LOAD_LIMIT) {
                    toLoadList.add(result.value.results[i])
                }
                if (viewBinding.allTypesChip.isChecked) {
                    viewModel.getPokemon(toLoadList)
                }
            }
        })
    }

    private fun setupViews() {
        with(viewBinding) {
            setupAdapter(allTypesList.size)
            showLoadingAnimation()
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_checked)
                ),
                intArrayOf(
                    ContextCompat.getColor(requireContext(), R.color.selector_default),
                    ContextCompat.getColor(requireContext(), R.color.selector_checked)
                )
            )
            currentType = resources.getString(R.string.all_types).lowercase()
            customToolbarTextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.title_color))
            typesChipGroup.forEach {
                val chip = it as Chip
                chip.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
                    chipCheckChanged(compoundButton as Chip, isChecked)
                }
                chip.setChipBackgroundColorResource(R.color.bottom_nav_background)
                chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.chip_text_color_selector))
                chip.setTextColor(colorStateList)
                chip.setChipStrokeColorResource(R.color.chip_border_color_selector)
            }
        }
    }

    private fun setupAdapter(lastPosition: Int) {
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
        }, lastPosition)
        setupRecycler(adapter)
    }

    private fun setupRecycler(adapter: PokeRecyclerViewAdapter) {
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
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (pagingFlag) callGetPokemon()
                }
            })
        }
    }

    private fun showLoadingAnimation() {
        if (pokemonsDisplayed == 0) {
            viewBinding.shimmerLayout.startShimmer()
            viewBinding.shimmerLayout.visibility = View.VISIBLE
        }
    }

    private fun chipCheckChanged(chip: Chip, isChecked: Boolean) {
        if (isChecked) {
            allTypesList.clear()
            toLoadList.clear()
            pokemonsDisplayed = 0
            viewModel.list.clear()
            val chipText = chip.text.toString().lowercase()
            currentType = chipText
            if (chipText == resources.getString(R.string.all_types).lowercase()) {
                viewModel.getAllPokemonNames()
            } else {
                viewModel.getPokemonOfType(chipText)
            }
            showLoadingAnimation()
            viewBinding.recyclerView.visibility = View.INVISIBLE
        }
    }

    private fun callGetPokemon() {
        if (!viewBinding.recyclerView.canScrollVertically(1) && !loading && pokemonsDisplayed >= Constants.POKEMONS_LOAD_LIMIT) {
            toLoadList.clear()
            if (allTypesList.size - pokemonsDisplayed >= 20) {
                for (i in pokemonsDisplayed until pokemonsDisplayed + Constants.POKEMONS_LOAD_LIMIT) {
                    toLoadList.add(allTypesList[i])
                }
                pokemonsDisplayed += Constants.POKEMONS_LOAD_LIMIT
            } else if (pokemonsDisplayed < allTypesList.size) {
                for (i in pokemonsDisplayed until allTypesList.size) {
                    toLoadList.add(allTypesList[i])
                }
                pokemonsDisplayed = allTypesList.size
            }
            viewModel.getPokemon(toLoadList)
        }
    }

    private fun onConnectionRestored() {
        if (failureFlag) {
            activity?.runOnUiThread { viewBinding.noInternetLayout.visibility = View.GONE }
            if (toLoadList.isNotEmpty()) {
                viewModel.getPokemon(toLoadList)
                activity?.runOnUiThread { showLoadingAnimation() }
                failureFlag = false
            } else {
                viewBinding.typesChipGroup.forEach { chip ->
                    if ((chip as Chip).isChecked) {
                        activity?.runOnUiThread { chipCheckChanged(chip, true) }
                    }
                }
            }
        }
    }

    private fun verifyPokemonListType(pokemonList: MutableList<Pokemon>) {
        if (currentType != resources.getString(R.string.all_types).lowercase()) {
            val type = Type(TypeX(currentType))
            pokemonList.forEach {
                if (!it.types.contains(type)) {
                    pokemonList.remove(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding.allTypesChip.isChecked = true
        viewBinding.horizontalChips.scrollTo(0, 0)
        allTypesList.clear()
    }
}