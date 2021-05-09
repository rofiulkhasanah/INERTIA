package com.inertia.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.inertia.R
import com.inertia.databinding.ActivityMainBinding
import com.inertia.ui.home.HomeFragment
import com.inertia.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomBar.background = null

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()

        moveFragment(homeFragment)

        binding.bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.nav_home -> moveFragment(homeFragment)
                R.id.nav_profile -> moveFragment(profileFragment)
            }
            true
        }
    }

    private fun moveFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.wrapper, fragment)
            commit()
        }
}