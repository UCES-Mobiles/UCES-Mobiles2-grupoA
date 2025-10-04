package com.example.espncito.ui.viewByLeague

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

            // You can load the logo here using Glide or Picasso later
            // For now, we'll use a placeholder
            // Glide.with(binding.root).load(team.logos?.firstOrNull()?.href).into(binding.teamLogo)
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