package psp.proyectos.clinicanilo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import psp.proyectos.clinicanilo.databinding.ActivityCreateBinding
import psp.proyectos.clinicanilo.db.AppDatabase
import psp.proyectos.clinicanilo.db.entity.Paciente

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var bd: AppDatabase
    private lateinit var paciente: Paciente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras?.getSerializable("_paciente") != null){
            paciente = intent.extras?.getSerializable("_paciente") as Paciente
        }

        binding.btnSave.setOnClickListener{editarPaciente()}

    }

    fun editarPaciente() {
        var editaPaciente: Paciente = intent?.extras?.getSerializable("_paciente") as Paciente

        editaPaciente.avatar = binding.editAvatar.toString().trim()
        editaPaciente.nombre = binding.editNombre.text.toString().trim()
        editaPaciente.tlfn = binding.editTlfn.text.toString().trim()
        editaPaciente.email = binding.editEmail.text.toString().trim()

        if (editaPaciente!=null){
            val pacienteEditado = bd?.getPacienteDAO().editPaciente(editaPaciente)
            setResult(RESULT_OK)
            finish()
        } else {
            val pacienteEditado = bd?.getPacienteDAO().insertPaciente(editaPaciente)
            setResult(RESULT_OK)
            finish()
        }
    }
}