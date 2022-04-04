package com.example.demoapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapp.adapter.UserAdapter
import com.example.demoapp.databinding.ActivityMainBinding
import com.example.demoapp.viewmodel.DemoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val demoViewModel: DemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        demoViewModel.fetchDemo()
        onObserver()
    }

    private fun onObserver() {
        demoViewModel.userList.observe(this) {
            val adapter = UserAdapter(this, it)
            binding.adapter = adapter
        }
    }
}