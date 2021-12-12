package psp.proyectos.clinicanilo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import psp.proyectos.clinicanilo.db.AppDatabase
import psp.proyectos.clinicanilo.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: Int = intent.extras?.getInt("_id") as Int

        val paciente = AppDatabase.getAppDatabase(this)?.getPacienteDAO()?.getPacienteById(id)

        paciente?.let {
            binding.apply {
                Glide.with(binding.root).load(paciente.avatar).into(infoAvatar)
                infoNombre.text = paciente.nombre
                infoEmail.text = paciente.email
                infoTlfn.text = paciente.tlfn
                btnInfoVolver.setOnClickListener { finish() }
            }
        }
    }
}