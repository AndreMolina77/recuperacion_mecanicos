package RecyclerViewHelpers

import andre.molina.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val txtNombreMecanico =  view.findViewById<TextView>(R.id.txtNombreMecanico)


}