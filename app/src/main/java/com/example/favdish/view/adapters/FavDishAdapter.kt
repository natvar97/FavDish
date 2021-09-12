package com.example.favdish.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favdish.R
import com.example.favdish.databinding.ItemDishLayoutBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.utils.Constants
import com.example.favdish.view.activities.AddUpdateDishActivity
import com.example.favdish.view.fragments.AllDishesFragment
import com.example.favdish.view.fragments.FavouriteDishesFragment
import javax.inject.Inject

class FavDishAdapter @Inject constructor() : RecyclerView.Adapter<FavDishAdapter.FavDishViewHolder>() {

    private var dishesList: List<FavDish> = listOf()

    lateinit var mFragment: Fragment

    class FavDishViewHolder(itemView: ItemDishLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvDishTitle = itemView.tvDishTitle
        val ivDishImage = itemView.ivDishImage
        val ibMore = itemView.ibMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavDishViewHolder {
        val binding: ItemDishLayoutBinding = ItemDishLayoutBinding
            .inflate(LayoutInflater.from(mFragment.context), parent, false)
        return FavDishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavDishViewHolder, position: Int) {
        val dish = dishesList[position]

        Glide.with(mFragment)
            .load(dish.image)
            .into(holder.ivDishImage)

        holder.tvDishTitle.text = dish.title

        holder.itemView.setOnClickListener {
            if (mFragment is AllDishesFragment) {
                (mFragment as AllDishesFragment).dishDetails(dish)
            }
            if (mFragment is FavouriteDishesFragment) {
                (mFragment as FavouriteDishesFragment).favDishDetails(dish)
            }
        }

        holder.ibMore.setOnClickListener {

            val popUp = PopupMenu(mFragment.context, holder.ibMore)
            popUp.menuInflater.inflate(R.menu.menu_adapter, popUp.menu)

            popUp.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit_dish) {
                    val intent =
                        Intent(mFragment.requireActivity(), AddUpdateDishActivity::class.java)
                    intent.putExtra(Constants.EXTRA_DISH_DETAILS, dish)
                    mFragment.requireActivity().startActivity(intent)

                } else if (it.itemId == R.id.action_delete_dish) {
                    if (mFragment is AllDishesFragment) {
                        (mFragment as AllDishesFragment).deleteFavDish(dish)
                    }
                }
                true
            }
            popUp.show()
        }



        if (mFragment is AllDishesFragment) {
            holder.ibMore.visibility = View.VISIBLE
        } else if (mFragment is FavouriteDishesFragment) {
            holder.ibMore.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dishesList.size
    }

    fun updateList(list: List<FavDish>) {
        dishesList = list
        notifyDataSetChanged()
    }

    fun addFragment(fragment: Fragment) {
        mFragment = fragment
    }

}