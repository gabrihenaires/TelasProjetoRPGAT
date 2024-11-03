package Raças

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personagem")
data class Personagem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nomePersonagem: String,
    val racaSelecionada: String,
    val descricao: String,
    val habilidadesFinais: String
)