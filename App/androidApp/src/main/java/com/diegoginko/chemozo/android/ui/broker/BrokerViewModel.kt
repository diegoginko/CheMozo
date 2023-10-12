package com.diegoginko.chemozo.android.ui.broker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegoginko.chemozo.android.entidades.Dispositivo
import com.diegoginko.chemozo.android.repository.DispositivoRepository
import com.diegoginko.chemozo.android.utilidades.Multicast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mqtt.broker.Broker
import mqtt.broker.interfaces.PacketInterceptor
import mqtt.packets.MQTTPacket
import mqtt.packets.Qos
import mqtt.packets.mqtt.MQTTConnect
import mqtt.packets.mqtt.MQTTPublish
import java.nio.charset.StandardCharsets
import java.util.GregorianCalendar

@ExperimentalUnsignedTypes
class BrokerViewModel(
    private val dispositivoRepository: DispositivoRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val listadoDispositivosMutableHandler = MutableLiveData<List<Dispositivo>>()
    val toastMessage = MutableLiveData<String>()

    lateinit var broker : Broker

    init {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                //Configuracion de broker
                broker = Broker(packetInterceptor = object : PacketInterceptor {
                    override fun packetReceived(clientId: String, username: String?, password: UByteArray?, packet: MQTTPacket) {
                        when (packet) {
                            is MQTTConnect -> {
                                //toastMessage.postValue(packet.protocolName)
                                if(clientId.contains("CheMozo-")){
                                    //Creo el dispositivo si no existe, si no actualizo el estado a activo
                                    onGetConexion(clientId)
                                }

                            }
                            is MQTTPublish -> {
//                                toastMessage.postValue("${packet.topicName} - ${packet.payload?.let { String(it.toByteArray(), StandardCharsets.UTF_8) }}")
                                //Recibo mensaje y acciono segun corresponda
                                if(packet.payload != null){
                                    procesarMensaje(clientId,packet.topicName, packet.payload!!)
                                }

                            }
                        }
                    }
                })
                broker.listen()

            }
        }

        getDispositivos()
    }

    fun getDispositivos(){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val dispositivos = dispositivoRepository.getAll()
                listadoDispositivosMutableHandler.postValue(dispositivos)
            }
        }
    }

    fun onSendMulticast(){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    //Envia la ip al multicast
                    val envioIp = Multicast().postIpMulticast()
                    if(envioIp.first){
                        toastMessage.postValue("Se envio la ip ")
                    }
                }catch (e:Exception){
                    toastMessage.postValue(e.message)
                }
            }
        }
    }

    fun onGetMulticast(){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val ip = Multicast().getIpMulticast()
                    toastMessage.postValue(ip)
                }catch (e: Exception){
                    toastMessage.postValue(e.message)
                }
            }
        }
    }

    fun onGetConexion(nombreDispositivo:String){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val dispositivo = dispositivoRepository.getByNombre(nombreDispositivo)
                if(dispositivo != null){
                    dispositivo.activo = true
                    dispositivo.ultimaConexion = GregorianCalendar()
                    dispositivoRepository.update(dispositivo)
                }else{
                    dispositivoRepository.insert(Dispositivo(null, nombreDispositivo, "", true, GregorianCalendar()))
                }

                //Actualizo listado
                getDispositivos()
            }
        }
    }

    fun updateAlias(nombre:String,alias:String){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val dispositivo = dispositivoRepository.getByNombre(nombre)
                if(dispositivo != null){
                    dispositivo.alias = alias
                    dispositivoRepository.update(dispositivo)
                }else{
                    toastMessage.postValue("No se encontro el dispositivo a actualizar")
                }

                //Actualizo listado
                getDispositivos()
            }
        }
    }

    fun procesarMensaje(nombreDispositivo:String,tema:String,mensajePlano:UByteArray){
        try{
            when{
                tema.contains("mesa/accion/")->{
                    uiScope.launch {
                        withContext(Dispatchers.IO) {
                            val dispositivo = dispositivoRepository.getByNombre(nombreDispositivo)
                            val nombreEnviar = if(dispositivo!=null){
                                if (dispositivo.alias.isNotEmpty()){
                                    dispositivo.alias
                                }else{
                                    dispositivo.nombre
                                }
                            }else{
                                nombreDispositivo
                            }
                            when(String(mensajePlano.toByteArray(), StandardCharsets.UTF_8)){
                                "Hola!!"->{
                                    toastMessage.postValue("$nombreEnviar se conectó")
                                }
                                "Uno"->{
                                    val enviado = broker.publish(false,"mesa/mensaje", Qos.AT_LEAST_ONCE,null, "$nombreEnviar Solicita atencion".encodeToByteArray().toUByteArray())
                                    if(!enviado){
                                        toastMessage.postValue("$nombreEnviar Solicita atencion")
                                    }
                                }
                                "Dos"->{
                                    val enviado = broker.publish(false,"mesa/mensaje", Qos.AT_LEAST_ONCE,null, "$nombreEnviar Pide cuenta".encodeToByteArray().toUByteArray())
                                    if(!enviado){
                                        toastMessage.postValue("$nombreEnviar Pide cuenta")
                                    }
                                }
                                else->{
                                    toastMessage.postValue("${tema} - ${String(mensajePlano.toByteArray(), StandardCharsets.UTF_8)}")
                                }
                            }
                        }
                    }

                }
                tema.contains("mesa/keepAlive/")->{

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