package com.ixap2i.floap

import com.facebook.internal.ImageResponse

class ImageStore(dispatcher: Dispatcher) {
    val image: (suspend (ImageResponse) -> Unit) = {
//        dispatcher.dispatch(ImageLoaded(it))
    }
}

interface ImageService {
    fun getCode(): String

    fun getAccessKeys(): String
}


class ImageServiceImpl(val imageRepo: ImageRepository): ImageService {

    override fun getCode(): String {
        return "hoge"
    }

    override fun getAccessKeys(): String {
        // アクセスキーだけを抽出するイメージ
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}