package se.test.testcase.ui.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import se.test.testcase.R
import se.test.testcase.ui.restaurants.model.RestaurantUiModel

class RestaurantAdapter(
    private val onClick: (item: RestaurantUiModel) -> Unit
) : ListAdapter<RestaurantUiModel, RestaurantAdapter.ViewHolder>(
    RestaurantModelCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: AppCompatImageView = itemView.findViewById(R.id.ivRestaurantImage)
        private val name: AppCompatTextView = itemView.findViewById(R.id.tvRestaurantName)
        private val tags: AppCompatTextView = itemView.findViewById(R.id.tvRestaurantTags)
        private val deliveryTime: AppCompatTextView = itemView.findViewById(R.id.tvDeliveryTime)
        private val rating: AppCompatTextView = itemView.findViewById(R.id.tvRating)

        fun bind(item: RestaurantUiModel, onClick: (item: RestaurantUiModel) -> Unit) {
            itemView.setOnClickListener { onClick.invoke(item) }
            if (item.imageUrl.isNotEmpty()) {
                val options = RequestOptions.fitCenterTransform().transform(
                    GranularRoundedCorners(
                        12f, 12f, 0f, 0f
                    )
                )
                Glide.with(itemView.context)
                    .setDefaultRequestOptions(options)
                    .load(item.imageUrl)
                    .into(image)
            }
            name.text = item.name
            tags.text = item.tags
            deliveryTime.text = item.deliveryTime
            rating.text = item.rating
        }
    }

    object RestaurantModelCallback : DiffUtil.ItemCallback<RestaurantUiModel>() {
        override fun areItemsTheSame(
            oldItem: RestaurantUiModel,
            newItem: RestaurantUiModel
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: RestaurantUiModel,
            newItem: RestaurantUiModel
        ): Boolean =
            oldItem == newItem
    }
}