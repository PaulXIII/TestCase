package se.test.testcase.ui.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import se.test.testcase.R
import se.test.testcase.ui.filter.model.FilterUiModel

class FilterAdapter(
    private val onClick: (item: FilterUiModel) -> Unit
) : ListAdapter<FilterUiModel, FilterAdapter.ViewHolder>(
    FilterModelCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filter_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val filterCard: MaterialCardView = itemView.findViewById(R.id.cvFilter)
        private val filterImage: AppCompatImageView = itemView.findViewById(R.id.ivFilterImage)
        private val filterName: AppCompatTextView = itemView.findViewById(R.id.tvFilterName)

        fun bind(item: FilterUiModel, onClick: (item: FilterUiModel) -> Unit) {
            itemView.setOnClickListener {
                onClick.invoke(item)
                filterCard.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, getCardColor(!item.isSelected))
                )
            }
            if (item.imageUrl.isNotEmpty()) {
                val options = RequestOptions.fitCenterTransform().transform(
                    RoundedCorners(24)
                )
                Glide.with(itemView.context)
                    .setDefaultRequestOptions(options)
                    .load(item.imageUrl)
                    .into(filterImage)
            }
            filterName.text = item.name
            val cardColor = getCardColor(item.isSelected)
            filterCard.setBackgroundColor(ContextCompat.getColor(itemView.context, cardColor))
        }

        private fun getCardColor(isSelected: Boolean) =
            if (isSelected) R.color.selected else R.color.background
    }

    object FilterModelCallback : DiffUtil.ItemCallback<FilterUiModel>() {
        override fun areItemsTheSame(
            oldItem: FilterUiModel,
            newItem: FilterUiModel
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FilterUiModel,
            newItem: FilterUiModel
        ): Boolean =
            oldItem == newItem
    }
}