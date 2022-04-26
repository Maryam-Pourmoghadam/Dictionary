package com.example.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dictionary.database.Word
import com.example.dictionary.databinding.FragmentAddWordBinding
import com.google.android.material.snackbar.Snackbar


class AddWordFragment : Fragment() {
    lateinit var binding: FragmentAddWordBinding
    val vmodel: MainViewModel by viewModels()
    var wordID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordID = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (wordID != -1) {
            initViews()
        }

        binding.buttonSave.setOnClickListener {

            if (areValidInputs()) {
                val tempWord = Word(
                    0, binding.editTextWord.text.toString(),
                    binding.editTextMeaning.text.toString(),
                    binding.editTextSynonym.text.toString(),
                    binding.editTextExample.text.toString()
                )

                if (wordID != -1) {
                    vmodel.updateWord(
                        Word(
                            wordID,
                            tempWord.word,
                            tempWord.meaning,
                            tempWord.synonyms,
                            tempWord.example,"",
                            vmodel.findWordByID(wordID).isFavorite
                        )
                    )
                } else {
                    vmodel.addWord(tempWord)
                }
                Toast.makeText(requireContext(), "کلمه با موفقیت ذخیره شد", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_addWordFragment_to_homeFragment)
            }
        }


    }

    private fun initViews() {
        val word = vmodel.findWordByID(wordID)
        binding.editTextWord.setText(word.word)
        binding.editTextMeaning.setText(word.meaning)
        binding.editTextSynonym.setText(word.synonyms)
        binding.editTextExample.setText(word.example)
    }

    private fun areValidInputs(): Boolean {
        if (binding.editTextWord.text.isNullOrBlank()) {
            binding.editTextWord.error = "لطفا فیلد را پر کنید"
            return false
        }

        if (binding.editTextMeaning.text.isNullOrBlank()) {
            binding.editTextMeaning.error = "لطفا فیلد را پر کنید"
            return false
        }

        if (binding.editTextExample.text.isNullOrBlank()) {
            binding.editTextExample.error = "لطفا فیلد را پر کنید"
            return false
        }
        if (binding.editTextSynonym.text.isNullOrBlank()) {
            binding.editTextSynonym.error = "لطفا فیلد را پر کنید"
            return false
        }

        return true

    }

}