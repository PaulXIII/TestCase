package se.test.testcase.ui.details

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import se.test.testcase.databinding.FragmentRestaurantDetailsBinding
import se.test.testcase.ui.restaurants.model.RestaurantUiModel

class RestaurantDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantDetailsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantDetailsBinding.inflate(inflater)
        binding.ivBackArrow.setOnClickListener { onBackPressed() }
        return binding.root
    }

    private fun onBackPressed() {
        parentFragmentManager.popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(KEY_ITEM, RestaurantUiModel::class.java)
        } else {
            arguments?.getParcelable(KEY_ITEM)
        }
        item?.let { setData(it) }
    }

    private fun setData(item: RestaurantUiModel) {
        if (item.imageUrl.isNotEmpty()) {
            val options = RequestOptions.fitCenterTransform().transform(
                GranularRoundedCorners(
                    12f, 12f, 0f, 0f
                )
            )
            Glide.with(requireActivity())
                .setDefaultRequestOptions(options)
                .load(item.imageUrl)
                .into(binding.ivRestaurantImage)
        }
        binding.tvRestaurantName.text = item.name
        binding.tvRestaurantTags.text = item.tags
        binding.tvRestaurantStatus.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                item.status.textColor
            )
        )
        binding.tvRestaurantStatus.setText(item.status.text)
    }

    companion object {

        private const val KEY_ITEM = "key_item"

        @JvmStatic
        fun newInstance(item: RestaurantUiModel) = RestaurantDetailsFragment().apply {
            arguments = bundleOf(
                KEY_ITEM to item
            )
        }
    }
}