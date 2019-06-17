package com.ixap2i.floap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ImageListViewModel(application: Application, val imgRepo: ImageRepository) : AndroidViewModel(application) {

    override fun <T : Application> getApplication(): T {
        return super.getApplication()
    }

    fun getImageResponse() {
        viewModelScope.launch {
            val result = imgRepo.getUserImage()
        }
    }
}
