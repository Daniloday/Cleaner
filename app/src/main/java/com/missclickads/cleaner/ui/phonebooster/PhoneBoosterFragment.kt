package com.missclickads.cleaner.ui.phonebooster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.missclickads.cleaner.R

class PhoneBoosterFragment : Fragment() {

    private lateinit var phoneBoosterViewModel: PhoneBoosterViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        phoneBoosterViewModel =
                ViewModelProvider(this).get(PhoneBoosterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_phone_booster, container, false)
        val textView: TextView = root.findViewById(R.id.text_phone_booster)
        phoneBoosterViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}