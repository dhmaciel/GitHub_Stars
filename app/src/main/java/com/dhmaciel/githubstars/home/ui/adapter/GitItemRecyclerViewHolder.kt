package com.dhmaciel.githubstars.home.ui.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhmaciel.githubstars.R
import com.dhmaciel.githubstars.core.utils.Constants.AVATAR_SIZE
import com.dhmaciel.githubstars.databinding.FragmentGitlistItemBinding
import com.dhmaciel.githubstars.home.domain.model.GitListItem

class GitItemRecyclerViewHolder(private val binding: FragmentGitlistItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GitListItem, fragment: Fragment) {
        with(binding) {
            setImageFromUrl(item.owner.avatarURL, fragment, ivGitlistAvatar)
            tvGitlistRepo.text = item.repoName
            tvGitlistAuthor.text = item.owner.login
            tvGitlistStarNumber.text = item.stargazersCount.toString()
            tvGitlistForkNumber.text = item.forksCount.toString()
        }
    }

    private fun setImageFromUrl(url: String, fragment: Fragment, imageView: AppCompatImageView) {
        Glide
            .with(fragment)
            .load(url)
            .placeholder(R.drawable.ic_git_broken_image_24)
            .circleCrop()
            .override(AVATAR_SIZE)
            .into(imageView)
    }
}