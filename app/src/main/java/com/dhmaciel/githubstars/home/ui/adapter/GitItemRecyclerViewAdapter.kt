package com.dhmaciel.githubstars.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.dhmaciel.githubstars.databinding.FragmentGitlistItemBinding
import com.dhmaciel.githubstars.home.domain.model.GitListItem

class GitItemRecyclerViewAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<GitItemRecyclerViewHolder>() {

    var pageNumber = 1

    var gitListItems: ArrayList<GitListItem> = arrayListOf()
        set(value) {
            val oldSize = field.size
            val newSize = value.size
            field.addAll(value)
            pageNumber++

            notifyItemRangeInserted(oldSize, newSize)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitItemRecyclerViewHolder =
        GitItemRecyclerViewHolder(
            FragmentGitlistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GitItemRecyclerViewHolder, position: Int) =
        holder.bind(gitListItems[position], fragment)

    override fun getItemCount(): Int = gitListItems.size
}