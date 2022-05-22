package com.example.angkotin

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.angkotin.fragmentDaftar.PenumpangFragment
import com.example.angkotin.fragmentDaftar.SupirFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PenumpangFragment()
            1 -> fragment = SupirFragment()
        }
        return fragment as Fragment
    }

}