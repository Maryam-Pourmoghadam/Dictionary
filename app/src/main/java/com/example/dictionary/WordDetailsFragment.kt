package com.example.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dictionary.databinding.FragmentWordDetailsBinding


class WordDetailsFragment : Fragment() {
    lateinit var binding:com.example.dictionary.databinding.FragmentWordDetailsBinding
    val vmodel: MainViewModel by viewModels()
    var wordID=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        wordID=it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentWordDetailsBinding.inflate(inflater,container,false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()


        binding.buttonEdit.setOnClickListener {
        val action=WordDetailsFragmentDirections.actionWordDetailsFragmentToAddWordFragment(wordID)
            findNavController().navigate(action)
        }

        binding.buttonDelete.setOnClickListener {
            vmodel.deleteWord(wordID)
            findNavController().navigate(R.id.action_wordDetailsFragment_to_homeFragment)
        }
    }

    private fun initViews(){
        val word=vmodel.findWordByID(wordID)
        binding.textViewWord.text=word.word
        binding.textViewMeaning.text=word.meaning
        binding.textViewSynonyms.text=word.synonyms
        binding.textViewExample.text=word.example
    }
}