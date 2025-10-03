package com.example.espncito.ui.viewByLeague

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.espncito.databinding.ActivityViewByLeagueBinding

class ViewByLeagueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewByLeagueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewByLeagueBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        // Configurar el RecyclerView
        binding.recyclerViewTeams.layoutManager = LinearLayoutManager(this)

        // Configurar el spinner (lo implementaremos después)
        setupSpinner()
    }

    private fun setupSpinner() {
        // Aquí configuraremos el spinner en el siguiente paso
    }
}