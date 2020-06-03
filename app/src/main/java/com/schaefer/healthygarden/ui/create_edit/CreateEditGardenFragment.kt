package com.schaefer.healthygarden.ui.create_edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.ui.create_edit.viewmodel.CreateEditViewModel
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.layout_garden_header.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import java.util.*

class CreateEditGardenFragment : Fragment() {
    private val calendar: Calendar by inject()
    private val createEditViewModel: CreateEditViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupView() {
        includeCreateGardenForm.etDate.setOnClickListener {
            createDatePicker()
        }

        btnSave.setOnClickListener {
            createEditViewModel.createGarden()
        }

        createEditViewModel.setIsIndoor(false)
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

    private fun setupObserver() {
        createEditViewModel.isValidForm.observe(viewLifecycleOwner, Observer {
            btnSave.isEnabled = it
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
        includeCreateGardenForm.etDate.setText(sdf.format(calendar.time))
    }
}
