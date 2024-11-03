package com.example.telasprojetorpg


import Raças.*
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Personagem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personagemDao(): PersonagemDao
}
