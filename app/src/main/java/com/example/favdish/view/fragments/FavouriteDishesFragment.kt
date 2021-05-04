package com.example.favdish.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favdish.application.FavDishApplication
import com.example.favdish.databinding.FragmentFavouriteDishesBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.view.activities.MainActivity
import com.example.favdish.view.adapters.FavDishAdapter
import com.example.favdish.viewmodel.DashboardViewModel
import com.example.favdish.viewmodel.FavDishViewModel
import com.example.favdish.viewmodel.FavDishViewModelFactory

class FavouriteDishesFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private var mBinding: FragmentFavouriteDishesBinding? = null

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

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

        val favDishAdapter = FavDishAdapter(this@FavouriteDishesFragment)

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