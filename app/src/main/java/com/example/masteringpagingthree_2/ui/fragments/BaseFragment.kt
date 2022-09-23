package com.example.masteringpagingthree_2.ui.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.masteringpagingthree_2.util.AppConstants.TAG_PROGRESS_LOADER
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    @Inject
    lateinit var loaderFragment: LoaderFragment

    fun showLoader() {
        loaderFragment.show(requireActivity().supportFragmentManager, TAG_PROGRESS_LOADER)
    }

    fun dismissLoader() {
        loaderFragment.dismiss()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.ANIMATION_MODE_SLIDE).show()
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
