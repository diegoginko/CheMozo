package com.diegoginko.chemozo.android.ui.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.diegoginko.chemozo.android.R
import com.diegoginko.chemozo.android.databinding.ActivityClientBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClientActivity : AppCompatActivity() {
    private val model : ClientViewModel by viewModel()
    private lateinit var binding: ActivityClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_client)
        binding.clientViewModel = model
    }
}