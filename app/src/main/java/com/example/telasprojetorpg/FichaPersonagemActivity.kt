package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import Raças.*
import com.example.telasprojetorpg.DatabaseBuilder
import com.example.telasprojetorpg.PersonagemDao
import com.example.telasprojetorpg.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FichaPersonagemActivity : AppCompatActivity() {
    private lateinit var nome: String
    private lateinit var descricao: String
    private lateinit var raca: String
    private lateinit var habilidadesFinais: Map<String, Int>
    private var pontosVida: Int = 0
    private lateinit var racaSelecionada: Raca
    private lateinit var personagemDao: PersonagemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_personagem)

        val db = DatabaseBuilder.getDatabase(this)
        personagemDao = db.personagemDao()

        nome = intent.getStringExtra("nome") ?: "Sem Nome"
        descricao = intent.getStringExtra("descricao") ?: "Sem Descrição"
        raca = intent.getStringExtra("raca") ?: "Sem Raça"
        habilidadesFinais = intent.getSerializableExtra("habilidades") as? Map<String, Int> ?: emptyMap()

        racaSelecionada = when (raca) {
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

        habilidadesFinais = habilidadesFinais.mapValues { (habilidade, valor) ->
            val bonusHabilidade = racaSelecionada.bonusHabilidades[habilidade] ?: 0
            val habilidadeFinal = valor + bonusHabilidade
            val modificador = ModificadoresHabilidade.getModificador(habilidadeFinal)
            habilidadeFinal + modificador
        }

        calcularPontosVida()

        exibirFicha()

        val buttonSalvar = findViewById<Button>(R.id.buttonSalvar)
        val buttonAtualizar = findViewById<Button>(R.id.buttonAtualizar)
        val buttonDeletar = findViewById<Button>(R.id.buttonDeletar)

        val novoPersonagem = Personagem(
            nomePersonagem = nome,
            racaSelecionada = raca,
            descricao = descricao,
            habilidadesFinais = habilidadesFinais.toString()
        )

        buttonSalvar.setOnClickListener {
            lifecycleScope.launch {
                salvarPersonagem(novoPersonagem)
            }
        }

        buttonAtualizar.setOnClickListener {
            lifecycleScope.launch {
                atualizarPersonagem(novoPersonagem.copy(descricao = "Nova Descrição"))
            }
        }

        buttonDeletar.setOnClickListener {
            lifecycleScope.launch {
                deletarPersonagem(novoPersonagem)
            }
        }
    }

    private fun calcularPontosVida() {
        Log.d("FichaPersonagem", "Método calcularPontosVida chamado.")
        val constituicaoBase = habilidadesFinais["Constituição"] ?: 0
        val bonusConstituicao = racaSelecionada.bonusHabilidades["Constituição"] ?: 0
        val constituicaoFinal = constituicaoBase + bonusConstituicao
        val modificadorConstituicao = ModificadoresHabilidade.getModificador(constituicaoFinal)
        pontosVida = 10 + modificadorConstituicao - 1
        Log.d("FichaPersonagem", "Pontos de Vida: $pontosVida")
    }

    private fun exibirFicha() {
        val textViewFicha = findViewById<TextView>(R.id.textViewFicha)
        val habilidadesText = habilidadesFinais.entries.joinToString("\n") { "${it.key}: ${it.value}" }
        val fichaCompleta = """
Nome: $nome
Descrição: $descricao
Raça: $raca

Habilidades:
$habilidadesText

Pontos de Vida: $pontosVida
    """.trimIndent()
        textViewFicha.text = fichaCompleta
    }


    private suspend fun salvarPersonagem(personagem: Personagem) {
        withContext(Dispatchers.IO) {
            personagemDao.insert(personagem)
            Log.d("FichaPersonagem", "Personagem salvo no banco de dados")
        }
    }

    private suspend fun atualizarPersonagem(personagem: Personagem) {
        withContext(Dispatchers.IO) {
            personagemDao.update(personagem)
            Log.d("FichaPersonagem", "Personagem atualizado no banco de dados")
        }
    }

    private suspend fun deletarPersonagem(personagem: Personagem) {
        withContext(Dispatchers.IO) {
            val id = personagemDao.findPersonagemByName(personagem.nomePersonagem)?.id
            if (id != null) {
                personagemDao.delete(personagem.copy(id = id))
                Log.d("FichaPersonagem", "Personagem deletado do banco de dados")
            } else {
                Log.d("FichaPersonagem", "Personagem não encontrado no banco de dados")
            }
        }
    }
}
