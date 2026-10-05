package com.isengard.fruegas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.RadioGroup
import android.widget.ImageButton


class main : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val NombreHeroe = findViewById<EditText>(R.id.name)
        NombreHeroe.requestFocus()
        NombreHeroe.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {

                if (NombreHeroe.text.isEmpty()) NombreHeroe.error = "¡Tu héroe necesita un nombre!"
            }
        }
        val Raza = findViewById<Spinner>(R.id.Lista)
        Raza.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val tipounidad = parent?.getItemAtPosition(position).toString()
                Log.d("Personaje", "Ahora eres: $tipounidad")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val rgFaccion = findViewById<RadioGroup>(R.id.Grupo)
        rgFaccion.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.armadura -> Toast.makeText(
                    this,
                    "Has elegido la armadura de hierro",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.Escudo -> Toast.makeText(
                    this,
                    "Has elegido el escudo de Isengard",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val antorcha = findViewById<CheckBox>(R.id.antorcha)
        antorcha.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.d("Habilidad", "Has adquirido la antorcha")

                //Boton registro
                val btnRegistrar = findViewById<ImageButton>(R.id.botonguardar)

                // 2. Función lambda: recibe nombre, raza, facción y habilidades para el logcat

                val registrarEnLogcat: (String, String, String, List<String>) -> Unit =
                    { nombre, raza, faccion, habilidades ->
                        Log.d(
                            "Personaje",
                            "Nombre: $nombre | Raza: $raza | Facción: $faccion | Habilidades: $habilidades"
                        )
                    }

                // 3. Al pulsar leemos lo que hay elegido en el formulario
                btnRegistrar.setOnClickListener {
                    val nombre = NombreHeroe.text.toString()
                    val raza = Raza.selectedItem.toString()

                    val faccion = when (rgFaccion.checkedRadioButtonId) {
                        R.id.armadura -> "Armadura de hierro"
                        R.id.Escudo -> "Escudo de isengard"
                        else -> "Sin elegir"
                    }

                    val habilidades = mutableListOf<String>()
                    if (antorcha.isChecked) habilidades.add("Lleva antorcha")

                    // 3b. Texto explicacion cosas
                    val resumen = "Héroe: $nombre\nRaza: $raza\nFacción: $faccion\n" +
                            "Habilidades: ${
                                if (habilidades.isEmpty()) "Ninguna" else habilidades.joinToString(
                                    ", "
                                )
                            }"

                    // 3c. Lo enseñamos en toast como pide el profe
                    Toast.makeText(this, resumen, Toast.LENGTH_LONG).show()

                    // 3d. Lo registramos en Logcat llamando a la lambda (lo ve el programador)
                    registrarEnLogcat(nombre, raza, faccion, habilidades)



                }
            }
        }
    }
    //los logs de la app
    override fun onStart() {
        super.onStart()
        Log.d("FraguasIsengard", "onStart: Las fraguas se encienden")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FraguasIsengard", "onResume: Los capataces vuelven al trabajo")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FraguasIsengard", "onPause: Saruman detiene la producción temporalmente")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FraguasIsengard", "onStop: Las fraguas quedan en la sombra de Orthanc")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FraguasIsengard", "onDestroy: Las fraguas de Isengard son derribadas")
    }
}