package RecyclerViewHelpers

import andre.molina.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import modelo.tbMecanicos

class Adaptador(var Datos: List<tbMecanicos>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Unir el RecyclerView con la card
        val vista =  LayoutInflater.from(parent.context).inflate(R.layout.item_cards, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Datos[position]
        holder.txtNombreMecanico.text = item.Nombre_mecanico
    }
}