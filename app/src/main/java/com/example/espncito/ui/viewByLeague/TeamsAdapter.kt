package com.example.espncito.ui.viewByLeague

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.espncito.R
import com.example.espncito.databinding.ItemTeamBinding
import com.example.espncito.model.TeamInfo

class TeamsAdapter(
    private val onTeamClick: (TeamInfo, String, String) -> Unit
) : ListAdapter<TeamInfo, TeamsAdapter.TeamViewHolder>(TeamDiffCallback) {

    // Store current sport and league to pass when team is clicked
    private var currentSport: String = ""
    private var currentLeague: String = ""

    fun setCurrentLeagueInfo(sport: String, league: String) {
        currentSport = sport
        currentLeague = league
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = getItem(position)
        holder.bind(team)
    }

    inner class TeamViewHolder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onTeamClick(getItem(adapterPosition), currentSport, currentLeague)
                }
            }
        }

        fun bind(team: TeamInfo) {
            binding.teamName.text = team.displayName
            binding.teamLocation.text = team.location
            binding.teamAbbreviation.text = team.abbreviation

            // Load team logo using Glide
            loadTeamLogo(team)
        }

        private fun loadTeamLogo(team: TeamInfo) {
            val logoUrl = team.logos?.firstOrNull()?.href

            if (!logoUrl.isNullOrEmpty()) {
                // Use Glide to load the image from URL
                Glide.with(binding.root.context)
                    .load(logoUrl)
                    .placeholder(R.drawable.ic_team_placeholder) // Use custom placeholder
                    .error(R.drawable.ic_error_placeholder) // Use custom error icon
                    .centerInside()
                    .into(binding.teamLogo)
            } else {
                // If no logo URL, set custom placeholder
                binding.teamLogo.setImageResource(R.drawable.ic_team_placeholder)
            }
        }
    }

    object TeamDiffCallback : DiffUtil.ItemCallback<TeamInfo>() {
        override fun areItemsTheSame(oldItem: TeamInfo, newItem: TeamInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TeamInfo, newItem: TeamInfo): Boolean {
            return oldItem == newItem
        }
    }
}