package com.example.espncito.ui.viewByLeague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.espncito.databinding.FragmentViewByLeagueBinding

class ViewByLeagueFragment : Fragment() {

    private var _binding: FragmentViewByLeagueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewByLeagueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Por ahora solo mostramos un texto
        binding.tvTitle.text = "Pantalla de Ligas - Próximamente"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}