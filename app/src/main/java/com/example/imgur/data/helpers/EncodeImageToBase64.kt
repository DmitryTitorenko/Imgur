package com.example.imgur.data.helpers

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

object EncodeImageToBase64 {

    /**
     * Convert the selected image to base64 encoded string.
     *
     * @param bm image to convert.
     * @return base64 code in string.
     */
    fun encode(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}