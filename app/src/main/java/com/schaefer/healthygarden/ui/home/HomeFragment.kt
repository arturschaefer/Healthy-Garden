package com.schaefer.healthygarden.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.MockDTO
import com.schaefer.healthygarden.ui.create_edit.CreateEditGardenActivity
import com.schaefer.healthygarden.ui.create_edit.CreateEditGardenFragment
import com.schaefer.healthygarden.ui.home.adapter.GardenAdapter
import com.schaefer.healthygarden.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_create.*
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
    }

    private fun setupView() {
        homeViewModel.getGardens()

        rvGarden.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = GardenAdapter(MockDTO.arrayListOfGardenDTO)
        }

        fbCreateGarden.setOnClickListener {
            startActivity(Intent(requireContext(), CreateEditGardenActivity::class.java))
        }
    }
}
