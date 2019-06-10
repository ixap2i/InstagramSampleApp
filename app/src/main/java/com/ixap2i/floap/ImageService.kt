package com.ixap2i.floap

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