package se.test.testcase.data.dto

import com.google.gson.annotations.SerializedName

data class RestaurantsItem(

	@field:SerializedName("delivery_time_minutes")
	val deliveryTimeMinutes: Int? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("filterIds")
	val filterIds: List<String?>? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)