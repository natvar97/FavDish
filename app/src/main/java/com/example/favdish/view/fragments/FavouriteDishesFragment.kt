package com.example.favdish.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favdish.databinding.FragmentFavouriteDishesBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.view.activities.MainActivity
import com.example.favdish.view.adapters.FavDishAdapter
import com.example.favdish.viewmodel.FavDishViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteDishesFragment : Fragment() {

    private var mBinding: FragmentFavouriteDishesBinding? = null

    @Inject
    lateinit var mFavDishViewModel: FavDishViewModel

    @Inject
    lateinit var favDishAdapter: FavDishAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentFavouriteDishesBinding.inflate(inflater, container, false)

        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding!!.rvFavDishesList.layoutManager = GridLayoutManager(requireActivity(), 2)

        favDishAdapter.addFragment(this@FavouriteDishesFragment)

        mBinding!!.rvFavDishesList.adapter = favDishAdapter

        mFavDishViewModel.allFavouriteDishesList.observe(viewLifecycleOwner, { favDishList ->
            favDishList?.let {
                if (it.isNotEmpty()) {
                    mBinding!!.rvFavDishesList.visibility = View.VISIBLE
                    mBinding!!.tvNoFavDishesAddedYet.visibility = View.GONE
                    favDishAdapter.updateList(favDishList)
                } else {
                    mBinding!!.rvFavDishesList.visibility = View.GONE
                    mBinding!!.tvNoFavDishesAddedYet.visibility = View.VISIBLE
                }
            }
        })
    }

    fun favDishDetails(favDish: FavDish) {
        findNavController().navigate(
            FavouriteDishesFragmentDirections.actionNavigationFavouriteDishesToNavigationDishDetails(
                favDish
            )
        )
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigation()
        }
    }

    override fun onResume() {
        super.onResume()

        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigation()
        }
    }
}