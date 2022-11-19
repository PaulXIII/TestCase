package se.test.testcase.ui.restaurants.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import se.test.testcase.R

enum class RestaurantStatus(
    @StringRes val text: Int,
    @ColorRes val textColor: Int,
) {
    OPEN(R.string.status_open, R.color.positive),
    CLOSED(R.string.status_closed, R.color.negative),
}