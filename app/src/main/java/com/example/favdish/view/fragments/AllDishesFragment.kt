package com.example.favdish.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favdish.R
import com.example.favdish.databinding.DialogCustomListBinding
import com.example.favdish.databinding.FragmentAllDishesBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.utils.Constants
import com.example.favdish.view.activities.AddUpdateDishActivity
import com.example.favdish.view.activities.MainActivity
import com.example.favdish.view.adapters.CustomListItemAdapter
import com.example.favdish.view.adapters.FavDishAdapter
import com.example.favdish.viewmodel.FavDishViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllDishesFragment : Fragment() {

    private lateinit var mBinding: FragmentAllDishesBinding

    @Inject
    lateinit var mFavDishAdapter: FavDishAdapter

    private lateinit var mCustomListDialog: Dialog

    @Inject
    lateinit var favDishViewModel: FavDishViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAllDishesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvDishesList.layoutManager = GridLayoutManager(requireActivity(), 2)

        mFavDishAdapter.addFragment(this@AllDishesFragment)

        mBinding.rvDishesList.adapter = mFavDishAdapter

        favDishViewModel.allDishesList.observe(viewLifecycleOwner, { dishes ->
            dishes?.let {
                if (dishes.isNotEmpty()) {
                    mBinding.rvDishesList.visibility = View.VISIBLE
                    mBinding.tvNoDishesAddedYet.visibility = View.GONE
                    mFavDishAdapter.updateList(it)
                } else {
                    mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                    mBinding.rvDishesList.visibility = View.GONE
                }
            }
        })
    }

    fun dishDetails(favDish: FavDish) {
        findNavController().navigate(
            AllDishesFragmentDirections.actionNavigationAllDishesToDishDetailsFragment(
                favDish
            )
        )

        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)?.hideBottomNavigation()
        }

    }

    override fun onResume() {
        super.onResume()

        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)?.showBottomNavigation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
            R.id.action_filter_dishes -> {
                filterDishesListDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteFavDish(favDish: FavDish) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.title_delete_dish))
            .setMessage(resources.getString(R.string.msg_delete_dish_dialog, favDish.title))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(resources.getString(R.string.lbl_yes)) { dialogInterface, _ ->
                favDishViewModel.delete(favDish)
                dialogInterface.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.lbl_no)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun filterDishesListDialog() {
        mCustomListDialog = Dialog(requireActivity())

        val binding = DialogCustomListBinding.inflate(layoutInflater)
        mCustomListDialog.setContentView(binding.root)
        val dishTypes = Constants.dishTypes()
        dishTypes.add(0, Constants.ALL_ITEMS)
        binding.tvTitle.text = resources.getString(R.string.title_select_item_to_filter)
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter =
            CustomListItemAdapter(
                requireActivity(),
                this@AllDishesFragment,
                dishTypes,
                Constants.FILTER_SELECTION
            )
        mCustomListDialog.show()
    }

    fun filterSelection(filterItemSelection: String) {
        mCustomListDialog.dismiss()
        Log.e("Filter Selection", filterItemSelection)

        if (filterItemSelection == Constants.ALL_ITEMS) {
            favDishViewModel.allDishesList.observe(viewLifecycleOwner) { dishes ->
                dishes?.let {
                    if (dishes.isNotEmpty()) {
                        mBinding.rvDishesList.visibility = View.VISIBLE
                        mBinding.tvNoDishesAddedYet.visibility = View.GONE
                        mFavDishAdapter.updateList(it)

                    } else {
                        mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                        mBinding.rvDishesList.visibility = View.GONE
                    }
                }
            }
        } else {
            favDishViewModel.getFilteredDishesList(filterItemSelection)
                .observe(viewLifecycleOwner) { dishes ->
                    dishes?.let {
                        if (it.isNotEmpty()) {
                            mBinding.rvDishesList.visibility = View.VISIBLE
                            mBinding.tvNoDishesAddedYet.visibility = View.GONE
                            mFavDishAdapter.updateList(it)
                        } else {
                            mBinding.rvDishesList.visibility = View.GONE
                            mBinding.tvNoDishesAddedYet.text = resources.getString(
                                R.string.lbl_dishes_not_fount,
                                filterItemSelection
                            )
                            mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                        }
                    }
                }
        }
    }
}