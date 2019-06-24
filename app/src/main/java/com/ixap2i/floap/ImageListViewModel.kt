package com.ixap2i.floap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ImageListViewModel(val imgRepo: ImageResponceFactory) : ViewModel() {
    private val liveDataImg = object: LiveData<Data>(){}
}
