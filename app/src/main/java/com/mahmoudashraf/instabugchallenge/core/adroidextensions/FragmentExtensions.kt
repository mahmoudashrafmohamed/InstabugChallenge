package com.mahmoudashraf.instabugchallenge.core.adroidextensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this) { it?.let(observer) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, @IdRes frame: Int) {
    supportFragmentManager
        .beginTransaction()
        .replace(frame, fragment, fragment::class.java.simpleName)
        .commit()
}