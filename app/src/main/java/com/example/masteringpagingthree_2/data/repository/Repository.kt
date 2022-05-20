package com.example.masteringpagingthree_2.data.repository

import com.example.masteringpagingthree_2.data.remote.apiService.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService)
