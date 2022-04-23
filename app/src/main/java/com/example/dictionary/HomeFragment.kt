package com.example.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dictionary.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {
lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addWordFragment)
        }

        binding.buttonSearch.setOnClickListener {
            if (isLanguageChoosed()){
               /* if (binding.radioButtonEnglish.isChecked){

                }else{

                }*/
            }

        }
    }
    private fun isLanguageChoosed():Boolean{
        if (binding.radioButtonEnglish.isChecked==binding.radioButtonPersian.isChecked) {
            Snackbar.make(binding.coordinatorLayout,"Please choose a language",Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}