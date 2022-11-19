package se.test.testcase.data.repositories.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import se.test.testcase.data.dto.FilterResponse
import se.test.testcase.data.dto.RestaurantResponse
import se.test.testcase.data.dto.RestaurantStatusResponse

interface ApiInterface {

    @GET("restaurants")
    fun getRestaurants(): Call<RestaurantResponse>

    @GET("open/{restaurant_id}")
    fun getRestaurantStatus(@Path("restaurant_id") restaurantId: String): Call<RestaurantStatusResponse>

    @GET("filter/{id}")
    fun getFilter(@Path("id") filterId: String): Call<FilterResponse>
}