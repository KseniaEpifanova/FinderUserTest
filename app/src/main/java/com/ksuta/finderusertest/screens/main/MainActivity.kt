package com.ksuta.finderusertest.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*
import android.R
import androidx.navigation.NavController
import com.ksuta.finderusertest.utils.findNavController

class MainActivity : AppCompatActivity(com.ksuta.finderusertest.R.layout.activity_main) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val navController: NavController by lazy { findNavController(com.ksuta.finderusertest.R.id.main_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityComponent.init(this).inject(this)
        setSupportActionBar(toolbarView)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            onBackPressed()
            val backStackEntryCount = supportFragmentManager.backStackEntryCount
            if (backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
