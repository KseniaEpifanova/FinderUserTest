package com.ksuta.finderusertest.screens.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksuta.finderusertest.R
import com.ksuta.finderusertest.databinding.FragmentSearchBinding
import com.ksuta.finderusertest.screens.details.di.DetailUserFragmentComponent
import com.ksuta.finderusertest.screens.search.SearchViewModel
import javax.inject.Inject

class DetailUserFragment: Fragment(R.layout.fragment_detail) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<SearchViewModel> { factory }
    private val viewBinding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DetailUserFragmentComponent.init(requireActivity() as AppCompatActivity).inject(this)

    }

}