package se.test.testcase.data.repositories

import android.util.Log
import se.test.testcase.data.dto.FilterResponse
import se.test.testcase.data.dto.RestaurantResponse
import se.test.testcase.data.dto.RestaurantStatusResponse
import se.test.testcase.data.exceptions.GenericException
import se.test.testcase.data.exceptions.NetworkException
import se.test.testcase.data.repositories.api.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

/**
 * repository to handle external network call
 */
@Singleton
class HttpRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {

    private val tag = "httpRepository"

    fun getAllRestaurants(): RestaurantResponse {
        Log.d(tag, "fetching currencies from api...")

        val request = apiInterface.getRestaurants()

        val response = request.execute()

        if (!response.isSuccessful) {
            Log.w(
                tag,
                "a network error occurred during comunication, throwing a NetworkException ${response.errorBody()}"
            )
            throw NetworkException()
        }

        val responseBody = response.body()
        if (responseBody == null) {
            Log.w(
                tag,
                "an unexpected error occurred during comunication, throwing a GenericException ${response.errorBody()}"
            )
            throw GenericException()
        }

        Log.d(tag, "call executed, response body: $responseBody")

        return responseBody
    }


    fun getRestaurantStatus(restaurantId: String): RestaurantStatusResponse {
        Log.d(tag, "fetching currencies from api...")

        val request = apiInterface.getRestaurantStatus(restaurantId)

        val response = request.execute()

        if (!response.isSuccessful) {
            Log.w(
                tag,
                "a network error occurred during comunication, throwing a NetworkException ${response.errorBody()}"
            )
            throw NetworkException()
        }

        val responseBody = response.body()
        if (responseBody == null) {
            Log.w(
                tag,
                "an unexpected error occurred during comunication, throwing a GenericException ${response.errorBody()}"
            )
            throw GenericException()
        }

        Log.d(tag, "call executed, response body: $responseBody")

        return responseBody
    }

    fun getFilters(filterId: String): FilterResponse {
        Log.d(tag, "fetching currencies from api...")

        val request = apiInterface.getFilter(filterId)

        val response = request.execute()

        if (!response.isSuccessful) {
            Log.w(
                tag,
                "a network error occurred during comunication, throwing a NetworkException ${response.errorBody()}"
            )
            throw NetworkException()
        }

        val responseBody = response.body()
        if (responseBody == null) {
            Log.w(
                tag,
                "an unexpected error occurred during comunication, throwing a GenericException ${response.errorBody()}"
            )
            throw GenericException()
        }

        Log.d(tag, "call executed, response body: $responseBody")

        return responseBody
    }

}