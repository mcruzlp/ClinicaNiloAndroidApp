package psp.proyectos.clinicanilo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import psp.proyectos.clinicanilo.databinding.ActivityLoginBinding
import psp.proyectos.clinicanilo.db.AppDatabase
import psp.proyectos.clinicanilo.db.entity.Paciente
import psp.proyectos.clinicanilo.db.entity.Usuario

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    /**
     * Registramos cómo reacciona la actividad de LOGIN a la respuesta
     * de la actividad de REGISTRO. Este registro debe realizarse SIEMPRE
     * antes de que la actividad de LOGIN se cree.
     */

    private val respuestaRegistro = registerForActivityResult(StartActivityForResult()) {
        /**
         * La lambda contiene la lógica que se ejecutará cuando la actividad
         * REGISTRO responda.
         */
        if (it.resultCode == RESULT_OK)
            it.data?.extras?.getString("_response")?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }

        else
            Snackbar.make(binding.root, R.string.err_registro, Snackbar.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        iniciarBaseDatos()

        if(auth.currentUser != null) goMain(auth.uid.toString())

        binding.btnLogin.setOnClickListener {login(it)}
        binding.btnRegistro.setOnClickListener {registro(it)}
    }

    fun login(view: View) {
        val email: String = binding.email.text.toString().trim()
        val pass: String = binding.password.text.toString().trim()

        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this) {
            if (it.isSuccessful) goMain(auth.uid.toString())
            else {
                Snackbar.make(view, R.string.err_login, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    fun registro (view: View) {
        val intencion = Intent(this, RegisterActivity::class.java)
        respuestaRegistro.launch(intencion)
    }

    private fun goMain(uid: String){
        val db = AppDatabase.getAppDatabase(this)
        val usuario = db?.getPacienteDAO()?.getUserById(uid)

        val intencion = Intent(this, MainActivity::class.java)

        intencion.putExtra("_usuario", usuario)
        startActivity(intencion)
    }

    private fun iniciarBaseDatos()
    {
        val dao = AppDatabase.getAppDatabase(this)?.getPacienteDAO()

        if (dao?.countUsers() == 0) {
            dao.insertUser(Usuario(0, "admin@nilo.com", "Cruz", "Lopez",))
        }

        if(dao?.countPacientes() == 0)
        {
            dao.insertPaciente(
                Paciente(0, "https://robohash.org/facereaperiamquaerat.png?size=91x91&set=set1","Trudi Lazell", "772258414", "tlazell0@thetimes.co.uk", 0),
                Paciente(0, "https://robohash.org/dictaatperferendis.png?size=91x91&set=set1","Nickie Hoble", "894693974", "nhoble1@flickr.com", 0),
                Paciente(0, "https://robohash.org/animidebitisnostrum.png?size=91x91&set=set1","Saudra Nickoles", "735201723", "snickoles2@virginia.edu", 0),
                Paciente(0, "https://robohash.org/recusandaelaboriosamid.png?size=91x91&set=set1","Bogart Lucian", "669982914", "blucian3@google.com", 0),
                Paciente(0, "https://robohash.org/fugitetsit.png?size=91x91&set=set1","Gizela De Domenicis", "795496599", "gde4@ucoz.com", 0),
                Paciente(0, "https://robohash.org/voluptatemitaqueratione.png?size=91x91&set=set1","Waylon Patrie", "815152111", "wpatrie5@arizona.edu", 0),
                Paciente(0, "https://robohash.org/voluptasquiveniam.png?size=91x91&set=set1","Mattie Clapham", "680675948", "mclapham6@google.pl", 0),
                Paciente(0, "https://robohash.org/dolorconsequaturofficiis.png?size=91x91&set=set1","Jemima Fessler", "616790616", "jfessler7@bandcamp.com", 0),
            )
        }
    }
}

