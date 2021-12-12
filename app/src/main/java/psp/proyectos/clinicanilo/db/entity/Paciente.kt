package psp.proyectos.clinicanilo.db.entity

import android.text.Editable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Paciente(
    @PrimaryKey(autoGenerate = true) val idPac: Int,
    @ColumnInfo(name = "avatar") val avatar: String,
    @ColumnInfo(name = "nombre") val nombre: String,
    //@ColumnInfo(name = "apellidos") val apellidos: String,
    //@ColumnInfo(name = "fecNac") val fecNac: String,
    @ColumnInfo(name = "tlfn") val tlfn: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "uid") val uid: Int,
)