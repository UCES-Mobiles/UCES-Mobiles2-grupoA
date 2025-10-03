package com.example.espncito.ui.viewByTeam;

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.espncito.databinding.ActivityViewByTeamBinding
import com.example.espncito.viewmodel.ViewByTeamViewModel


class ViewByTeamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewByTeamBinding
    private lateinit var viewModel: ViewByTeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewByTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewByTeamViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        // Llamamos al ViewModel para que traiga los datos
        viewModel.fetchTeam("soccer", "usa.1", 18418)
    }

    private fun setupRecyclerView() {
        // Aquí inicializás tu RecyclerView y Adapter con ViewBinding
    }

    private fun observeViewModel() {
        viewModel.team.observe(this) { team ->
            binding.textName.text = team.name
            binding.textColor.text = team.color
            binding.textLocation.text = team.location
            binding.textLeagueName.text = binding.textLeagueName.text
        }

        viewModel.stats.observe(this) { stats ->
            // Actualizar el RecyclerView con la lista de stats
            // adapter.submitList(stats) o lo que uses
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        }
    }


}
