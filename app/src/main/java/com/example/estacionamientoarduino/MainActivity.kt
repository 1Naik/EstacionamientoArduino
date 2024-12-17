package com.example.estacionamientoarduino

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.estacionamientoarduino.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    //Inicializar viewBinding
    private lateinit var binding: ActivityMainBinding
    //Referenciar a la database de Firebase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configurar viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Inicializar referencia a Firebase "datos/Estado"
        database = FirebaseDatabase.getInstance().getReference("datos/Estacionamiento/Estado")

        //Tomar datos en tiempo real
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Obtener el valor del estado del estacionamiento del snapshot
                val estado = snapshot.getValue(String::class.java)

                //Mostrar el valor en el textView, y si es nulo mostrar mensaje por defecto
                binding.tvEstado.text = if (estado != null){
                    "Estado: $estado"
                } else {
                    "Estado: ---"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Mostrar mensaje de error en el textView si no se puede ver el dato
                binding.tvEstado.text = "Error al obtener datos"
            }

        })
    }
}