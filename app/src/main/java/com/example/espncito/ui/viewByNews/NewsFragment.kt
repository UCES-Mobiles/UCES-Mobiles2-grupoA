package com.example.espncito.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appparcial2.model.Headline
import com.example.espncito.R
import com.example.espncito.adapter.NewsAdapter
import com.example.espncito.viewmodel.NewsViewModel

class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView = view.findViewById(R.id.rlNews)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = NewsAdapter(emptyList())
        recyclerView.adapter = adapter

        // En el observer del LiveData
        viewModel.newsList.observe(viewLifecycleOwner) { news ->
            if (news.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                adapter.setData(news)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observamos el LiveData del ViewModel
        viewModel.newsList.observe(viewLifecycleOwner) { news ->
            Log.d("NewsFragment", "Recibí ${news.size} noticias")
            adapter.setData(news) // aquí ya pasarías la lista al adapter
        }

        // Llamamos a la API
        viewModel.apiNews()
    }
}
