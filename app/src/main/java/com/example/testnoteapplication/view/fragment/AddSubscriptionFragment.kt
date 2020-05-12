package com.example.testnoteapplication.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.db.async.InsertSubscriptionTask
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.adapter.CustomAdapterSpinnerSub
import com.example.testnoteapplication.viewmodel.AddSubscriptionViewModel
import kotlinx.android.synthetic.main.add_subscription_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class AddSubscriptionFragment : DialogFragment() {

    companion object {
        fun newInstance() =
            AddSubscriptionFragment()
    }

    //Var declaration
    private lateinit var viewModel: AddSubscriptionViewModel
    lateinit var dateTextView: TextView
    var cal = Calendar.getInstance()
    lateinit var subName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpView(view)
        initViewModel()
        initListener()
        observeAddSubscriptionViewModel()
        observeValidateInputs(view)
    }

    private fun observeAddSubscriptionViewModel() {
        viewModel.getValue().observe(viewLifecycleOwner, Observer<Boolean> { value ->
            if (value) {
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
                closeCurrentFragment()
            } else
                Log.e("NO ", "No");
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddSubscriptionViewModel::class.java)
    }

    private fun initListener() {
        expiryDate.setOnClickListener { v ->
            when (v?.id) {
                R.id.expiryDate -> openDatePicker(v)
            }
        }

        addSubscription.setOnClickListener {
            if(view?.let { it1 -> validateInputs(it1) }!!) {
                saveNote()
            }
        }
    }

    private fun saveNote() {
        var notesModel =
            AllNotesModel(
                0,
                subName,
                subDescription.text.toString(),
                NoteUtil.SUB, "",
                dateTextView.text.toString(), 2
            )
        InsertSubscriptionTask(this.context, viewModel, notesModel).execute()
    }

    private fun closeCurrentFragment() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun setUpView(view: View) {

        dateTextView = view.findViewById(R.id.expiryDate)
        val spin = view.findViewById<View>(R.id.subTitle) as Spinner
        //getting Subscription array list from string resource
        var subscriptions_array = view.context.resources.getStringArray(R.array.subscription_array)
        //getting subscription icons array list from string resource
        var icons_array = view.context.resources.getIntArray(R.array.icons_array)
        val customAdapter = CustomAdapterSpinnerSub(view.context, icons_array, subscriptions_array)
        spin.adapter = customAdapter
        //select listener on spinner
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                subName = subscriptions_array[position]
                if (subscriptions_array[position] == "Add Custom") {
                    Toast.makeText(
                        view.context,
                        "Other subsription ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    view.context,
                    "Please select subscription name  ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_subscription_fragment, container, false)
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

    private fun observeValidateInputs(view: View) {
        var expiryDate = view.findViewById<TextView>(R.id.expiryDate)
        /*var subTitle = view.findViewById<View>(R.id.subTitle) as Spinner
        var subError = view.findViewById<TextView>(R.id.subError)*/
        expiryDate.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    if (!NoteUtil.checkInput(expiryDate)) {
                        expiryDate.error = "Expiry date can't be empty!"
                    }
                }
            }
        }

        /*subTitle.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    if (subTitle.selectedItem.toString().equals("Choose Subscription")) {
                        subError.error = "Select subscription title!"
                    }
                }
            }
        }*/
    }

    private fun validateInputs(view: View): Boolean {
        //Form Validation
        var expiryDate = view.findViewById<TextView>(R.id.expiryDate)
        /*var subTitle = view.findViewById<View>(R.id.subTitle) as Spinner
        var subError = view.findViewById<TextView>(R.id.subError)
        val subTitleSelectedView = subTitle.selectedView*/
        if (!NoteUtil.checkInput(expiryDate)) {
            expiryDate.error = "Expiry date can't be empty!"
            return false
        } /*else if(subTitle.selectedItem == "Choose Subscription") {
            subError.error = "Select subscription title!"
            return false
        }*/

        return true
    }
}
