package com.ixap2i.floap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ImageListViewModel(application: Application) : AndroidViewModel(application) {

//    private val imageListObserval: LiveData<List<ImageRepository>>
//    = ImageRepository().getInstance()

    override fun <T : Application> getApplication(): T {
        return super.getApplication()
    }
}