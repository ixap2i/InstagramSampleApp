package com.ixap2i.floap

interface ImageService {
    fun sayHello(): String
}


class ImageServiceImpl(val imageRepo: ImageRepository): ImageService {
    override fun sayHello() = "Hello ${imageRepo.getHello()}"

}