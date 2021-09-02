package com.nurozkaya.noteapp.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nurozkaya.noteapp.MainActivity
import com.nurozkaya.noteapp.R
import com.nurozkaya.noteapp.databinding.FragmentUpdateNoteBinding
import com.nurozkaya.noteapp.model.Note
import com.nurozkaya.noteapp.viewmodel.NoteViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class UpdateNoteFragment : Fragment() {

    private var currentDate:String?=null
    private var _binding:FragmentUpdateNoteBinding?=null
    private val binding get()=_binding!!
    //receive obj
    private val args:UpdateNoteFragmentArgs by navArgs()
    private lateinit var  currentNote: Note
    private lateinit var noteViewModel:NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentUpdateNoteBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel=(activity as MainActivity).noteViewModel
        currentNote=args.note!!

        val sdf= SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

        currentDate=sdf.format(Date())
        binding.timeStamp.text=currentDate


        binding.noteTitleUpdate.setText(currentNote.noteTitle)
        binding.noteBodyUpdate.setText(currentNote.noteBody)
        binding.timeStamp.text = currentNote.noteDate


        //update process
        binding.updatedButton.setOnClickListener {
            val title =binding.noteTitleUpdate.text.toString().trim()
            val body = binding.noteBodyUpdate.text.toString().trim()
            val noteDate = currentDate.toString()

            //title control
            if(title.isNotEmpty()) {
                val note = Note(currentNote.id, title, body,noteDate)
                noteViewModel.updateNote(note)
                Snackbar.make(view, "Updated", Snackbar.LENGTH_LONG).show()

                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homePageFragment)
            } else {
                Toast.makeText(context, "Please enter a title", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("YES") { _,_ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homePageFragment)
            }
            setNegativeButton("NO", null)
        }.create().show()
    }

    //for delete option
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_delete-> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}