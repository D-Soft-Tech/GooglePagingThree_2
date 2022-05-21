package com.example.masteringpagingthree_2.ui.adapters

import dagger.assisted.AssistedFactory

@AssistedFactory
interface RvLoadStateAdapterFactory {
    fun createRvLoadStateAdapter(
        callBack: retryCallBack
    ): RvLoadStateAdapter
}
