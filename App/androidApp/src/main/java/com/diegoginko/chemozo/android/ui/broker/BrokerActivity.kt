package com.diegoginko.chemozo.android.ui.broker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diegoginko.chemozo.android.R
import com.diegoginko.chemozo.android.databinding.ActivityBrokerBinding
import com.diegoginko.chemozo.android.entidades.Dispositivo
import com.diegoginko.chemozo.android.ui.broker.adapter.DispositivosAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrokerActivity : AppCompatActivity() {
    @OptIn(ExperimentalUnsignedTypes::class)
    private val model: BrokerViewModel by viewModel()

    private lateinit var binding : ActivityBrokerBinding

    private val adapter: DispositivosAdapter by inject()


    @OptIn(ExperimentalUnsignedTypes::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_broker)
        binding.brokerViewModel = model

        initializeDispositivos()
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

    private fun initializeDispositivos(){
        val listDispositivos = binding.rvDispositivos
        listDispositivos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listDispositivos.setHasFixedSize(true)
        listDispositivos.adapter = adapter

        val dispositivos = arrayListOf<Dispositivo>()
        dispositivos.add(Dispositivo("Prueba","Mesa 8"))
        dispositivos.add(Dispositivo("Prueba","Mesa 9"))
        dispositivos.add(Dispositivo("Prueba","Mesa 14"))
        dispositivos.add(Dispositivo("Prueba","Mesa 2"))
        dispositivos.add(Dispositivo("Prueba","Mesa 5"))
        dispositivos.add(Dispositivo("Prueba","Mesa 23"))

        adapter.setComprobantes(dispositivos)
    }
}