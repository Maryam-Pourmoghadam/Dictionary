package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //splash
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val imageView=findViewById<ImageView>(R.id.splash_img)
        val welcomeTv=findViewById<TextView>(R.id.textView_welcome)
        val frg_container=findViewById<View>(R.id.nav_host_fragment)
        imageView.alpha=0f
        welcomeTv.alpha=0f
        imageView.animate().setDuration(1000).alpha(1f)
        welcomeTv.animate().setDuration(1500).alpha(1f).setStartDelay(500L).withEndAction{
            frg_container.visibility=View.VISIBLE
            imageView.visibility=View.GONE
            supportActionBar?.show()
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

        }
    }
}