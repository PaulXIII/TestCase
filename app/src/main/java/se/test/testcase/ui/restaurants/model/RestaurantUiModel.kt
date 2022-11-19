package se.test.testcase.ui.restaurants.model

data class RestaurantUiModel(
    val id: String,
    val imageUrl: String,
    val name: String,
    val tags: String,
    val deliveryTime: String,
    val rating: String,
    val status: RestaurantStatus,
    val filterIds: List<String>,
)