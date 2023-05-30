package com.diegoginko.chemozo.android.utilidades

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.DatagramPacket
import java.net.Inet4Address
import java.net.InetAddress
import java.net.MulticastSocket
import java.net.NetworkInterface
import java.net.SocketTimeoutException

class Multicast {

    suspend fun postIpMulticast():Pair<Boolean,String>{
        val response = withContext(Dispatchers.IO) {
            var realizado = false
            var mensaje = ""
            try {
                val ipAdress = getLocalIPAddress()
                if(!ipAdress.isNullOrEmpty()){
                    val buffer = ipAdress.encodeToByteArray()
//                    val buffer = ByteArray(1000)
                    val group: InetAddress = InetAddress.getByName("239.1.1.234")
                    val s: MulticastSocket = MulticastSocket(7234)
                    val packet = DatagramPacket(buffer, buffer.size,group,7234)

                    s.joinGroup(group)
                    s.soTimeout = 3000
                    s.send(packet)
                    s.leaveGroup(group)

                    realizado = true

//                    response = String(packet.data, 0, packet.length)
                }else{
                    mensaje = "no se pudo obtener la direccion ip"
                }

            } catch (e: SocketTimeoutException) {
//                Timber.e(e)
                mensaje = "timeout"
            } catch (e: IOException) {
//                Timber.e(e)
                mensaje = "error"
            }
            Pair(realizado,mensaje)
        }
        return response
    }

    suspend fun getIpMulticast():String{
        val response = withContext(Dispatchers.IO) {
            var response = ""
            try {
                val buffer = ByteArray(1000)
                val packet = DatagramPacket(buffer, buffer.size)
                val group: InetAddress = InetAddress.getByName("239.1.1.234")
                val s: MulticastSocket = MulticastSocket(7234)

                s.joinGroup(group)
                s.soTimeout = 3000
                s.receive(packet)
                s.leaveGroup(group)

                response = String(packet.data, 0, packet.length)

            } catch (e: SocketTimeoutException) {
//                Timber.e(e)
                response = "timeout"
            } catch (e: IOException) {
//                Timber.e(e)
                response = "error"
            }
            response
        }
        return response
    }

    fun getLocalIPAddress(): String? {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val networkInterface = en.nextElement()
                val enu = networkInterface.inetAddresses
                while (enu.hasMoreElements()) {
                    val inetAddress = enu.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }
}