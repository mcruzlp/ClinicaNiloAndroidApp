package psp.proyectos.clinicanilo.db.daos

import androidx.room.*
import psp.proyectos.clinicanilo.db.entity.Usuario

@Dao
interface UsuarioDAO {
    @Query("SELECT * FROM usuario WHERE UID = :uid")
    fun obtenerUsuario(uid: String): Usuario

    @Insert
    fun insertarUsuario(vararg usuario: Usuario)
    //vararg es para poder insertar más de un usuario a la vez

    @Update
    fun editarUsuario(vararg usuario: Usuario)

    @Delete
    fun borrarUsuario(usuario: Usuario)
}