package com.example.testnoteapplication.view.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.example.testnoteapplication.data.db.async.InsertTask
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AddNoteViewModel
import kotlinx.android.synthetic.main.add_note_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : DialogFragment() {

    companion object {
        fun newInstance() =
            AddNoteFragment()
    }
    private var mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.

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
                Toast.makeText(context, "Added", Toast.LENGTH_LONG).show()
                //if(NoteUtil.checkInput(model.createdOn))
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
                R.id.datePicker -> pickDateTime()
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
            "",1,0)

        //viewModel.addNoteVm(notesModel)
        //todo 6 utils.validate method (pass this.context)
        model = notesModel
        InsertTask(this.context, viewModel, notesModel).execute()
        //closeCurrentFragment()
    }

    private fun pickDateTime() {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)
        val imm =
            context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val imm =
                context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                Log.i("TIME", pickedDateTime.toString())
                dateTextView.setText(NoteUtil.convertDateToString(pickedDateTime))
                mNotificationTime = NoteUtil.convertDateToTimeInMilli(pickedDateTime)
                }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
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
    override fun getTheme(): Int {
        return R.style.AppTheme_NoActionBar_FullScreenDialog
    }
}





