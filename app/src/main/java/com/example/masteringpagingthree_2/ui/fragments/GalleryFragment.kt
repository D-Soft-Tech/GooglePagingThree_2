package com.example.masteringpagingthree_2.ui.fragments // ktlint-disable package-name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.masteringpagingthree_2.R
import com.example.masteringpagingthree_2.data.model.Status
import com.example.masteringpagingthree_2.databinding.FragmentGalleryBinding
import com.example.masteringpagingthree_2.ui.adapters.RvLoadStateAdapter
import com.example.masteringpagingthree_2.ui.adapters.RvLoadStateAdapterFactory
import com.example.masteringpagingthree_2.ui.adapters.UnsplashPhotoAdapters
import com.example.masteringpagingthree_2.ui.viewModel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment @Inject constructor() : BaseFragment() {
    @Inject
    lateinit var rvAdapter: UnsplashPhotoAdapters

    @Inject
    lateinit var rvLoadStateAdapterFactory: RvLoadStateAdapterFactory
    private lateinit var rvLoadStateAapter: RvLoadStateAdapter
    private val viewModel: AppViewModel by viewModels()
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.pager.collectLatest {
                rvAdapter.submitData(it)
            }
        }
//        viewModel.photos.observe(viewLifecycleOwner) {
//            when (it.status) {
//                Status.SUCCESS -> {
//                    dismissLoader()
//                    it.data?.let { it1 -> rvAdapter.submitData(viewLifecycleOwner.lifecycle, it1) }
//                }
//                Status.ERROR -> {
//                    dismissLoader()
//                    showSnackBar(getString(R.string.failed))
//                }
//                Status.LOADING -> {
//                    showLoader()
//                }
//            }
//        }
    }

    private fun initViews() {
        with(binding) {
            rv = recyclerView
            rv.setHasFixedSize(true)
            rv.adapter = rvAdapter.withLoadStateHeaderAndFooter(
                header = createRvLoadStateAdapter(),
                footer = createRvLoadStateAdapter()
            )
        }
    }

    private fun createRvLoadStateAdapter(): RvLoadStateAdapter {
        rvLoadStateAapter = rvLoadStateAdapterFactory.createRvLoadStateAdapter {
            rvAdapter.retry()
        }
        return rvLoadStateAapter
    }
}
