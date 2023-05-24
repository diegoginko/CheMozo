package com.diegoginko.chemozo.android.ui.broker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mqtt.broker.Broker
import mqtt.broker.interfaces.PacketInterceptor
import mqtt.packets.MQTTPacket
import mqtt.packets.mqtt.MQTTConnect
import mqtt.packets.mqtt.MQTTPublish
import java.nio.charset.StandardCharsets

@ExperimentalUnsignedTypes
class BrokerViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val toastMessage = MutableLiveData<String>()

    init {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                //Configuracion de broker
                val broker = Broker(packetInterceptor = object : PacketInterceptor {
                    override fun packetReceived(clientId: String, username: String?, password: UByteArray?, packet: MQTTPacket) {
                        when (packet) {
                            is MQTTConnect -> {
                                toastMessage.postValue(packet.protocolName)
                            }
                            is MQTTPublish -> {
                                toastMessage.postValue("${packet.topicName} - ${packet.payload?.let { String(it.toByteArray(), StandardCharsets.UTF_8) }}")
                            }
                        }
                    }
                })
                broker.listen()

            }
        }
    }



}