package com.example.imgur.data.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

object ImagesPath {
    /**
     * Use content provider for get all images and return them paths (uri).
     * example path: "/storage/sdcard0/1/11.jpg"
     *
     * @return the paths of images.
     */
    @SuppressLint("Recycle")
    fun getAllImagesPath(contentResolver: ContentResolver): ArrayList<String> {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        val listOfAllImages = ArrayList<String>()
        val absolutePathOfImage = StringBuilder()
        while (cursor.moveToNext()) {
            absolutePathOfImage.append(cursor.getString(columnIndex))
            listOfAllImages.add(absolutePathOfImage.toString())
            absolutePathOfImage.delete(0, absolutePathOfImage.capacity())
        }
        return listOfAllImages
    }
}
