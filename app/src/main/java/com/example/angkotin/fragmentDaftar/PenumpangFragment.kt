package com.example.angkotin.fragmentDaftar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.angkotin.ui.LoginActivity
import com.example.angkotin.R
import com.example.angkotin.viewModel.RegisterViewModel
import com.example.angkotin.databinding.FragmentPenumpangBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PenumpangFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPenumpangBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel
    private lateinit var nameR: String
    private lateinit var nikR: String
    private lateinit var pNumberR: String
    private lateinit var passR: String
    private lateinit var err: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPenumpangBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.linkMasuk.setOnClickListener(this)

        binding.btnDaftar.setOnClickListener {
            getData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getData(){
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            RegisterViewModel::class.java)

        binding.apply {
            nameR = edtNama.text.toString()
            nikR = edtNik.text.toString()
            pNumberR = edtNomorHp.text.toString()
            passR = edtPasswordDaftar.text.toString()
        }

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)

        viewModel.setRegister(nameR, nikR, pNumberR, passR)

        viewModel.getRegister().observe(viewLifecycleOwner, {
            if (it != null){
                with(it){
                    err = error.toString()
                    Log.d("Error message", err)
                }
            }
            if (err == "true"){
                Toast.makeText(activity, "Anda Sudah Terdaftar\nTunggu Sebentar", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    delay(2000L)
                    val mIntent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(mIntent)
                }
            }else{
                Toast.makeText(activity, "Gagal Daftar", Toast.LENGTH_LONG).show()
            }
        })
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