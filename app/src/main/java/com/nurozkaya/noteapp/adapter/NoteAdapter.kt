package com.nurozkaya.noteapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nurozkaya.noteapp.R
import com.nurozkaya.noteapp.databinding.NoteLayoutAdapterBinding
import com.nurozkaya.noteapp.fragment.HomePageFragmentDirections
import com.nurozkaya.noteapp.model.Note
import com.squareup.picasso.Picasso

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    class NoteViewHolder(val itemBinding: NoteLayoutAdapterBinding)
        :RecyclerView.ViewHolder(itemBinding.root)


    //check whether two items have the same data. This will compare whole object
    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.noteBody == newItem.noteBody &&
                        oldItem.noteTitle == newItem.noteTitle
            }
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.noteTitle.text=currentNote.noteTitle
        holder.itemBinding.noteBody.text=currentNote.noteBody
        holder.itemBinding.timeStamp.text=currentNote.noteDate

        holder.itemView.setOnClickListener { mView ->
            //passed argument
            mView.findNavController()
                .navigate(HomePageFragmentDirections
                    .actionHomePageFragmentToUpdateNoteFragment(currentNote))
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}