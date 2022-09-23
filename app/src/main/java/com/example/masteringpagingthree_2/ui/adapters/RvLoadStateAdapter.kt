package com.example.masteringpagingthree_2.ui.adapters // ktlint-disable package-name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.masteringpagingthree_2.databinding.HeaderAndFooterItemLayoutBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

typealias retryCallBack = () -> Unit

class RvLoadStateAdapter @AssistedInject constructor(@Assisted private val callBack: retryCallBack) :
    LoadStateAdapter<RvLoadStateAdapter.RvHeaderAndFooterViewHolder>() {

    inner class RvHeaderAndFooterViewHolder(private val binding: HeaderAndFooterItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener {
                callBack.invoke()
            }
        }

        fun bind(currentLoadState: LoadState) {
            binding.apply {
                progressBar2.isVisible = currentLoadState is LoadState.Loading
                retryBtn.isVisible = currentLoadState !is LoadState.Loading && currentLoadState !is LoadState.NotLoading
                textView2.isVisible = currentLoadState !is LoadState.Loading
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RvHeaderAndFooterViewHolder =
        RvHeaderAndFooterViewHolder(
            HeaderAndFooterItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RvHeaderAndFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}
