    package com.example.myapplication


    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity
    import com.example.telasprojetorpg.R

    class DistribuicaoHabilidadesActivity : AppCompatActivity() {

        private lateinit var pontosRestantesTextView: TextView
        private var pontosRestantes: Int = 27
        private var habilidades = mutableMapOf<String, Int>(
            "Força" to 8,
            "Destreza" to 8,
            "Constituição" to 8,
            "Inteligência" to 8,
            "Sabedoria" to 8,
            "Carisma" to 8
        )

        private val custoStrategy = CustoPadraoStrategy()

        private lateinit var nomePersonagem: String
        private lateinit var racaSelecionada: String
        private lateinit var descricaoPersonagem: String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_distribuicao_habilidades)

            nomePersonagem = intent.getStringExtra("nome") ?: "Sem Nome"
            racaSelecionada = intent.getStringExtra("raca") ?: "Sem Raça"
            descricaoPersonagem = intent.getStringExtra("descricao") ?: "Sem Descrição"

            pontosRestantesTextView = findViewById(R.id.pontosRestantesTextView)
            atualizarPontosRestantes()

            val textViewForca = findViewById<TextView>(R.id.textViewForca)
            val buttonForcaMais = findViewById<Button>(R.id.buttonForcaMais)
            val buttonForcaMenos = findViewById<Button>(R.id.buttonForcaMenos)

            val textViewDestreza = findViewById<TextView>(R.id.textViewDestreza)
            val buttonDestrezaMais = findViewById<Button>(R.id.buttonDestrezaMais)
            val buttonDestrezaMenos = findViewById<Button>(R.id.buttonDestrezaMenos)

            val textViewConstituicao = findViewById<TextView>(R.id.textViewConstituicao)
            val buttonConstituicaoMais = findViewById<Button>(R.id.buttonConstituicaoMais)
            val buttonConstituicaoMenos = findViewById<Button>(R.id.buttonConstituicaoMenos)

            val textViewInteligencia = findViewById<TextView>(R.id.textViewInteligencia)
            val buttonInteligenciaMais = findViewById<Button>(R.id.buttonInteligenciaMais)
            val buttonInteligenciaMenos = findViewById<Button>(R.id.buttonInteligenciaMenos)

            val textViewSabedoria = findViewById<TextView>(R.id.textViewSabedoria)
            val buttonSabedoriaMais = findViewById<Button>(R.id.buttonSabedoriaMais)
            val buttonSabedoriaMenos = findViewById<Button>(R.id.buttonSabedoriaMenos)

            val textViewCarisma = findViewById<TextView>(R.id.textViewCarisma)
            val buttonCarismaMais = findViewById<Button>(R.id.buttonCarismaMais)
            val buttonCarismaMenos = findViewById<Button>(R.id.buttonCarismaMenos)

            configurarListeners(
                buttonForcaMais, buttonForcaMenos, textViewForca, "Força"
            )
            configurarListeners(
                buttonDestrezaMais, buttonDestrezaMenos, textViewDestreza, "Destreza"
            )
            configurarListeners(
                buttonConstituicaoMais, buttonConstituicaoMenos, textViewConstituicao, "Constituição"
            )
            configurarListeners(
                buttonInteligenciaMais, buttonInteligenciaMenos, textViewInteligencia, "Inteligência"
            )
            configurarListeners(
                buttonSabedoriaMais, buttonSabedoriaMenos, textViewSabedoria, "Sabedoria"
            )
            configurarListeners(
                buttonCarismaMais, buttonCarismaMenos, textViewCarisma, "Carisma"
            )

            val buttonGerarFicha: Button = findViewById(R.id.buttonGerarFicha)
            buttonGerarFicha.setOnClickListener {
                gerarFichaPersonagem()
            }
        }

        private fun configurarListeners(
            buttonMais: Button,
            buttonMenos: Button,
            textView: TextView,
            habilidade: String
        ) {
            buttonMais.setOnClickListener {
                val valorAtual = habilidades[habilidade] ?: 8
                val valorDesejado = valorAtual + 1

                val custo = custoStrategy.calcularCusto(valorAtual, valorDesejado)
                if (pontosRestantes >= custo && valorDesejado <= 15) {
                    habilidades[habilidade] = valorDesejado
                    pontosRestantes -= custo
                    textView.text = valorDesejado.toString()
                    atualizarPontosRestantes()
                }
            }

            buttonMenos.setOnClickListener {
                val valorAtual = habilidades[habilidade] ?: 8
                val valorDesejado = valorAtual - 1

                if (valorDesejado >= 8) {
                    val custoDevolvido = custoStrategy.calcularCusto(valorDesejado, valorAtual)
                    habilidades[habilidade] = valorDesejado
                    pontosRestantes += custoDevolvido
                    textView.text = valorDesejado.toString()
                    atualizarPontosRestantes()
                }
            }
        }

        private fun atualizarPontosRestantes() {
            pontosRestantesTextView.text = "Pontos Restantes: $pontosRestantes"
        }

        private fun gerarFichaPersonagem() {
            val intent = Intent(this, FichaPersonagemActivity::class.java)
            intent.putExtra("nome", nomePersonagem)
            intent.putExtra("raca", racaSelecionada)
            intent.putExtra("descricao", descricaoPersonagem)
            intent.putExtra("habilidades", HashMap(habilidades))
            startActivity(intent)
        }
    }
