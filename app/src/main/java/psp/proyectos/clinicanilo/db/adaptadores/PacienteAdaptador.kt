package psp.proyectos.clinicanilo.db.adaptadores

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import psp.proyectos.clinicanilo.R
import psp.proyectos.clinicanilo.databinding.ListaPacienteItemBinding
import psp.proyectos.clinicanilo.db.entity.Paciente

class PacienteAdaptador(var asignacion: MutableList<Paciente>): RecyclerView.Adapter<PacienteAdaptador.PacienteContenedor>() {

    var pulsacionSencilla: (Paciente) -> Unit = {}
        set(value) {
            field = value
        }

    var pulsacionLarga: (MenuItem, Paciente) -> Boolean = { menuItem, paciente -> false }
        set(value) {
            field = value
        }

    override fun getItemCount(): Int = asignacion.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteContenedor {
        val inflador = LayoutInflater.from(parent.context)
        val binding = ListaPacienteItemBinding.inflate(inflador, parent, false)

        return PacienteContenedor(binding)
    }

    /**
     * Asocia el contenedor al elemento de la colección de datos que se le pasa en la asignación
     */
    override fun onBindViewHolder(holder: PacienteContenedor, position: Int) {
        holder.bindPaciente(asignacion[position])
    }

    inner class PacienteContenedor(val binding: ListaPacienteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindPaciente(paciente: Paciente) {

            //muestra info paciente
            binding.pacienteNombre.text = paciente.nombre
            binding.pacienteEmail.text = paciente.email
            binding.pacienteTlfn.text = paciente.tlfn
            Glide.with(binding.root).load(paciente.avatar).into(binding.pacienteAvatar)

            //comportamiento reactivo a pulsación corta
            binding.root.setOnClickListener { pulsacionSencilla(paciente) }

            //comportamiento reactivo a pulsación larga
            binding.root.setOnLongClickListener {
                val pop = PopupMenu(binding.root.context, binding.pacienteNombre)
                pop.inflate(R.menu.item_menu)
                pop.setOnMenuItemClickListener { pulsacionLarga(it, paciente) }
                pop.show()

                true
            }
        }
    }

}
