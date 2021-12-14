package psp.proyectos.clinicanilo.db.daos

import androidx.room.*
import psp.proyectos.clinicanilo.db.entity.PacientesUsuario
import psp.proyectos.clinicanilo.db.entity.Paciente
import psp.proyectos.clinicanilo.db.entity.Usuario

@Dao
interface PacienteDAO {

    /**
     * Usuarios
     */
    @Query("SELECT COUNT(*) FROM usuario ;")
    fun countUsers(): Int

    @Query("SELECT * FROM usuario WHERE uid = :uid ;")
    fun getUserById(uid: String?): Usuario

    @Insert
    fun insertUser(vararg usuario: Usuario)

    /**
     * Pacientes
     */
    @Query("SELECT COUNT(*) FROM paciente;")
    fun countPacientes(): Int

    @Query("SELECT * FROM paciente;")
    fun getPaciente():MutableList<Paciente>

    @Query("SELECT * FROM paciente WHERE idPac = :id")
    fun getPacienteById(id: Int): Paciente

    @Insert
    fun insertPaciente(vararg paciente: Paciente)
    //vararg es para poder insertar más de un usuario a la vez

    @Update
    fun editPaciente(vararg paciente: Paciente)

    @Delete
    fun removePaciente(vararg paciente: Paciente)

    /**
     * Relación Usuario y Pacientes
     */
    @Query("SELECT * FROM usuario INNER JOIN paciente ON usuario.uid = paciente.uid WHERE usuario.uid = :id")
    fun getPacientesUsuario(id:Int):MutableList<Paciente>
}
