package andre.molina

import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tbMecanicos
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1 Mandar a llamar a todos los elementos

        val txtNombre = findViewById<TextView>(R.id.txtNombreMecanico)
        val txtEdad = findViewById<TextView>(R.id.txtEdadMecanico)
        val txtPeso = findViewById<TextView>(R.id.txtPeso)
        val txtCorreo = findViewById<TextView>(R.id.txtCorreoMecanico)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val rcvMecanicos = findViewById<RecyclerView>(R.id.rcvMecanicos)


        //Agrego un layout  al RecyclerView
        rcvMecanicos.layoutManager = LinearLayoutManager(this)

        ////////////////TODO mostrar datos

        fun obtenerMecanicos(): List<tbMecanicos> {
            //1- Creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Creo un Statement
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM tbMecanico")!!

            val listaMecanicos = mutableListOf<tbMecanicos>()

            while (resultSet.next()) {
                val UUID = resultSet.getString("UUID")
                val nombreMecanico = resultSet.getString("Nombre_mecanico")
                val edadMecanico = resultSet.getInt("Edad_mecanico")
                val pesoMecanico = resultSet.getInt("Peso_mecanico")
                val correoMecanico = resultSet.getString("Correo_mecanico")

                val valoresJuntos =
                    tbMecanicos(UUID, nombreMecanico, edadMecanico, pesoMecanico, correoMecanico)
                listaMecanicos.add(valoresJuntos)
            }
            return listaMecanicos

        }
        //Asignarle el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val mecanicosDB = obtenerMecanicos()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(mecanicosDB)
                rcvMecanicos.adapter = adapter
            }
        }


        //2 - Programar el boton de agregar
        btnGuardar.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {

                //!- Crear un objeto de clase conexion

                val objConexion = ClaseConexion().cadenaConexion()

                //2- Crear uan variable que contenga un PrepareStatement
                val addMecanico = objConexion?.prepareStatement("insert into tbMecanico (UUID_Mecanico, Nombre_Mecanico, Edad_Mecanico, Peso_Mecanico, Correo_Mecanico) values(?,?,?,?,?)")!!
                addMecanico.setString(1, UUID.randomUUID().toString())
                addMecanico.setString(2, txtNombre.text.toString())
                addMecanico.setInt(3, txtEdad.text.toString().toInt())
                addMecanico.setInt(4, txtPeso.text.toString().toInt())
                addMecanico.setString(5, txtCorreo.text.toString())
                addMecanico.executeUpdate()


            }
        }

    }
}