package com.example.masteringpagingthree_2.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.masteringpagingthree_2.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: Repository): ViewModel() {
}