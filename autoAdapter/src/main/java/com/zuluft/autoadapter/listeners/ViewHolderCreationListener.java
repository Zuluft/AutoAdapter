package com.zuluft.autoadapter.listeners;

import android.support.annotation.NonNull;

import com.zuluft.autoadapter.renderables.AutoViewHolder;

public interface ViewHolderCreationListener {

    void onViewHolderCreated(@NonNull AutoViewHolder autoViewHolder);
}
