package com.diegoginko.chemozo.android

import MQTTClient
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.diegoginko.chemozo.Greeting
import mqtt.Subscription
import mqtt.packets.Qos
import mqtt.packets.mqttv5.SubscriptionOptions

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalUnsignedTypes::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }

        //Configuro recepcion de paquetes
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

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
