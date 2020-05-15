package com.example.testnoteapplication.view.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProviders
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
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.Util.NotificationUtils
import com.example.testnoteapplication.data.db.async.UpdateTask
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.EditNoteViewModel
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
           // var model:AllNotesModel
            val bundle = Bundle().apply {
                putSerializable("notesModel", notesModel)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    lateinit var model : AllNotesModel

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
                Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show()
                //if(NoteUtil.checkInput(model.createdOn))
                    NotificationUtils().setNotification(model,mNotificationTime, this.requireActivity())
                closeCurrentFragment()
            } else
                Log.e("NO ", "No");
        })
    }

    private fun initListener() {
        datePicker.setOnClickListener { v ->
            when (v?.id) {
                R.id.datePicker -> pickDateTime()
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
    private fun saveNote() {
        var notesModel =
            AllNotesModel(
                allNotesModel.noteId,
                noteTitle.text.toString(),
                noteDescription.text.toString(),
                NoteUtil.NOTE,
                dateTextView.text.toString(), "", 1,0)
        model = notesModel
        UpdateTask(this.context, viewModel, notesModel).execute()
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
    override fun getTheme(): Int {
        return R.style.AppTheme_NoActionBar_FullScreenDialog
    }
}
