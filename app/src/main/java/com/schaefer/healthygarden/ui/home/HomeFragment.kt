package com.schaefer.healthygarden.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.ui.home.adapter.GardenAdapter
import com.schaefer.healthygarden.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
    }

    private fun setupObservers() {
        homeViewModel.listOfGarden.observe(viewLifecycleOwner, Observer { listOfGardens ->
            pbHome.visibility = View.GONE
            tvEmptyList.visibility = if (listOfGardens.isNullOrEmpty()) View.VISIBLE else View.GONE
            rvGarden.apply {
                adapter = GardenAdapter(listOfGardens)
            }
        })
    }

    private fun setupView() {
        homeViewModel.getGardens()

        rvGarden.apply {
            layoutManager = GridLayoutManager(activity, 2)
        }

        fbCreateGarden.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_homeFragment_to_createEditGardenFragment)
//            startActivity(Intent(requireContext(), CreateEditGardenActivity::class.java))
        }

        srlHome.setOnRefreshListener {
            Toast.makeText(requireContext(), "Updating the list of Gardens!", Toast.LENGTH_SHORT)
                .show()
            Handler().postDelayed(Runnable { srlHome.isRefreshing = false }, 2000)
        }
    }
}
