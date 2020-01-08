package com.example.imgur.data.helpers

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
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
        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val listOfAllImages = ArrayList<String>()
        val absolutePathOfImage = StringBuilder()

        val cursor = contentResolver
            .query(
                externalUri,
                arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DATE_MODIFIED),
                null,
                null,
                "${MediaStore.MediaColumns.DATE_MODIFIED} DESC"
            )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val uri = ContentUris.withAppendedId(externalUri, id.toLong())
                absolutePathOfImage.append(uri.toString())
                listOfAllImages.add(absolutePathOfImage.toString())
                absolutePathOfImage.delete(0, absolutePathOfImage.capacity())
            }
        }
        cursor?.close()
        return listOfAllImages
    }
}
