package com.nurozkaya.noteapp.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nurozkaya.noteapp.MainActivity
import com.nurozkaya.noteapp.R
import com.nurozkaya.noteapp.databinding.FragmentNewNoteBinding
import com.nurozkaya.noteapp.model.Note
import com.nurozkaya.noteapp.viewmodel.NoteViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var currentDate:String?=null
    private var _binding:FragmentNewNoteBinding?=null
    private val binding get()=_binding!!
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView:View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel=(activity as MainActivity).noteViewModel
        mView=view

        val sdf=SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        currentDate=sdf.format(Date())
        binding.timeStamp.text=currentDate
    }

    //------------------------------------SAVE
    private fun saveNote(view:View) {

        val noteTitle=binding.NoteTitle.text.toString().trim()
        val noteBody = binding.NoteBody.text.toString().trim()
        val noteDate=binding.timeStamp.text.toString()

        if (noteTitle.isNotEmpty()) {
            val note =Note(0, noteTitle, noteBody, noteDate)

            noteViewModel.addNote(note)
            Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG).show()

            view.findNavController().navigate(R.id.action_newNoteFragment_to_homePageFragment)

        } else {
            Toast.makeText(context, "Please enter a title", Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save_menu -> { saveNote(mView)}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_note_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}