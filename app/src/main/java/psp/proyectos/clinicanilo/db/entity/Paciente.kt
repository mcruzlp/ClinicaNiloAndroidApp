package psp.proyectos.clinicanilo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Paciente(
    @PrimaryKey(autoGenerate = true) var idPac: Int,
    @ColumnInfo(name = "avatar") var avatar: String,
    @ColumnInfo(name = "nombre") var nombre: String,
    //@ColumnInfo(name = "apellidos") val apellidos: String,
    //@ColumnInfo(name = "fecNac") val fecNac: String,
    @ColumnInfo(name = "tlfn") var tlfn: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "uid") var uid: Int,
):Serializable