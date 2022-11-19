package se.test.testcase.data.dto

import com.google.gson.annotations.SerializedName

data class RestaurantStatusResponse(

	@field:SerializedName("is_currently_open")
	val isCurrentlyOpen: Boolean? = null,

	@field:SerializedName("restaurant_id")
	val restaurantId: String? = null
)
