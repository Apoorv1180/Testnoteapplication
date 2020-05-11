package com.example.testnoteapplication.view.fragment

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer

import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.EditNoteViewModel
import com.sdsu.noteapp.data.db.async.UpdateTask
import kotlinx.android.synthetic.main.add_note_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class EditNoteFragment : DialogFragment() {

    companion object {
        fun newInstance() = EditNoteFragment()
    }
    var cal = Calendar.getInstance()
    lateinit var dateTextView: TextView
    private lateinit var viewModel: EditNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        initViewModel()
        initListner()
        observeEditNoteViewModel()
        observeValidateInputs(view)
    }

    private fun setUpView(view: View) {
        dateTextView = view.findViewById(R.id.datePicker)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(EditNoteViewModel::class.java)
    }

    private fun observeEditNoteViewModel() {
        viewModel.getValue().observe(viewLifecycleOwner, Observer<Boolean>{ value ->
            if(value){
                Toast.makeText(context, "Updated to Database", Toast.LENGTH_LONG).show()
                closeCurrentFragment()
            }else
                Log.e("NO ","No");
        })
    }

    private fun initListner() {
        datePicker.setOnClickListener { v ->
            when (v?.id) {
                R.id.datePicker -> openDatePicker(v)
            }
        }

        addNote.setOnClickListener {
            if(view?.let { it1 -> validateInputs(it1) }!!) {
                saveNote()
                //closeCurrentFragment()
            }
        }
    }

    private fun closeCurrentFragment() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun saveNote() {
        var notesModel =
            AllNotesModel(
                0,
                noteTitle.text.toString(),
                noteDescription.text.toString(),
                NoteUtil.NOTE,
                dateTextView.text.toString(),"",R.color.colornote)
        //viewModel.updateNoteVm(notesModel)
        UpdateTask(this.context, viewModel, notesModel).execute()
    }

    private fun openDatePicker(v: View?) {

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        DatePickerDialog(
            v?.context!!,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val format = sdf.format(cal.time)
        dateTextView.text = format
    }

    private fun validateInputs(view: View): Boolean {
        //Form Validation
        var noteTitle = view.findViewById<EditText>(R.id.noteTitle)
        var noteDescription = view.findViewById<EditText>(R.id.noteDescription)
        if(!NoteUtil.checkInput(noteTitle)) {
            noteTitle.error = "Title can't be empty!"
            return false
        } else if(!NoteUtil.checkInput(noteDescription)) {
            noteDescription.error = "Note description can't be empty!"
            return false
        }
        return true
    }

    private fun observeValidateInputs(view: View) {
        var noteTitle = view.findViewById<EditText>(R.id.noteTitle)
        noteTitle.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    if (!NoteUtil.checkInput(noteTitle)) {
                        noteTitle.error = "Title can't be empty!"
                    }
                }
            }
        }

        var noteDescription = view.findViewById<EditText>(R.id.noteDescription)
        noteDescription.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    if (!NoteUtil.checkInput(noteDescription)) {
                        noteDescription.error = "Note description can't be empty!"
                    }
                }
            }
        }
    }
}
