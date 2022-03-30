package com.ksuta.finderusertest.screens.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksuta.finderusertest.R
import com.ksuta.finderusertest.databinding.FragmentSearchBinding
import com.ksuta.finderusertest.screens.search.adapters.HomeUsersAdapter
import com.ksuta.finderusertest.screens.search.adapters.ItemCallback
import com.ksuta.finderusertest.screens.search.adapters.UsersLoaderStateAdapter
import com.ksuta.finderusertest.screens.search.di.SearchFragmentComponent
import com.ksuta.finderusertest.utils.navigate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search), ItemCallback {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<SearchViewModel> { factory }
    private val viewBinding by viewBinding(FragmentSearchBinding::bind)

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        HomeUsersAdapter(requireActivity(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SearchFragmentComponent.init(requireActivity() as AppCompatActivity).inject(this)
        with(viewBinding) {
            usersView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UsersLoaderStateAdapter(),
                footer = UsersLoaderStateAdapter()
            )
            queryView.doAfterTextChanged { text ->
                viewModel.setQuery(text?.toString() ?: "")
            }
            toolbarView.title = requireContext().getString(R.string.app_name)
        }

        adapter.addLoadStateListener { state ->
            with(viewBinding) {
                usersView.isVisible = state.refresh != LoadState.Loading
                progressView.isVisible = state.refresh == LoadState.Loading
                queryView.isVisible = true
            }
        }

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.users
                .collectLatest(adapter::submitData)

            viewModel.query
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .onEach(::updateSearchQuery)
                .launchIn(lifecycleScope)
        }
    }

    private fun updateSearchQuery(searchQuery: String) {
        with(viewBinding.queryView) {
            if ((text?.toString() ?: "") != searchQuery) {
                setText(searchQuery)
            }
        }
    }

    override fun onClick(userId: UserModel?) {
        val action = userId?.let {
            SearchFragmentDirections.actionSearchFragmentToDetailUserFragment(
                it
            )
        }
        action?.let { navigate(it) }
    }

}