package se.test.testcase.domain.usecases

import android.util.Log
import se.test.testcase.data.dto.RestaurantsItem
import se.test.testcase.data.exceptions.GenericException
import se.test.testcase.data.exceptions.NetworkException
import se.test.testcase.data.repositories.HttpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsUseCase @Inject constructor(
    private val httpRepository: HttpRepository,
) {

    private val tag = "RestaurantsUseCases"

    @Throws(NetworkException::class, GenericException::class)
    fun getAllRestaurants(): List<RestaurantsItem> {
        try {
            val restaurantResponse = httpRepository.getAllRestaurants()
            return restaurantResponse.restaurants?.filterNotNull().orEmpty()
        } catch (networkException: NetworkException) {
            // rethrow the network exception, we already handled it
            Log.d(tag, "a network exception occurred, rethrowing it")
            throw networkException
        } catch (exception: Exception) {
            Log.w(tag, "an exception occurred: ${exception.message}", exception)
            //throw a custom generic exception to handle all others unexpected errors
            throw GenericException()
        }
    }
}