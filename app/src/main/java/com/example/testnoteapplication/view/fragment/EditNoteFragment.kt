package com.example.testnoteapplication.view.fragment

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import kotlinx.android.synthetic.main.add_note_fragment.addNote
import kotlinx.android.synthetic.main.add_note_fragment.datePicker
import kotlinx.android.synthetic.main.add_note_fragment.noteDescription
import kotlinx.android.synthetic.main.add_note_fragment.noteTitle
import java.text.SimpleDateFormat
import java.util.*

class EditNoteFragment : DialogFragment() {

    lateinit var allNotesModel: AllNotesModel


    companion object {
        fun newInstance(notesModel: AllNotesModel): EditNoteFragment {
            val fragment = EditNoteFragment()
            var model:AllNotesModel
            val bundle = Bundle().apply {
                putSerializable("notesModel", notesModel)
            }
            fragment.arguments = bundle
            return fragment
        }
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
        allNotesModel =arguments?.getSerializable("notesModel") as AllNotesModel
        setAllValues()
        initViewModel()
        initListener()
        observeEditNoteViewModel()
        observeValidateInputs(view)

        Toast.makeText(view.context, allNotesModel.noteTitle, Toast.LENGTH_LONG).show()
    }

    private fun setAllValues() {
        noteTitle.setText(allNotesModel.noteTitle.toString())
        noteDescription.setText(allNotesModel.noteDescription.toString())
        datePicker.setText(allNotesModel.createdOn.toString())
    }

    private fun setUpView(view: View) {
        dateTextView = view.findViewById(R.id.datePicker)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(EditNoteViewModel::class.java)
    }

    private fun observeEditNoteViewModel() {
        viewModel.getValue().observe(viewLifecycleOwner, Observer<Boolean> { value ->
            if (value) {
                Toast.makeText(context, "Updated to Database", Toast.LENGTH_LONG).show()
                closeCurrentFragment()
            } else
                Log.e("NO ", "No");
        })
    }

    private fun initListener() {
        datePicker.setOnClickListener { v ->
            when (v?.id) {
                R.id.datePicker -> openDatePicker(v)
            }
        }

        addNote.setOnClickListener {
            if (view?.let { it1 -> validateInputs(it1) }!!) {
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
                allNotesModel.noteId,
                noteTitle.text.toString(),
                noteDescription.text.toString(),
                NoteUtil.NOTE,
                dateTextView.text.toString(), "", 1)
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
        if (!NoteUtil.checkInput(noteTitle)) {
            noteTitle.error = "Title can't be empty!"
            return false
        } else if (!NoteUtil.checkInput(noteDescription)) {
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
    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}
