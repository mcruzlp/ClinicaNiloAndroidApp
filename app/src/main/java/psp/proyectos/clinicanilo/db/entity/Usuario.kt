package psp.proyectos.clinicanilo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Usuario(@PrimaryKey val uid: Int,
                    @ColumnInfo(name="email") val email: String,
                    @ColumnInfo(name="nombre") val nombre: String,
                    @ColumnInfo(name="apellido") val apellido: String,
                    //@ColumnInfo(name="foto") val foto: String?
                    ): Serializable
