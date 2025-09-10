package com.example.projetoroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projetoroom.dao.UserDao
import com.example.projetoroom.model.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    companion object {
        // Volatile garante que mudanças sejam visíveis para todas as threads
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            // Se já existe, retorna; senão cria
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user-database"
                )
                    // Aqui o banco é criado em segundo plano por padrão
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }



}
