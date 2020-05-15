package com.example.testnoteapplication.view.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.Util.NotificationUtils
import com.example.testnoteapplication.data.db.async.UpdateTaskSub
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.view.adapter.CustomAdapterSpinnerSub
import com.example.testnoteapplication.viewmodel.EditSubscriptionViewModel
import kotlinx.android.synthetic.main.add_subscription_fragment.addSubscription
import kotlinx.android.synthetic.main.add_subscription_fragment.expiryDate
import kotlinx.android.synthetic.main.add_subscription_fragment.subDescription
import java.text.SimpleDateFormat
import java.util.*



class EditSubscriptionFragment : DialogFragment() {
    lateinit var allNotesModel: AllNotesModel
    companion object {
        fun newInstance(notesModel: AllNotesModel) : EditSubscriptionFragment{
            val fragment = EditSubscriptionFragment()
            val bundle = Bundle().apply {
                putSerializable("notesModel", notesModel) }
            fragment.arguments = bundle
            return fragment
        }
    }
    //Var declaration
    private var mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private lateinit var viewModel: EditSubscriptionViewModel
    lateinit var dateTextView: TextView
    lateinit var model : AllNotesModel
    lateinit var subName : String
    var subicon : Int = 0
    val icons_array = intArrayOf(R.drawable.disney, R.drawable.googleplay, R.drawable.hbo, R.drawable.hulu, R.drawable.netflix, R.drawable.primevideo, R.drawable.ic_add)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_subscription_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        allNotesModel =arguments?.getSerializable("notesModel") as AllNotesModel
        setAllValues()
        initViewModel()
        initListener()
        observeValidateInputs(view)
        observeEditSubscriptionViewModel()
    }

    private fun setAllValues() {
        var subname =allNotesModel.noteTitle
        subDescription.setText(allNotesModel.noteDescription)
        expiryDate.text = allNotesModel.expiryDate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditSubscriptionViewModel::class.java)

    }

    private fun observeEditSubscriptionViewModel() {
        viewModel.getValue().observe(viewLifecycleOwner, Observer<Boolean> { value ->
            if (value) {
                Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show()
                if(NoteUtil.checkInput(model.createdOn))
                    NotificationUtils().setNotification(model,mNotificationTime, this.requireActivity())
                    closeCurrentFragment()
            } else
                Log.e("NO ", "No")
        })
    }
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(EditSubscriptionViewModel::class.java)
    }

    private fun initListener() {
        expiryDate.setOnClickListener { v ->
            when (v?.id) {
                R.id.expiryDate -> pickDateTime()
            }
        }

        addSubscription.setOnClickListener {
            if (view?.let { it1 -> validateInputs(it1) }!!) {
                saveNote()
            }
        }
    }
    private fun saveNote() {
        var notesModel =
                AllNotesModel(
                        allNotesModel.noteId,
                        subName,
                        subDescription.text.toString(),
                        NoteUtil.SUB,"",
                        dateTextView.text.toString(),2,subicon)
                model=notesModel
        UpdateTaskSub(this.context, viewModel, notesModel).execute()

    }
    private fun closeCurrentFragment() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun setUpView(view: View) {
        dateTextView = view.findViewById(R.id.expiryDate)
        val spin = view.findViewById<View>(R.id.subTitle) as Spinner
        //getting Subscription array list from string resource
        var subscriptions_array = view.context.resources.getStringArray(R.array.subscription_array)
        val customAdapter = CustomAdapterSpinnerSub(view.context,icons_array,subscriptions_array)
        spin.adapter = customAdapter
        //select listener on spinner
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                subName=subscriptions_array[position]
                subicon =icons_array[position]
                subicon =position

                if(subscriptions_array[position]=="Add Custom"){
                    Toast.makeText(
                            view.context,
                            "Other subsription " ,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
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
                dateTextView.text = NoteUtil.convertDateToString(pickedDateTime)
                mNotificationTime = NoteUtil.convertDateToTimeInMilli(pickedDateTime)
                // NotificationUtils().setNotification(model,mNotificationTime, this.requireActivity())
                // doSomethingWith(pickedDateTime)
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    private fun observeValidateInputs(view: View) {
        var expiryDate = view.findViewById<TextView>(R.id.expiryDate)
        var subDescription = view.findViewById<EditText>(R.id.subDescription)
        expiryDate.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    if (!NoteUtil.checkInput(expiryDate)) {
                        expiryDate.error = "Expiry date can't be empty!"
                    }
                }
            }
        }
        subDescription.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    if (!NoteUtil.checkInput(subDescription)) {
                        subDescription.error = "Description can't be empty!"
                    }
                }
            }
        }
    }

    private fun validateInputs(view: View): Boolean {
        //Form Validation
        var expiryDate = view.findViewById<TextView>(R.id.expiryDate)
        var subDescription = view.findViewById<EditText>(R.id.subDescription)
        if (!NoteUtil.checkInput(subDescription)) {
            subDescription.error = "Description can't be empty!"
            return false
        }
        if (!NoteUtil.checkInput(expiryDate)) {
            expiryDate.error = "Expiry date can't be empty!"
            return false
        }
        return true
    }
    override fun getTheme(): Int {
        return R.style.AppTheme_NoActionBar_FullScreenDialog
    }

}
