package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.example.telasprojetorpg.R

class InformacoesPersonagemActivity : AppCompatActivity() {

    private lateinit var nome: String
    private lateinit var descricao: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_personagem)

        val editTextNome = findViewById<EditText>(R.id.editTextNome)
        val editTextDescricao = findViewById<EditText>(R.id.editTextDescricao)
        val buttonNext = findViewById<Button>(R.id.buttonNext)

        buttonNext.setOnClickListener {
            nome = editTextNome.text.toString()
            descricao = editTextDescricao.text.toString()

            if (nome.isNotBlank() && descricao.isNotBlank()) {
                val intent = Intent(this, RacaSelecaoActivity::class.java).apply {
                    putExtra("nome", nome)
                    putExtra("descricao", descricao)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

