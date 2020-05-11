package com.example.testnoteapplication.view.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AddNoteViewModel
import com.sdsu.noteapp.data.db.async.InsertTask
import kotlinx.android.synthetic.main.add_note_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : DialogFragment() {

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
        setUpView(view)
        initViewModel()
        initListener()
        observeAddNoteViewModel()
    }

    private fun setUpView(view: View) {
        dateTextView = view.findViewById(R.id.datePicker)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
    }

    private fun observeAddNoteViewModel() {
        viewModel.getValue().observe(viewLifecycleOwner,Observer<Boolean>{ value ->
            if(value){
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
                closeCurrentFragment()
            }else
                Log.e("NO ","No");
        })
    }

    private fun initListener() {
        //reminder date picker text-view listener
        datePicker.setOnClickListener { v ->
            when (v?.id) {
                R.id.datePicker -> openDatePicker(v)
            }
        }
        //Add button click listener
        addNote.setOnClickListener {
            saveNote()

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
                dateTextView.text.toString(),
            "")
        //viewModel.addNoteVm(notesModel)
        //todo 6 utils.validate method (pass this.context)

        InsertTask(this.context, viewModel, notesModel).execute()
        //closeCurrentFragment()
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

}





