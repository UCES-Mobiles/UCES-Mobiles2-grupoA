package com.example.espncito.ui.viewByLeague

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.espncito.R
import com.example.espncito.databinding.FragmentViewByLeagueBinding
import com.example.espncito.model.LeagueItem
import com.example.espncito.model.TeamInfo
import com.example.espncito.ui.viewByTeam.ViewByTeamActivity
import com.example.espncito.viewmodel.TeamsState
import com.example.espncito.viewmodel.ViewByLeagueViewModel

class ViewByLeagueFragment : Fragment() {
    private var _binding: FragmentViewByLeagueBinding? = null
    private val binding get() = _binding!!
    private lateinit var teamsAdapter: TeamsAdapter
    private val viewModel: ViewByLeagueViewModel by viewModels()
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
        setupObservers()
    }
    private fun setupUI() {
        setupRecyclerView()
        setupSpinner()
    }

    private fun setupObservers() {
        // Observar el LiveData del ViewModel
        viewModel.teamsState.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
    }

    private fun handleState(state: TeamsState) {
        when (state) {
            is TeamsState.Loading -> showLoading()
            is TeamsState.Success -> showTeams(state.teams)
            is TeamsState.Error -> showError(state.message)
            is TeamsState.Empty -> showEmptyState(getString(R.string.no_teams_found))
            is TeamsState.Initial -> showEmptyState(getString(R.string.select_league_message))
        }
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
                    // Set current league info to adapter
                    teamsAdapter.setCurrentLeagueInfo(selectedLeague.sport, selectedLeague.league)
                    // Fetch teams using ViewModel
                    viewModel.fetchTeams(selectedLeague.sport, selectedLeague.league)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Show initial message when nothing is selected
                showEmptyState(getString(R.string.select_league_message))
            }
        }
    }
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewTeams.visibility = View.GONE
        binding.tvMessage.visibility = View.GONE
    }
    private fun showTeams(teams: List<TeamInfo>) {
        teamsAdapter.submitList(teams)
        binding.progressBar.visibility = View.GONE
        binding.recyclerViewTeams.visibility = View.VISIBLE
        binding.tvMessage.visibility = View.GONE
    }
    private fun showEmptyState(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerViewTeams.visibility = View.GONE
        binding.tvMessage.visibility = View.VISIBLE
        binding.tvMessage.text = message
    }
    private fun showError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
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
        }
        startActivity(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}