package com.schaefer.healthygarden.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.Mock
import com.schaefer.healthygarden.ui.create_edit.CreateEditGardenActivity
import com.schaefer.healthygarden.ui.home.adapter.GardenAdapter
import com.schaefer.healthygarden.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.content_main.*
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
        homeViewModel.listOfGarden.observe(viewLifecycleOwner, Observer {listOfGardens ->
            pbHome.visibility = if(listOfGardens.isNotEmpty()) View.GONE else View. VISIBLE
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

        fbCreateGarden.setOnClickListener {view ->
            view.findNavController().navigate(R.id.action_homeFragment_to_createEditGardenFragment)
//            startActivity(Intent(requireContext(), CreateEditGardenActivity::class.java))
        }
    }
}
