package com.example.testnoteapplication.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.data.model.NotesModel
import com.example.testnoteapplication.viewmodel.AddNoteViewModel
import com.example.testnoteapplication.viewmodel.enum.NoteType
import kotlinx.android.synthetic.main.add_note_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            AddNoteFragment()
    }
    var cal = Calendar.getInstance()
    private lateinit var viewModel: AddNoteViewModel
    lateinit var dateTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTextView = view.findViewById(R.id.datePicker)
        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
        datePicker.setOnClickListener(this)
        addNote.setOnClickListener{
        var notesModel =
                NotesModel(1, noteTitle.text.toString(), noteDescription.text.toString(), NoteType.NOTE.toString())
            viewModel.addNoteVm(notesModel)
                .observe(viewLifecycleOwner, Observer<Boolean> { valueBoolean ->
                    Log.e("NOTE ", valueBoolean.toString())
                })
        }
    }
    private fun openDatePicker(v: View?) {

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        DatePickerDialog(v?.context!!, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val format = sdf.format(cal.time)
        dateTextView.text = format
    }
        override fun onClick(v: View?) {
        when(v?.id){
            R.id.datePicker-> openDatePicker(v)
        }
    }
}





