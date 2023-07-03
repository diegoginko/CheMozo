package com.diegoginko.chemozo.android.ui.broker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.diegoginko.chemozo.android.R
import com.diegoginko.chemozo.android.databinding.ItemDispositivoBinding
import com.diegoginko.chemozo.android.entidades.Dispositivo

class DispositivosAdapter(): RecyclerView.Adapter<DispositivosViewHolder>(), AdapterView.OnItemLongClickListener  {

    private var listaDispositivos: ArrayList<Dispositivo> = ArrayList()
    private var dispositivoSeleccionado: Dispositivo? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispositivosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDispositivoBinding.inflate(inflater, parent, false)
        return DispositivosViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listaDispositivos.size
    }

    override fun onBindViewHolder(holder: DispositivosViewHolder, position: Int) {
        holder.bind(listaDispositivos[position],this)
    }

    fun setComprobantes(dispositivos: ArrayList<Dispositivo>) {
        listaDispositivos = dispositivos
        notifyDataSetChanged()
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        view?.let { v ->
            dispositivoSeleccionado = listaDispositivos[position]
            val popup = PopupMenu(v.context, v)
            popup.setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.crear_alias_dispositivo_menu -> {
                        dispositivoSeleccionado?.let {
                            //Todo: dialog crear alias
                        }
                    }
                    else -> {
                        dispositivoSeleccionado = null
                    }
                }
                true
            }
            popup.inflate(R.menu.menu_seleccion_dispositivos)
            popup.show()
        }
        return true
    }
}