package com.example.favdish.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.favdish.databinding.ItemCustomListLayoutBinding
import com.example.favdish.view.activities.AddUpdateDishActivity
import com.example.favdish.view.fragments.AllDishesFragment

class CustomListItemAdapter(
    private val activity: Activity,
    private val fragment : Fragment?,
    private val itemsList: List<String>,
    private val selection: String
) : RecyclerView.Adapter<CustomListItemAdapter.CustomListItemViewHolder>() {

    class CustomListItemViewHolder(
        itemView: ItemCustomListLayoutBinding
    ) : RecyclerView.ViewHolder(itemView.root) {
        val tvText = itemView.tvText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomListItemViewHolder {
        val binding: ItemCustomListLayoutBinding = ItemCustomListLayoutBinding
            .inflate(LayoutInflater.from(activity), parent, false)
        return CustomListItemViewHolder(binding )
    }

    override fun onBindViewHolder(holder: CustomListItemViewHolder, position: Int) {
        holder.tvText.text = itemsList[position]

        holder.itemView.setOnClickListener {
            if (activity is AddUpdateDishActivity){
                activity.selectedListItem(itemsList[position] , selection)
            }
            if (fragment is AllDishesFragment) {
                fragment.filterSelection(itemsList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}