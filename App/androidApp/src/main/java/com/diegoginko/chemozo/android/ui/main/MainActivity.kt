package com.diegoginko.chemozo.android.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.diegoginko.chemozo.android.R
import com.diegoginko.chemozo.android.databinding.ActivityMainBinding
import com.diegoginko.chemozo.android.ui.broker.BrokerActivity
import com.diegoginko.chemozo.android.ui.client.ClientActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val model : MainViewModel by viewModel()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.mainViewModel = model

        setObservers()
    }

    private fun setObservers(){
        model.brokerPressed.observe(this) {
            if (it) {
                val intent = Intent(this, BrokerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        model.clientPressed.observe(this) {
            if (it) {
                val intent = Intent(this, ClientActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}