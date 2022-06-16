package com.mahmoudashraf.instabugchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mahmoudashraf.core.androidextensions.replaceFragment
import com.mahmoudashraf.instabugchallenge.words.fragments.WordsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState==null)
          replaceFragment(WordsFragment(),R.id.app_screens_container)
    }
}

