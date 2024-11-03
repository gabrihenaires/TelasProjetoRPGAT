package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.telasprojetorpg.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonIniciar = findViewById<Button>(R.id.buttonIniciar)
        buttonIniciar.setOnClickListener {
            val intent = Intent(this, InformacoesPersonagemActivity::class.java)
            startActivity(intent)
        }
    }
}

