package com.schaefer.healthygarden.ui.create_edit

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.schaefer.healthygarden.R
import kotlinx.android.synthetic.main.activity_create_edit_garden.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class CreateEditGardenActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_garden)
        setSupportActionBar(includeToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}