package se.test.testcase.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import se.test.testcase.R

class RestaurantDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RestaurantDetailsFragment()
    }
}