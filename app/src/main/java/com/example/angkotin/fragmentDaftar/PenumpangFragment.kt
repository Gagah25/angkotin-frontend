package com.example.angkotin.fragmentDaftar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.angkotin.LoginActivity
import com.example.angkotin.R
import com.example.angkotin.databinding.FragmentPenumpangBinding

class PenumpangFragment : Fragment(), View.OnClickListener {

    lateinit var linkMasuk: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_penumpang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        linkMasuk = view.findViewById(R.id.link_masuk)


        linkMasuk.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.link_masuk -> {
                val mIntent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(mIntent)
            }
        }

    }


}