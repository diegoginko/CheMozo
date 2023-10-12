package com.diegoginko.chemozo.android.ui.client

import android.Manifest
import android.app.NotificationManager
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
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

        setObservables()
    }

    fun setObservables(){
        model.toastMessage.observe(this){
            if(it.isNotEmpty()){
                Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
            }
        }
        model.enviarNotificacion.observe(this){
            if(it.isNotEmpty()){
                enviarNotificacion(it)
            }
        }
    }

    fun enviarNotificacion(mensajeNotificacion:String){

        var builder = NotificationCompat.Builder(this, "chemozo-channel")
            .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
            .setContentTitle("CheMozo")
            .setContentText(mensajeNotificacion)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define.
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, builder.build())
        }
    }
}