package com.example.testnoteapplication.view.fragment

import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testnoteapplication.R
import com.example.testnoteapplication.Util.NoteUtil
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AddNoteViewModel
import com.google.gson.Gson
import com.sdsu.noteapp.data.db.async.InsertTaskList
import kotlinx.android.synthetic.main.add_list_fragment.*
import java.util.*



class AddListFragment : Fragment() {

    companion object {
        fun newInstance() = AddListFragment()
    }
    lateinit var finalString:String
    var itemlist = arrayListOf<String>()
    lateinit var adapter: ArrayAdapter<String>
    private lateinit var viewModel: AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
        setUpView(view)
        initListener()
        observeAddListModel()
    }

    private fun observeAddListModel() {
        viewModel.getValueList().observe(viewLifecycleOwner, Observer<Boolean>{ value ->
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
                        0,
                        title,
                        finalString,
                        NoteUtil.LIST,"",
                        "",3)

        InsertTaskList(this.context, viewModel, notesModel).execute()

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
    }

}
