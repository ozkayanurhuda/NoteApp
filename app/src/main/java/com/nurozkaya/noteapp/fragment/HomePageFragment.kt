package com.nurozkaya.noteapp.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nurozkaya.noteapp.MainActivity
import com.nurozkaya.noteapp.R
import com.nurozkaya.noteapp.adapter.NoteAdapter
import com.nurozkaya.noteapp.databinding.FragmentHomePageBinding
import com.nurozkaya.noteapp.model.Note
import com.nurozkaya.noteapp.viewmodel.NoteViewModel


class HomePageFragment : Fragment(R.layout.fragment_home_page), SearchView.OnQueryTextListener {

    private var _binding:FragmentHomePageBinding?=null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel=(activity as MainActivity).noteViewModel

        setUpRecyclerView()

        binding.addNote.setOnClickListener { mView ->
            mView.findNavController().navigate(R.id.action_homePageFragment_to_newNoteFragment)

        }
    }

    private fun setUpRecyclerView() {
        noteAdapter= NoteAdapter()

        binding.rv.apply {
            layoutManager=StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter=noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNote().observe(viewLifecycleOwner, {note->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            })
        }
    }

    private fun updateUI(note: List<Note>) {
        if (note.isNotEmpty()) {
            binding.rv.visibility=View.VISIBLE
            binding.TextViewNoNotesAvailable.visibility=View.GONE
        } else {
            binding.rv.visibility=View.GONE
            binding.TextViewNoNotesAvailable.visibility=View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        val mMenuSearch=menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled=true
        mMenuSearch.setOnQueryTextListener(this)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if(p0!=null) {
            searchNote(p0)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if(p0!=null) {
            searchNote(p0)
        }
        return true
    }

    private fun searchNote(query:String?) {
        //If contains the searched text
        val searchQuery="%$query%"
        noteViewModel.searchNote(searchQuery).observe(this, { list ->
            noteAdapter.differ.submitList(list)
        })
    }
}