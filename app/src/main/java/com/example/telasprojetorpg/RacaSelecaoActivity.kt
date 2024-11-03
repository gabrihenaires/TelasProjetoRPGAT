package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.telasprojetorpg.R
import Raças.*

class RacaSelecaoActivity : AppCompatActivity() {

    private lateinit var habilidadesTextView: TextView
    private lateinit var spinnerRaca: Spinner
    private lateinit var buttonNext: Button
    private var habilidadesFinais: Map<String, Int> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raca_selecao)

        habilidadesTextView = findViewById(R.id.textViewHabilidades)
        spinnerRaca = findViewById(R.id.spinnerRaca)
        buttonNext = findViewById(R.id.buttonNext)

        val racas = listOf(
            "Humano",
            "Alto Elfo",
            "Anão",
            "Anão Colina",
            "Anão Montanha",
            "Draconato",
            "Drow",
            "Elfo",
            "Elfo Floresta",
            "Gnomo",
            "Gnomo Floresta",
            "Gnomo Rochas",
            "Halfing",
            "Halfing Pé Leves",
            "Halfing Robusto",
            "Meio Elfo",
            "Meio Orc",
            "Tiefling"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, racas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRaca.adapter = adapter

        spinnerRaca.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                val racaSelecionada = parent.getItemAtPosition(position).toString()
                habilidadesFinais = exibirHabilidadesComBonus(racaSelecionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                habilidadesTextView.text = ""
            }
        }

        buttonNext.setOnClickListener {
            val intent = Intent(this, DistribuicaoHabilidadesActivity::class.java).apply {
                putExtra("nome", intent.getStringExtra("nome"))
                putExtra("descricao", intent.getStringExtra("descricao"))
                putExtra("raca", spinnerRaca.selectedItem.toString())
            }
            startActivity(intent)
        }
    }

    private fun exibirHabilidadesComBonus(racaSelecionada: String): Map<String, Int> {
        val raca = when (racaSelecionada) {
            "Humano" -> humano()
            "Alto Elfo" -> altoElfo()
            "Anão" -> anao()
            "Anão Colina" -> anaoColina()
            "Anão Montanha" -> anaoMontanha()
            "Draconato" -> draconato()
            "Drow" -> drow()
            "Elfo" -> elfo()
            "Elfo Floresta" -> elfoFloresta()
            "Gnomo" -> gnomo()
            "Gnomo Floresta" -> gnomoFloresta()
            "Gnomo Rochas" -> gnomoRochas()
            "Halfing" -> halfing()
            "Halfing Pé Leves" -> halfingPesLeves()
            "Halfing Robusto" -> halfingRobusto()
            "Meio Elfo" -> meioElfo()
            "Meio Orc" -> meioOrc()
            "Tiefling" -> tiefling()
            else -> throw IllegalArgumentException("Raça desconhecida")
        }

        val bonusHabilidades = raca.bonusHabilidades

        habilidadesTextView.text = bonusHabilidades.entries.joinToString("\n") { "${it.key}: ${it.value}" }
        return bonusHabilidades
    }
}
