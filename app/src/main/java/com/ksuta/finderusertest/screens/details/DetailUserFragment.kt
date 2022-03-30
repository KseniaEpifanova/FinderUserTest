package com.ksuta.finderusertest.screens.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.ksuta.finderusertest.databinding.FragmentDetailBinding
import com.ksuta.finderusertest.screens.details.di.DetailUserFragmentComponent
import com.ksuta.finderusertest.utils.observeToSafe
import javax.inject.Inject
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.ksuta.finderusertest.R
import com.ksuta.finderusertest.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailUserFragment : Fragment(R.layout.fragment_detail) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailUserViewModel> { factory }
    private val viewBinding by viewBinding(FragmentDetailBinding::bind)
    private val navController: NavController by lazy {
        Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment
        )
    }

    private val args: DetailUserFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DetailUserFragmentComponent.init(requireActivity() as AppCompatActivity, args.modelArg)
            .inject(this)

        subscribeToViewModel()
        setOptionsMenuVisible()
        (activity as MainActivity).setSupportActionBar(toolbarView)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
       // val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
      //  val navController = navHostFragment.navController
    }

    private fun subscribeToViewModel() {
        viewModel.setModel.observeToSafe(this, ::onDetailReceived)
    }

    private fun setOptionsMenuVisible() {
        setMenuVisibility(true)
        setHasOptionsMenu(true)
    }

    private fun onDetailReceived(model: DetailModel) {

        with(model) {
            viewBinding.nameView.apply {
                text = name
                visibility = View.VISIBLE
            }
            viewBinding.reputationView.apply {
                if (reputation.toString().isNotEmpty()) {
                    text = reputation.toString()
                    visibility = View.VISIBLE
                }
            }
            viewBinding.tagsView.apply {
                if (!topTags.isNullOrEmpty()) {
                    text = topTags
                    visibility = View.VISIBLE
                }
            }
            viewBinding.locationView.apply {
                if (!location.isNullOrEmpty()) {
                    text = location
                    visibility = View.VISIBLE
                }
            }
            viewBinding.creationDateView.apply {
                if (creationDate.toString().isNotEmpty()) {
                    text = creationDate
                    visibility = View.VISIBLE
                }
            }

            viewBinding.imageView.load(model.url) {
                placeholder(ColorDrawable(Color.TRANSPARENT))
            }
        }
        toolbarView.title = model.name
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home ->
                navController.popBackStack()

            else -> false
        }
    }
}