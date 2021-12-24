package com.dhmaciel.githubstars.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.dhmaciel.githubstars.R
import com.dhmaciel.githubstars.core.result.model.GitListError
import com.dhmaciel.githubstars.core.utils.invisible
import com.dhmaciel.githubstars.core.utils.viewBinding
import com.dhmaciel.githubstars.core.utils.visible
import com.dhmaciel.githubstars.databinding.FragmentGitlistBinding
import com.dhmaciel.githubstars.home.domain.model.GitList
import com.dhmaciel.githubstars.home.ui.adapter.GitItemRecyclerViewAdapter
import com.dhmaciel.githubstars.home.ui.viewmodel.GitListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitListFragment : Fragment() {

    private val viewModel: GitListViewModel by viewModels()
    private var binding: FragmentGitlistBinding by viewBinding()

    private lateinit var viewAdapter: GitItemRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGitlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupAdapter()
        setupRecycleViewScroll()
        viewModel.fetchRepositories()
    }

    private fun setupAdapter() {
        viewAdapter = GitItemRecyclerViewAdapter(this)
        binding.rvGitList.adapter = viewAdapter
    }

    private fun setupRecycleViewScroll() {
        with(binding.rvGitList) {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!canScrollVertically(1) && newState == SCROLL_STATE_IDLE) {
                        viewModel.fetchRepositories(pageNumber = viewAdapter.pageNumber)
                    }
                }
            })
        }

    }

    private fun setupObservers() {
        viewModel.gitListViewState.observe(viewLifecycleOwner, { state ->
            state.handleIt(
                onSuccess = { handleProposalStatus(it) },
                onFailure = { handleFailure(it) },
                isLoading = { handleLoading(it) }
            )
        })
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) binding.pbGitList.visible() else binding.pbGitList.invisible()
    }

    private fun handleFailure(error: GitListError) {
        showAlert(error)
    }

    private fun handleProposalStatus(gitList: GitList) {
        viewAdapter.gitListItems = ArrayList(gitList.items)
    }

    private fun showAlert(error: GitListError) {

        val builder = AlertDialog.Builder(requireContext())

        val title = getString(R.string.alert_default_title)
        val message =
            if (error.message.isBlank()) getString(R.string.alert_default_message) else error.message
        val confirmButton = getString(R.string.alert_default_confirm_button)

        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setPositiveButton(confirmButton) { _, _ -> }
            show()
        }
    }
}