package com.diegoginko.chemozo.android.ui.broker.adapter

import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.diegoginko.chemozo.android.databinding.ItemDispositivoBinding
import com.diegoginko.chemozo.android.entidades.Dispositivo

class DispositivosViewHolder(
    private val binding: ItemDispositivoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dispositivo: Dispositivo, listener: AdapterView.OnItemLongClickListener){
        binding.tvNombre.text = dispositivo.alias.ifEmpty {
            dispositivo.nombre
        }
        binding.clBackgroundDispositivo.setOnLongClickListener { v ->
            listener.onItemLongClick(v?.parent as? AdapterView<*>?, v, adapterPosition, itemId)
            true
        }
    }
}