package se.test.testcase.ui.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import se.test.testcase.R
import se.test.testcase.databinding.FragmentRestaurantListBinding
import se.test.testcase.ui.details.RestaurantDetailsFragment
import se.test.testcase.ui.filter.FilterAdapter
import se.test.testcase.ui.filter.model.FilterUiModel
import se.test.testcase.ui.restaurants.model.RestaurantUiModel
import se.test.testcase.ui.state.Status

@AndroidEntryPoint
class RestaurantListFragment : Fragment() {

    companion object {
        fun newInstance() = RestaurantListFragment()
    }

    private val viewModel: RestaurantListViewModel by viewModels()

    private val restaurantAdapter = RestaurantAdapter {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fvContainer, RestaurantDetailsFragment.newInstance(it))
            .addToBackStack(null)
            .commit()
    }

    private val filterAdapter = FilterAdapter {
        viewModel.showRestaurantsWithFilter(it)
    }

    private lateinit var binding: FragmentRestaurantListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantListBinding.inflate(inflater)
        setupFilterRecycler()
        setupRecycler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRestaurants()
        viewModel
            .filters
            .onEach { state ->
                when (state.status) {
                    Status.SUCCESS -> showFilterData(state.data)
                    else -> {
                        hideFilters()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.filteredRestaurants.onEach { state ->
            when (state.status) {
                Status.SUCCESS -> showData(state.data)
                else -> {

                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun loadRestaurants() {
        viewModel.loadRestaurants()
        viewModel
            .restaurants
            .onEach { state ->
                when (state.status) {
                    Status.SUCCESS -> {
                        showData(state.data)
                    }
                    Status.LOADING -> {
                        showLoadingValues()
                    }
                    Status.ERROR -> {
                        showError(state.message)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showLoadingValues() {
        binding.recyclerview.visibility = View.GONE
        binding.circularProgress.visibility = View.VISIBLE
    }

    private fun showData(restaurants: List<RestaurantUiModel>?) {
        binding.circularProgress.visibility = View.GONE
        if (restaurants != null) {
            binding.recyclerview.visibility = View.VISIBLE
            restaurantAdapter.submitList(restaurants)
        } else {
            binding.recyclerview.visibility = View.GONE
        }
    }

    private fun showFilterData(filters: List<FilterUiModel>?) {
        if (filters != null && filters.isNotEmpty()) {
            binding.filterRecyclerview.visibility = View.VISIBLE
            filterAdapter.submitList(filters)
        } else {
            hideFilters()
        }
    }

    private fun hideFilters() {
        binding.filterRecyclerview.visibility = View.GONE
    }

    private fun setupRecycler() = with(binding.recyclerview) {
        layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = restaurantAdapter
    }

    private fun setupFilterRecycler() = with(binding.filterRecyclerview) {
        layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapter = filterAdapter
    }

}