package com.example.telasprojetorpg

import Raças.*
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonagemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(personagem: Personagem)

    @Update
    fun update(personagem: Personagem)

    @Delete
    fun delete(personagem: Personagem)

    @Query("SELECT * FROM personagem WHERE nomePersonagem = :nome LIMIT 1")
    fun findPersonagemByName(nome: String): Personagem?
}

