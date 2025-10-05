package com.example.espncito.ui.viewByTeam

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.espncito.R
import com.example.espncito.databinding.ActivityViewByTeamBinding
import com.example.espncito.model.Team
import com.example.espncito.viewmodel.ViewByTeamViewModel

class ViewByTeamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewByTeamBinding
    private lateinit var viewModel: ViewByTeamViewModel

    private var teamId: Int = 0
    private var sport: String = ""
    private var league: String = ""
    private var teamName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewByTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()

        viewModel = ViewModelProvider(this).get(ViewByTeamViewModel::class.java)

        setupUI()
        observeViewModel()

        viewModel.fetchTeam(sport, league, teamId)
    }

    private fun getIntentData() {
        teamId = intent.getIntExtra("TEAM_ID", 0)
        sport = intent.getStringExtra("SPORT") ?: ""
        league = intent.getStringExtra("LEAGUE") ?: ""
        binding.textTeamName.text = teamName
    }

    private fun setupUI() {
        // Setup action bar with team name
        supportActionBar?.title = teamName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeViewModel() {
        viewModel.team.observe(this) { team ->
            team?.let {
                // MÓDULO 1: Información Básica
                binding.textTeamName.text = team.displayName ?: teamName
                binding.textLocation.text = "Ubicación: ${team.location ?: "No disponible"}"
                binding.textLeague.text = "Liga: ${team.defaultLeague?.name ?: team.leagueAbbrev ?: "No disponible"}"
                loadTeamLogo(it) }
        }

        viewModel.teamName.observe(this) { name ->
            binding.textTeamName.text = name
            supportActionBar?.title = name
        }



        viewModel.statDisplay.observe(this) { stats ->
            binding.textGamesPlayed.text = "Partidos Jugados: ${stats.gamesPlayed}"
            binding.textWins.text = "Victorias: ${stats.wins}"
            binding.textLosses.text = "Derrotas: ${stats.losses}"
            binding.textTies.text = "Empates: ${stats.ties}"
            binding.textPoints.text = "Puntos: ${stats.points}"
            binding.textGoalsFor.text = "Goles a favor: ${stats.pointsFor}"
            binding.textGoalsAgainst.text = "Goles en contra: ${stats.pointsAgainst}"
            binding.textPosition.text = "Posición en la liga: ${stats.rank}°"
        }

        viewModel.nextMatchDisplay.observe(this) { match ->
            binding.textNextMatch.text = match.name
            binding.textMatchDate.text = match.formattedDate
            binding.textMatchVenue.text = match.venue
        }

        viewModel.error.observe(this) { errorMsg ->
            showErrorState(errorMsg)
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()

        }
    }

    private fun loadTeamLogo(team: Team) {
        val logoUrl = team.logos?.firstOrNull()?.href
        if (!logoUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(logoUrl)
                .placeholder(R.drawable.ic_team_placeholder)
                .error(R.drawable.ic_error_placeholder)
                .centerInside()
                .into(binding.teamLogo)
        } else {
            binding.teamLogo.setImageResource(R.drawable.ic_team_placeholder)
        }
    }

    private fun showErrorState(message: String) {
        binding.textLocation.text = "Error: $message"
        binding.textLeague.text = "Por favor, intenta más tarde"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}