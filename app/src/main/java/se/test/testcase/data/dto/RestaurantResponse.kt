package se.test.testcase.data.dto

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem?>? = null
)