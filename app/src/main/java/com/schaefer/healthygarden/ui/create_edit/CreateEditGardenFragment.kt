package com.schaefer.healthygarden.ui.create_edit

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.ui.create_edit.viewmodel.CreateEditViewModel
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.layout_garden_header.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.ui.garden.details.DetailsGardenFragment
import com.schaefer.healthygarden.ui.garden.details.DetailsGardenFragment.Companion.ARG_GARDEN
import java.util.*

class CreateEditGardenFragment : Fragment() {
    private val calendar: Calendar by inject()
    private val createEditViewModel: CreateEditViewModel by viewModel()
    private var flagEditMode = false
    private lateinit var gardenFinal: Garden

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(arguments?.get(DetailsGardenFragment.ARG_GARDEN) as Garden?)
        setupObservers()
    }

    private fun setupView(garden: Garden?) {
        if (garden != null) {
            gardenFinal = garden
            includeCreateGardenForm.etDate.setText(garden.createdAt)
            includeCreateGardenForm.etName.setText(garden.name)
            includeCreateGardenForm.etDescription.setText(garden.description)
            cbIndoor.isChecked = garden.isIndoor
            flagEditMode = true
        }

        createEditViewModel.setIsIndoor(garden?.isIndoor ?: false)

        includeCreateGardenForm.etDate.setOnClickListener {
            createDatePicker()
        }

        createEditViewModel.setEditMode(true)
        btnSave.setOnClickListener {
            if (flagEditMode) {
                garden?.id?.let { id -> createEditViewModel.editGarden(id) }
            } else {
                createEditViewModel.createGarden()
            }
        }

        cbIndoor.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            createEditViewModel.setIsIndoor(isChecked)
        }

        includeCreateGardenForm.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                createEditViewModel.setName(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        includeCreateGardenForm.etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                createEditViewModel.setDescription(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        includeCreateGardenForm.etDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                createEditViewModel.setDate(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupObservers() {
        createEditViewModel.isValidForm.observe(viewLifecycleOwner, Observer {
            btnSave.isEnabled = it
        })

        createEditViewModel.isCreatedSuccess.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Success to create this garden!", Toast.LENGTH_SHORT)
                .show()
        })

        createEditViewModel.idToNavigate.observe(viewLifecycleOwner, Observer { navigateId ->
            if (flagEditMode){
                gardenFinal.apply {
                    name = includeCreateGardenForm.etName.text.toString()
                    description = includeCreateGardenForm.etDescription.text.toString()
                    updatedAt = includeCreateGardenForm.etDate.text.toString()
                    createdAt = includeCreateGardenForm.etDate.text.toString()
                    isIndoor = cbIndoor.isChecked
                }
                requireView().findNavController().navigate(navigateId, bundleOf(ARG_GARDEN to gardenFinal))
            } else {
                requireView().findNavController().navigate(navigateId)
            }
        })
    }

    private fun createDatePicker() {
        DatePickerDialog(
            requireContext(),
            dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private val dateListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_YEAR, dayOfMonth)
            updateLabel()
        }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val date = sdf.format(calendar.time)
        includeCreateGardenForm.etDate.setText(date)
    }
}
