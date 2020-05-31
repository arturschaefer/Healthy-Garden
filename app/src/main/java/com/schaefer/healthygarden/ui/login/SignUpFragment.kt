package com.schaefer.healthygarden.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import coil.api.load
import com.schaefer.healthygarden.R
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_signup, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        ivLogo.load(resources.getDrawable(R.drawable.ic_logo))

        setupClicks()
    }

    private fun setupClicks() {
        ibBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}