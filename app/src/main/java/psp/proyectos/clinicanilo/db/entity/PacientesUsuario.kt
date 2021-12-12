package psp.proyectos.clinicanilo.db.entity

import androidx.room.Entity

@Entity (primaryKeys = ["uid","idPac"])
data class PacientesUsuario(val uid: Int, val idPac: Int)
