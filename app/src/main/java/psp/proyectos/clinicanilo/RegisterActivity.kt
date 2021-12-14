package psp.proyectos.clinicanilo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import psp.proyectos.clinicanilo.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegistrar.setOnClickListener { registrar(it) }
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun registrar(view: View) {
        val email: String = binding.email.text.toString().trim()
        val pass : String = binding.password.text.toString().trim()

        if ((email!="") && (pass!="")) Snackbar.make(view, R.string.err_email_pass, Snackbar.LENGTH_LONG).show()

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Snackbar.make(view, R.string.msg_registro, Snackbar.LENGTH_LONG).show()
                val intencion = Intent(this, LoginActivity::class.java)
                startActivity(intencion)
            } else {
                Snackbar.make(view, R.string.err_registro, Snackbar.LENGTH_LONG).show()
            }
        }
    }

}
