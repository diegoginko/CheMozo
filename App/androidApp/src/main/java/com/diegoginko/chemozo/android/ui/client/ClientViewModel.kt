package com.diegoginko.chemozo.android.ui.client

import MQTTClient
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mqtt.Subscription
import mqtt.packets.Qos
import mqtt.packets.mqttv5.SubscriptionOptions

@OptIn(ExperimentalUnsignedTypes::class)
class ClientViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                //Configuracion de Client
                val client = MQTTClient(
                    5,
                    "test.mosquitto.org",
                    1883,
                    null
                ) {
                    println(it.payload?.toByteArray()?.decodeToString())
                }
                client.subscribe(listOf(Subscription("/randomTopic", SubscriptionOptions(Qos.EXACTLY_ONCE))))
                client.publish(false, Qos.EXACTLY_ONCE, "/randomTopic", "hello".encodeToByteArray().toUByteArray())
                client.run() // Blocking method, use step() if you don't want to block the thread

            }
        }
    }
}