package com.example.music.ui.bindadapter;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create By morningsun  on 2019-11-21
 */
public class BindingViewHolder <T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private T mBinding;
    public BindingViewHolder(@NonNull T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
    public T getBinding() {
        return mBinding;
    }
}
