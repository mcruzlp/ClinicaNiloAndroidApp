package psp.proyectos.clinicanilo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import psp.proyectos.clinicanilo.db.daos.PacienteDAO
import psp.proyectos.clinicanilo.db.entity.Paciente
import psp.proyectos.clinicanilo.db.entity.Usuario


@Database(entities = [Usuario::class, Paciente::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getPacienteDAO(): PacienteDAO

    companion object {

        private var instancia: AppDatabase? = null

        @Synchronized
        fun getAppDatabase(context: Context): AppDatabase? {

            if (instancia == null) {
                instancia = Room.databaseBuilder(context, AppDatabase::class.java, "basededatos")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instancia
        }
    }
}