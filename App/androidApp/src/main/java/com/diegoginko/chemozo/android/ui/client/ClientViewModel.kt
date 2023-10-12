package com.diegoginko.chemozo.android.ui.client

import MQTTClient
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mqtt.Subscription
import mqtt.packets.Qos
import mqtt.packets.mqttv5.SubscriptionOptions
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalUnsignedTypes::class)
class ClientViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    lateinit var client : MQTTClient

    val toastMessage = MutableLiveData<String>()
    val enviarNotificacion = MutableLiveData<String>()
    init {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    //Configuracion de Client
                    client = MQTTClient(
                        5,
                        "192.168.1.150",
                        1883,
                        null
                    ) {
                        if(it.payload != null){
                            procesarMensaje(it.topicName, it.payload!!)
                        }
//                    println(it.payload?.toByteArray()?.decodeToString())
                    }
                    client.subscribe(listOf(
                        Subscription("mesa/mensaje", SubscriptionOptions(Qos.AT_LEAST_ONCE))
                    ))
                    client.publish(false, Qos.EXACTLY_ONCE, "mozo/mensaje", "hola".encodeToByteArray().toUByteArray())
                    client.run() // Blocking method, use step() if you don't want to block the thread
//                client.step()
                    toastMessage.postValue("${client.running}")
                }catch (e:Exception){
                    toastMessage.postValue("${e.message}")
                }

            }
        }
    }

    fun procesarMensaje(tema:String,mensajePlano:UByteArray){
        try{
            when{
                tema.contains("mesa/mensaje")->{

                    toastMessage.postValue("${String(mensajePlano.toByteArray(), StandardCharsets.UTF_8)}")
                    enviarNotificacion.postValue("${String(mensajePlano.toByteArray(), StandardCharsets.UTF_8)}")
                }
                else->{
                    toastMessage.postValue("${tema} - ${String(mensajePlano.toByteArray(), StandardCharsets.UTF_8)}")
                }
            }
        }catch (e:Exception){
            toastMessage.postValue("Error al procesar mensaje - ${e.message}")
        }

    }
}