package com.example.testnoteapplication.view.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.Util.NotificationUtils
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.activity.MainActivity
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
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.

    var cal = Calendar.getInstance()
    lateinit var model :AllNotesModel
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
        observeValidateInputs(view)
    }

    private fun setUpView(view: View) {
        var noteTitle = view.findViewById<EditText>(R.id.noteTitle)
        noteTitle.requestFocus()
        val inputMethodManager: InputMethodManager =
            context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        dateTextView = view.findViewById(R.id.datePicker)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
    }

    private fun observeAddNoteViewModel() {
        viewModel.getValue().observe(viewLifecycleOwner,Observer<Boolean>{ value ->
            if(value){
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
                NotificationUtils().setNotification(model,mNotificationTime, this.requireActivity())
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
            if(view?.let { it1 -> validateInputs(it1) }!!) {
                saveNote()
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
                dateTextView.text.toString(),
            "",1)
        //viewModel.addNoteVm(notesModel)
        //todo 6 utils.validate method (pass this.context)
        model = notesModel
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


    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}





