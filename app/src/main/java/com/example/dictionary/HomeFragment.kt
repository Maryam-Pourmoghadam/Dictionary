package com.example.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dictionary.database.Word
import com.example.dictionary.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val vmodel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmodel.wordsCounterLiveData.observe(viewLifecycleOwner) { number ->
            binding.textViewWordsCounter.text = number.toString()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addWordFragment)
        }

        binding.buttonSearch.setOnClickListener {
            val word: Word?
            if (isLanguageChoosed()) {
                word = if (binding.radioButtonEnglish.isChecked) {
                    vmodel.findEngWordByName(binding.editTextSearch.text.toString())
                } else {
                    vmodel.findPersianWordByName(binding.editTextSearch.text.toString())
                }

                if (word != null) {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToWordDetailsFragment(word.id)
                    findNavController().navigate(action)
                } else {
                    Snackbar.make(
                        binding.coordinatorLayout,
                        "لغت مورد نظر موجود نمی باشد",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }


            }

        }
    }

    private fun isLanguageChoosed(): Boolean {
        if (binding.radioButtonEnglish.isChecked == binding.radioButtonPersian.isChecked) {
            Snackbar.make(
                binding.coordinatorLayout,
                "برای جستجو لطفا يک زبان را انتخاب کنید",
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }
}