package com.ixap2i.floap

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ImageListViewModel(application: Application) : AndroidViewModel(application) {

    override fun <T : Application> getApplication(): T {
        return super.getApplication()
    }
}