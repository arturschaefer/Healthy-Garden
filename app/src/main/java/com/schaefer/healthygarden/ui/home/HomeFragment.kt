package com.schaefer.healthygarden.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.MockDTO
import com.schaefer.healthygarden.ui.home.adapter.GardenAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvGarden.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = GardenAdapter(MockDTO.arrayListOfGardenDTO)
        }
    }
}
