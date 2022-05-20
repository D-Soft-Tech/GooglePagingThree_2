package com.example.masteringpagingthree_2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.masteringpagingthree_2.R
import com.example.masteringpagingthree_2.databinding.FragmentGalleryBinding
import com.example.masteringpagingthree_2.ui.adapters.UnsplashPhotoAdapters
import com.example.masteringpagingthree_2.ui.viewModel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment @Inject constructor() : Fragment() {
    @Inject
    lateinit var rvAdapter: UnsplashPhotoAdapters
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
        viewModel.photos.observe(viewLifecycleOwner) {
            rvAdapter.submitData(
                viewLifecycleOwner.lifecycle, it
            )
        }
    }

    private fun initViews() {
        with(binding) {
            rv = recyclerView
            rv.setHasFixedSize(true)
            rv.adapter = rvAdapter
        }
    }
}
