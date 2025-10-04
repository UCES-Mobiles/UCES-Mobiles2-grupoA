package com.example.espncito.ui.viewByLeague

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.espncito.R
import com.example.espncito.databinding.FragmentViewByLeagueBinding
import com.example.espncito.model.LeagueItem
import com.example.espncito.model.TeamInfo
import com.example.espncito.network.viewByLeague.ViewByLeagueRetrofitClient
import com.example.espncito.ui.viewByTeam.ViewByTeamActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewByLeagueFragment : Fragment() {

    private var _binding: FragmentViewByLeagueBinding? = null
    private val binding get() = _binding!!

    private lateinit var teamsAdapter: TeamsAdapter

    // List of available leagues
    private val availableLeagues = listOf(
        LeagueItem("Football (NFL)", "football", "nfl"),
        LeagueItem("Baseball (MLB)", "baseball", "mlb"),
        LeagueItem("Hockey (NHL)", "hockey", "nhl"),
        LeagueItem("Basketball (NBA)", "basketball", "nba"),
        LeagueItem("Soccer - Argentina (LFP)", "soccer", "arg.1"),
        LeagueItem("Soccer - England (Premier)", "soccer", "eng.1"),
        LeagueItem("Soccer - Italy (Serie A)", "soccer", "ita.1"),
        LeagueItem("Soccer - USA (MLS)", "soccer", "usa.1"),
        LeagueItem("Soccer - France (Ligue 1)", "soccer", "fra.1"),
        LeagueItem("Soccer - Spain (LaLiga)", "soccer", "esp.1")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewByLeagueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        // Setup RecyclerView for teams
        setupRecyclerView()

        // Setup Spinner for leagues
        setupSpinner()

        // Show initial message
        showEmptyState(getString(R.string.select_league_message))
    }

    private fun setupRecyclerView() {
        teamsAdapter = TeamsAdapter { team, sport, league ->
            // Handle team click - navigate to team details
            navigateToTeamDetails(team, sport, league)
        }
        binding.recyclerViewTeams.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTeams.adapter = teamsAdapter
    }

    private fun setupSpinner() {
        // Create adapter for spinner with league display names
        val leagueNames = availableLeagues.map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, leagueNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLeagues.adapter = adapter

        // Set spinner item selected listener
        binding.spinnerLeagues.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position >= 0) {
                    val selectedLeague = availableLeagues[position]
                    fetchTeams(selectedLeague.sport, selectedLeague.league)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Hide teams when nothing is selected
                binding.recyclerViewTeams.visibility = View.GONE
                binding.tvMessage.visibility = View.VISIBLE
                binding.tvMessage.text = getString(R.string.select_league_message)
            }
        }
    }

    private fun fetchTeams(sport: String, league: String) {
        showLoading()

        val call = ViewByLeagueRetrofitClient.viewByLeagueService.getTeamsByLeague(sport, league)
        call.enqueue(object : Callback<com.example.espncito.model.ViewByLeagueModel> {
            override fun onResponse(
                call: Call<com.example.espncito.model.ViewByLeagueModel>,
                response: Response<com.example.espncito.model.ViewByLeagueModel>
            ) {
                hideLoading()

                if (response.isSuccessful) {
                    val viewByLeagueModel = response.body()
                    if (viewByLeagueModel != null && viewByLeagueModel.sports.isNotEmpty()) {
                        val teams = extractTeamsFromResponse(viewByLeagueModel)
                        if (teams.isNotEmpty()) {
                            showTeams(teams, sport, league)
                        } else {
                            showEmptyState(getString(R.string.no_teams_found))
                        }
                    } else {
                        showEmptyState(getString(R.string.no_data_available))
                    }
                } else {
                    showError("Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<com.example.espncito.model.ViewByLeagueModel>, t: Throwable) {
                hideLoading()
                showError("${getString(R.string.network_error)}: ${t.message}")
            }
        })
    }

    private fun extractTeamsFromResponse(viewByLeagueModel: com.example.espncito.model.ViewByLeagueModel): List<TeamInfo> {
        val teams = mutableListOf<TeamInfo>()

        viewByLeagueModel.sports.forEach { sport ->
            sport.leagues.forEach { league ->
                league.teams.forEach { leagueTeam ->
                    teams.add(leagueTeam.team)
                }
            }
        }

        return teams
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewTeams.visibility = View.GONE
        binding.tvMessage.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showTeams(teams: List<TeamInfo>, sport: String, league: String) {
        // Set current league info to adapter
        teamsAdapter.setCurrentLeagueInfo(sport, league)
        teamsAdapter.submitList(teams)
        binding.recyclerViewTeams.visibility = View.VISIBLE
        binding.tvMessage.visibility = View.GONE
    }

    private fun showEmptyState(message: String) {
        binding.recyclerViewTeams.visibility = View.GONE
        binding.tvMessage.visibility = View.VISIBLE
        binding.tvMessage.text = message
    }

    private fun showError(errorMessage: String) {
        binding.recyclerViewTeams.visibility = View.GONE
        binding.tvMessage.visibility = View.VISIBLE
        binding.tvMessage.text = errorMessage
    }

    private fun navigateToTeamDetails(team: TeamInfo, sport: String, league: String) {
        // Navigate to team details activity
        val intent = Intent(requireContext(), ViewByTeamActivity::class.java).apply {
            putExtra("TEAM_ID", team.id.toIntOrNull() ?: 0)
            putExtra("SPORT", sport)
            putExtra("LEAGUE", league)
            putExtra("TEAM_NAME", team.displayName)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}