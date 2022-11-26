package com.example.finally_med


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    //create three objs for fragments as create in java?
    val homeFragment = HomeFragment()
    val camFragment = CamFragment()
    val settingFragment = SettingFragment()
    val guideFragment = GuideFragment()
    val btmNav: BottomNavigationView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btmNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        btmNav.setBackgroundResource(0)

        val mFab = findViewById<FloatingActionButton>(R.id.fab)
        mFab.setOnClickListener() {
            val intent = Intent(this,CamActivity::class.java)
            startActivity(intent)
        }


        makeCurrentFragment (homeFragment)


        btmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> makeCurrentFragment(homeFragment)

                R.id.nav_setting -> makeCurrentFragment(settingFragment)
            }
            true
        }

    }



    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fram_layout, fragment).commit()



    }






}