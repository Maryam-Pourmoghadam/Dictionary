package com.example.dictionary

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dictionary.database.Word
import com.example.dictionary.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val vmodel: MainViewModel by viewModels()
    var adapter: ListAdapter? = null
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

        //set observer to livedata list of words and listener to items in recyclerView

        vmodel.allWordsLivedata?.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter = ListAdapter(it)
                binding.rvWordList.adapter = adapter
            }

            adapter?.setOnItemClickListener(object : ListAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val id = vmodel.allWordsLivedata?.value?.get(position)?.id
                    val action = id?.let {
                        HomeFragmentDirections.actionHomeFragmentToWordDetailsFragment(
                            it
                        )
                    }
                    if (action != null) {
                        findNavController().navigate(action)
                    }
                }

            })
        }


        vmodel.findEngWordLiveData.observe(viewLifecycleOwner) {
            check(it)
        }
        vmodel.findPerWordLiveData.observe(viewLifecycleOwner){
            check(it)
        }
        vmodel.getNumberOfWords().observe(viewLifecycleOwner){
                number ->
            binding.textViewWordsCounter.text = number.toString()
        }

       /* vmodel.wordsCounterLiveData.observe(viewLifecycleOwner) { number ->
            binding.textViewWordsCounter.text = number.toString()
        }*/

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addWordFragment)
        }

        binding.buttonSearch.setOnClickListener {
            var word: Word? = null
            if (vmodel.wordsCounterLiveData.value == 0) {
                showDialog("دیتابیس خالی است")
            } else {
                if (isLanguageChoosed()) {
                    val input = binding.editTextSearch.text.toString()
                    if (binding.radioButtonEnglish.isChecked) {
                        vmodel.findEngWordByName(input)
                    } else {
                        vmodel.findPersianWordByName(input)
                    }

                }
            }

        }
    }

    fun check(word: Word?) {

        if (word != null) {
            val action =
                HomeFragmentDirections.actionHomeFragmentToWordDetailsFragment(word!!.id)
            findNavController().navigate(action)
        } else {
            showDialog("لغت مورد نظر موجود نمی باشد")
            /* Snackbar.make(
            binding.coordinatorLayout,
            "لغت مورد نظر موجود نمی باشد",
            Snackbar.LENGTH_SHORT
        ).show()*/

        }
    }

    private fun showDialog(message: String) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage(message)
            // if the dialog is cancelable
            .setCancelable(false)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()

            })

        val alert = dialogBuilder.create()
        alert.setTitle("هشدار")
        alert.show()
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