package se.test.testcase.ui.restaurants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.test.testcase.data.dto.RestaurantsItem
import se.test.testcase.domain.usecases.RestaurantCategoriesUseCase
import se.test.testcase.domain.usecases.RestaurantStatusUseCase
import se.test.testcase.domain.usecases.RestaurantsUseCase
import se.test.testcase.ui.restaurants.model.FilterUiModel
import se.test.testcase.ui.restaurants.model.RestaurantStatus
import se.test.testcase.ui.restaurants.model.RestaurantUiModel
import se.test.testcase.ui.state.State
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val restaurantsUseCase: RestaurantsUseCase,
    private val restaurantStatusUseCase: RestaurantStatusUseCase,
    private val restaurantCategoriesUseCase: RestaurantCategoriesUseCase,
) : ViewModel() {

    private val _restaurants = MutableStateFlow<State<List<RestaurantUiModel>>>(State.loading())
    val restaurants: StateFlow<State<List<RestaurantUiModel>>> get() = _restaurants.asStateFlow()

    fun loadRestaurants() {
        viewModelScope.launch {
            _restaurants.value = withContext(Dispatchers.IO) {
                try {
                    val restaurantsItemList = restaurantsUseCase.getAllRestaurants()
                    val restaurantsStatus = restaurantStatusUseCase
                        .getRestaurantStatus(restaurantsItemList.mapNotNull { it.id })
                        .mapValues {
                            if (it.value) {
                                RestaurantStatus.OPEN
                            } else {
                                RestaurantStatus.CLOSED
                            }
                        }
                    val categories = getRestaurantCategory(restaurantsItemList)
                    val restaurants = restaurantsItemList.map {
                        RestaurantUiModel(
                            id = it.id.orEmpty(),
                            imageUrl = it.imageUrl.orEmpty(),
                            name = it.name.orEmpty(),
                            tags = getTags(it, categories),
                            deliveryTime = it.deliveryTimeMinutes?.let { time ->
                                "$time min"
                            }.orEmpty(),
                            rating = it.rating?.toString().orEmpty(),
                            status = restaurantsStatus[it.id] ?: RestaurantStatus.CLOSED,
                            filterIds = it.filterIds?.filterNotNull().orEmpty()
                        )
                    }

                    restaurantsItemList.forEach {
                        Log.d("Item", "Id: ${it?.id}")
                        Log.d("Item", "Name: ${it?.name}")
                        Log.d("Item", "Rating: ${it?.rating}")
                        Log.d("Item", "=======")
                    }
                    State.success(restaurants)
                } catch (exception: Exception) {
                    State.error(exception.message)
                }
            }
        }
    }

    private fun getTags(
        item: RestaurantsItem,
        categories: Map<String, FilterUiModel>
    ): String {
        val separator = " • "
        val tags = item.filterIds?.mapNotNull { categories[it]?.name }
        return if (!tags.isNullOrEmpty()) {
            val result = StringBuilder()
            tags.forEachIndexed { index, s ->
                result.append(s)
                if (index != tags.size - 1)
                    result.append(separator)
            }
            result.toString()
        } else {
            ""
        }
    }

    private fun getRestaurantCategory(restaurants: List<RestaurantsItem?>): Map<String, FilterUiModel> {
        val result = HashMap<String, FilterUiModel>()
        val ids = mutableSetOf<String>()
        restaurants.forEach { item ->
            item?.filterIds?.filterNotNull()?.let { ids.addAll(it) }
        }
        ids.forEach { filterId ->
            val response = restaurantCategoriesUseCase.getRestaurantCategory(filterId)
            Log.d("ItemResponse", "Id: ${response.id}")
            Log.d("ItemResponse", "Name: ${response.name}")
            Log.d("ItemResponse", "======")
            result[filterId] = FilterUiModel(
                id = filterId,
                name = response.name.orEmpty(),
                imageUrl = response.imageUrl.orEmpty()
            )
        }
        return result
    }
}

