package com.example.dictionary

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dictionary.databinding.FragmentWordDetailsBinding


class WordDetailsFragment : Fragment() {
    lateinit var binding: com.example.dictionary.databinding.FragmentWordDetailsBinding
    lateinit var webView: WebView
    val vmodel: MainViewModel by viewModels()
    var wordID = -1
    var showWebView = false
    var isFavorite=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordID = it.getInt("id")
            isFavorite=vmodel.findWordByID(wordID).isFavorite
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = binding.webViewWikipedia
        setWebviewSetting()
        initViews()


        binding.buttonEdit.setOnClickListener {
            val action =
                WordDetailsFragmentDirections.actionWordDetailsFragmentToAddWordFragment(wordID)
            findNavController().navigate(action)
        }

        binding.buttonDelete.setOnClickListener {
            vmodel.deleteWord(wordID)
            findNavController().navigate(R.id.action_wordDetailsFragment_to_homeFragment)
        }

        binding.buttonWikipedia.setOnClickListener {
            val link=vmodel.findWordByID(wordID).wikipediaLink
            val action =WordDetailsFragmentDirections.actionWordDetailsFragmentToWikipediaWebViewFragment(link)
            findNavController().navigate(action)
            /*showWebView=(!showWebView)
            if (showWebView){
                webView.visibility = View.VISIBLE
                //webView.isVerticalScrollBarEnabled = true
                webView.loadUrl(vmodel.findWordByID(wordID).wikipediaLink)
            }else{
                webView.visibility = View.GONE
            }*/

        }

        binding.buttonFavorite.setOnClickListener {
            isFavorite=(!isFavorite)
            val word=vmodel.findWordByID(wordID)
            word.isFavorite=isFavorite
            vmodel.updateWord(word)
            if (isFavorite) {
                binding.buttonFavorite.setIconTintResource(R.color.yellow)
            }else{
                binding.buttonFavorite.setIconTintResource(R.color.grey)
            }
        }
    }

    private fun initViews() {
        val word = vmodel.findWordByID(wordID)
        binding.textViewWord.text = word.word
        binding.textViewMeaning.text = word.meaning
        binding.textViewSynonyms.text = word.synonyms
        binding.textViewExample.text = word.example
        if (word.isFavorite){
            binding.buttonFavorite.setIconTintResource(R.color.yellow)
        }
    }

    fun setWebviewSetting() {
        val settings: WebSettings = webView.getSettings()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }
        }
        webView.setVerticalScrollBarEnabled(true)
    }
}