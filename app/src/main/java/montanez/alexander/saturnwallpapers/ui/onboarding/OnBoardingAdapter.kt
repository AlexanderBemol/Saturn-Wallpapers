package montanez.alexander.saturnwallpapers.ui.onboarding

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import montanez.alexander.saturnwallpapers.R
import montanez.alexander.saturnwallpapers.model.OnBoardingItem


class OnBoardingAdapter(
    private val onBoardingItems : List<OnBoardingItem>
    ) : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_onboarding_container, parent, false
                )
            )
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.setOnBoardingData(onBoardingItems[position])
    }

    override fun getItemCount(): Int = onBoardingItems.size

    class OnBoardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private var textDescription: TextView = itemView.findViewById(R.id.textDescription)
        private var imageOnBoarding: ImageView = itemView.findViewById(R.id.imageOnBoarding)

        fun setOnBoardingData(onBoardingItem: OnBoardingItem) {
            textTitle.text = onBoardingItem.title
            textDescription.text = onBoardingItem.description
            imageOnBoarding.setImageResource(onBoardingItem.image)
            if(imageOnBoarding.drawable is AnimatedVectorDrawable){
                (imageOnBoarding.drawable as AnimatedVectorDrawable).start()
            }
        }
    }
}