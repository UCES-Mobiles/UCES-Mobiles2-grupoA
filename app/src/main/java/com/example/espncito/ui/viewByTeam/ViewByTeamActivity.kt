package com.example.espncito.ui.viewByTeam

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.espncito.R
import com.example.espncito.databinding.ActivityViewByTeamBinding
import com.example.espncito.viewmodel.ViewByTeamViewModel
import java.text.SimpleDateFormat
import java.util.Locale

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

        // Get data from intent
        getIntentData()

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ViewByTeamViewModel::class.java)

        setupUI()
        observeViewModel()

        // Fetch team data with received parameters
        viewModel.fetchTeam(sport, league, teamId)
    }

    private fun getIntentData() {
        teamId = intent.getIntExtra("TEAM_ID", 0)
        sport = intent.getStringExtra("SPORT") ?: ""
        league = intent.getStringExtra("LEAGUE") ?: ""
        teamName = intent.getStringExtra("TEAM_NAME") ?: ""

        // Set team name immediately for better UX
        binding.textTeamName.text = teamName
    }

    private fun setupUI() {
        // Setup action bar with team name
        supportActionBar?.title = teamName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeViewModel() {
        // Observe team data from ViewModel
        viewModel.team.observe(this) { team ->
            if (team != null) {
                updateTeamUI(team)
            } else {
                showErrorState("Failed to load team data")
            }
        }

        // Observe error states from ViewModel
        viewModel.error.observe(this) { errorMsg ->
            showErrorState(errorMsg)
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateTeamUI(team: com.example.espncito.model.Team) {
        // MÓDULO 1: Información Básica
        binding.textTeamName.text = team.displayName ?: teamName
        binding.textLocation.text = "Ubicación: ${team.location ?: "No disponible"}"
        binding.textLeague.text = "Liga: ${team.defaultLeague?.name ?: team.leagueAbbrev ?: "No disponible"}"

        // Load team logo from JSON
        loadTeamLogo(team)

        // MÓDULO 2: Estadísticas desde el JSON
        updateStatsModule(team)

        // MÓDULO 3: Próximo Partido desde el JSON
        updateNextMatchModule(team)
    }

    private fun loadTeamLogo(team: com.example.espncito.model.Team) {
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

    private fun updateStatsModule(team: com.example.espncito.model.Team) {
        val record = team.record?.items?.firstOrNull()

        // Get individual stats from JSON
        val gamesPlayed = getStatValue(record, "gamesPlayed")
        val wins = getStatValue(record, "wins")
        val losses = getStatValue(record, "losses")
        val ties = getStatValue(record, "ties")
        val points = getStatValue(record, "points")
        val pointsFor = getStatValue(record, "pointsFor")
        val pointsAgainst = getStatValue(record, "pointsAgainst")
        val rank = getStatValue(record, "rank")

        // Update UI with stats from JSON
        binding.textPosition.text = "Posición en la liga: ${rank ?: "N/A"}°"
        binding.textPoints.text = "Puntos: ${points ?: "N/A"}"
        binding.textGamesPlayed.text = "Partidos Jugados: ${gamesPlayed ?: "N/A"}"
        binding.textWins.text = "Victorias: ${wins ?: "N/A"}"
        binding.textLosses.text = "Derrotas: ${losses ?: "N/A"}"
        binding.textTies.text = "Empates: ${ties ?: "N/A"}"
        binding.textGoalsFor.text = "Goles a favor: ${pointsFor ?: "N/A"}"
        binding.textGoalsAgainst.text = "Goles en contra: ${pointsAgainst ?: "N/A"}"
    }

    private fun updateNextMatchModule(team: com.example.espncito.model.Team) {
        val nextEvent = team.nextEvent?.firstOrNull()

        if (nextEvent != null) {
            // Get match name from JSON
            binding.textNextMatch.text = nextEvent.name ?: "Próximo partido no disponible"

            // Format date from JSON
            val formattedDate = formatMatchDate(nextEvent.date)
            binding.textMatchDate.text = "Fecha: $formattedDate"

            // Get venue from JSON
            binding.textMatchVenue.text = "Estadio: ${nextEvent.venue?.fullName ?: "Por definir"}"
        } else {
            binding.textNextMatch.text = "No hay próximos partidos"
            binding.textMatchDate.text = "Fecha: No disponible"
            binding.textMatchVenue.text = "Estadio: No disponible"
        }
    }

    private fun getStatValue(record: com.example.espncito.model.RecordItem?, statName: String): String? {
        return record?.stats?.find { it.name == statName }?.value?.toString()
    }

    private fun formatMatchDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return "No disponible"

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d 'de' MMMM, yyyy", Locale("es", "ES"))
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            "Formato inválido"
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