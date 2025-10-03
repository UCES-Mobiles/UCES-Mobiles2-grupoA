package com.example.espncito.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.espncito.databinding.ActivityMainBinding
import com.example.espncito.ui.viewByLeague.ViewByLeagueFragment
import com.example.espncito.ui.viewByTeam.ViewByTeamActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupViewPager()

        //Prueba para ir al activity de viewByTeam, eliminar luego de pruebas.
/*        val intent = Intent(this, ViewByTeamActivity::class.java)
        startActivity(intent)
        finish()*/
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
    }

    // Inner class para el adaptador del ViewPager
    private inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> NewsFragment()
                1 -> ViewByLeagueFragment()
                else -> NewsFragment()
            }
        }
    }
}