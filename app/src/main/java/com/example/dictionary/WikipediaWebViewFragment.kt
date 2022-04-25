package com.example.dictionary

import android.os.Bundle
import android.view.*
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.dictionary.databinding.FragmentWikipediaWebViewBinding


class WikipediaWebViewFragment : Fragment() {
   lateinit var binding:FragmentWikipediaWebViewBinding
    lateinit var webView: WebView
    lateinit var link:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            link= it.getString("link").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentWikipediaWebViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView =binding.webViewWikipedia
        setWebviewSetting()
        webView.loadUrl(link)
        webView.canGoBack()
        webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && webView.canGoBack()) {
                webView.goBack()
                return@OnKeyListener true
            }
            false
        })
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