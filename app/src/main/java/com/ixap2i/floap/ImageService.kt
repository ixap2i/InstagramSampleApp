package com.ixap2i.floap

interface ImageService {
    fun sayHello(): String

    fun getAccessKeys(): String
}


class ImageServiceImpl(val imageRepo: ImageRepository): ImageService {
    override fun getAccessKeys(): String {
        // アクセスキーだけを抽出するイメージ
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sayHello() = "Hello ${imageRepo.getHello()}"

}