package com.example.testnoteapplication.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.db.async.InsertListTask
import com.example.testnoteapplication.data.db.async.UpdateTaskList
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AddListViewModel
import com.example.testnoteapplication.viewmodel.EditListViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.add_list_fragment.*


class EditListFragment : DialogFragment() {

    lateinit var allNotesModel: AllNotesModel

    companion object {
        fun newInstance(notesModel: AllNotesModel): EditListFragment {
            val fragment = EditListFragment()
            var model:AllNotesModel
            val bundle = Bundle().apply {
                putSerializable("notesModel", notesModel)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
    lateinit var finalString:String
    var itemlist = arrayListOf<String>()
    lateinit var adapter: ArrayAdapter<String>
    private lateinit var viewModel: EditListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditListViewModel::class.java)
        allNotesModel =arguments?.getSerializable("notesModel") as AllNotesModel
        createItemList()
        setUpView(view)
        initListener()
        observeAddListModel()
    }

    private fun createItemList() {
        itemlist.clear()
        itemlist.addAll(NoteUtil.generateDescriptionStringList(allNotesModel.noteDescription))
    }

    private fun observeAddListModel() {
        viewModel.getValue().observe(viewLifecycleOwner, Observer<Boolean>{ value ->
            if(value){
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
                closeCurrentFragment()
            }else
                Log.e("NO ","No");
        })
    }

    private fun closeCurrentFragment() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun initListener() {
        add.setOnClickListener {
            itemlist.add(editText.text.toString())
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
            // This is because every time when you add the item the input space or the eidt text space will be cleared
            editText.text.clear()
        }

        delete.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item)) {
                    adapter.remove(itemlist.get(item))
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(context, "You Selected the item --> "+ itemlist.get(i), Toast.LENGTH_LONG).show()
        }
        addList.setOnClickListener{
            if(itemlist!= null) {

                finalString = convertHashMapToGson(makeListHashMap())
           //     val finalString = convertGsonToString()
            }
            if(itemlist.size > 0){
                saveList()
            }
        }
    }

    private fun saveList() {
        var title :String
        title = listTitle.text.toString()
        var notesModel =
                AllNotesModel(
                        allNotesModel.noteId,
                        title,
                        finalString,
                        NoteUtil.LIST,"",
                        "",3)

        UpdateTaskList(this.context, viewModel, notesModel).execute()

    }

    private fun convertHashMapToGson(makeListHashMap: HashMap<String, Boolean>): String {
        val gson = Gson()
        var jsonString: String = gson.toJson(makeListHashMap)
       Toast.makeText(context,"string"+ jsonString,Toast.LENGTH_SHORT).show()
        return jsonString
    }

    private fun makeListHashMap(): HashMap<String, Boolean> {
        val checked: SparseBooleanArray = listView.checkedItemPositions
        val data = hashMapOf<String,Boolean >()
        for (i in 0 until listView.adapter.count) {
            if (checked[i]) {
                data.put(itemlist.get(i), true)
            } else {
                data.put(itemlist.get(i), false)
            }
        }
        return data
    }

    private fun setUpView(view: View) {

        adapter = ArrayAdapter(view.context,
                android.R.layout.simple_list_item_multiple_choice
                , itemlist )
        listView.adapter = adapter
        listTitle.setText(allNotesModel.noteTitle.toString())
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}





