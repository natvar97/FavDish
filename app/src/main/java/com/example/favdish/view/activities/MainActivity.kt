package com.example.favdish.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.favdish.R
import com.example.favdish.databinding.ActivityMainBinding
import com.example.favdish.model.notification.NotificationWorker
import com.example.favdish.utils.Constants
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //for the navigating the fragments in mainActivity
        mNavController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_all_dishes,
                R.id.navigation_favourite_dishes,
                R.id.navigation_random_dish
            )
        )

        //join together fragment and bottomNavigation
        setupActionBarWithNavController(mNavController, appBarConfiguration)

        //setting up the nav controller with bottom navigation
        mBinding.navView.setupWithNavController(mNavController)

        if (intent.hasExtra(Constants.NOTIFICATION_ID)) {
            val notificationId = intent.getIntExtra(Constants.NOTIFICATION_ID, 0)
            Log.i("notification_id", "$notificationId")
            mBinding.navView.selectedItemId = R.id.navigation_random_dish
        }

        startWork()
    }

    private fun createConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresCharging(false)
        .setRequiresBatteryNotLow(true)
        .build()

    private fun createWorkRequest() =
        PeriodicWorkRequestBuilder<NotificationWorker>(720, TimeUnit.MINUTES)
            .setConstraints(createConstraints())
            .build()

    private fun startWork() {
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "FavDish Notify Work",
                ExistingPeriodicWorkPolicy.KEEP,
                createWorkRequest()
            )
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, null)
    }

    fun hideBottomNavigation() {
        mBinding.navView.clearAnimation()
        mBinding.navView.animate().translationY(mBinding.navView.height.toFloat()).duration = 300

        mBinding.navView.visibility = View.GONE

    }

    fun showBottomNavigation() {
        mBinding.navView.clearAnimation()
        mBinding.navView.animate().translationY(0f).duration = 300

        mBinding.navView.visibility = View.VISIBLE
    }
}