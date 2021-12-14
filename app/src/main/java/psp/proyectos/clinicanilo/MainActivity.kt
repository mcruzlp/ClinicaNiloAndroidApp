package psp.proyectos.clinicanilo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import psp.proyectos.clinicanilo.databinding.ActivityMainBinding
import psp.proyectos.clinicanilo.db.AppDatabase
import psp.proyectos.clinicanilo.db.adaptadores.PacienteAdaptador
import psp.proyectos.clinicanilo.db.entity.Paciente

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var bd: AppDatabase
    private lateinit var adaptador: PacienteAdaptador
    private lateinit var datos: MutableList<Paciente>

    /**
     * CICLO DE VIDA
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        bd = AppDatabase.getAppDatabase(this)!!

        datos = bd.getPacienteDAO().getPaciente()

        val gestionarPulsacionSencilla: (Paciente) -> Unit = {
            val intent = Intent(this, ViewActivity::class.java)
            intent.putExtra("_id", it.idPac)
            startActivity(intent)
        }

        val gestionarPulsacionLarga: (MenuItem, Paciente) -> Boolean = { item, paciente ->
            when(item.itemId) {
                R.id.mnuEditar -> {
                    val intent = Intent(this, CreateActivity::class.java)
                    intent.putExtra("_paciente",paciente.uid)
                    startActivity(intent)
                    true
                }
                R.id.mnuBorrar -> {
                    // eliminamos el paciente de la base de datos
                    bd.getPacienteDAO().removePaciente(paciente)

                    // buscamos el id del paciente
                    val idx = datos.indexOfFirst { it.equals(paciente) }

                    // eliminamos el paciente de la colección de datos
                    datos.removeAt(idx)

                    // notificamos el cambio al adaptador
                    adaptador.notifyItemRemoved(idx)
                }
            }
            false
        }

        // creamos el adaptador
        adaptador = PacienteAdaptador(datos).apply {
            pulsacionSencilla = gestionarPulsacionSencilla
            pulsacionLarga    = gestionarPulsacionLarga
        }

        // asociamos el adaptador y configuramos el recyclerView
        binding.rvPacientes.apply {
            adapter = adaptador
            setHasFixedSize(true)
        }
    }

    /**
     * CICLO DE VIDA
     *
     * Bloqueamos la vuelta a la actividad anterior
     */
    override fun onBackPressed() {
        //al sobrescribir el método de la clase padre, aunque se deje vacío, funciona el bloqueo
    }

    /**
     * MENÚ
     *
     * Creamos el Menú de la aplicación
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * MENÚ
     *
     * Opciones del MENÚ
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {

            R.id.mnuAddPatient -> {
                val intent = Intent(this, CreateActivity::class.java)
                startActivity(intent)
                true
            }

            /*R.id.mnuPerfil -> {
                Log.i("XXXX", "Has pulsado en la opción PERFIL")
                true
            }*/

            R.id.mnuSalir -> {
                auth.signOut()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
