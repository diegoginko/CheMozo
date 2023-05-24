package com.diegoginko.chemozo.android.ui.broker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.diegoginko.chemozo.android.R
import com.diegoginko.chemozo.android.databinding.ActivityBrokerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrokerActivity : AppCompatActivity() {
    @OptIn(ExperimentalUnsignedTypes::class)
    private val model: BrokerViewModel by viewModel()

    private lateinit var binding : ActivityBrokerBinding


    @OptIn(ExperimentalUnsignedTypes::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_broker)
        binding.brokerViewModel = model

        setObservables()
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun setObservables(){
        model.toastMessage.observe(this){
            if(it.isNotEmpty()){
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        }
    }
}