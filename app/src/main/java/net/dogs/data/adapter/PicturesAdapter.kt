package net.dogs.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.dogs.data.local.Dog
import net.dogs.databinding.ItemDogBinding

class PicturesAdapter(
    private val dogs: List<Dog>
) : RecyclerView.Adapter<PicturesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDogBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PicturesAdapter.ViewHolder {
        val view = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PicturesAdapter.ViewHolder, position: Int) {
        Glide.with(holder.binding.imgDog.context)
            .load(dogs[position].url)
            .into(holder.binding.imgDog)
    }

    override fun getItemCount() = dogs.size

}