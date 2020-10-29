/*
 * Copyright 2020 Jeziel Lago.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.image.minifier

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.File
import java.io.FileOutputStream

internal fun Bitmap.CompressFormat.toFileExtension() = when (this) {
    Bitmap.CompressFormat.PNG -> ".png"
    Bitmap.CompressFormat.WEBP -> ".webp"
    Bitmap.CompressFormat.JPEG -> ".jpg"
}

internal fun File.getExtensionAsBitmapFormat(): Bitmap.CompressFormat {
    return when (this.extension) {
        "png" -> Bitmap.CompressFormat.PNG
        "webp" -> Bitmap.CompressFormat.WEBP
        "jpeg", "jpg" -> Bitmap.CompressFormat.JPEG
        else -> throw IllegalArgumentException("Invalid extension to Bitmap: ${this.extension}")
    }
}

internal fun File.copyToDir(parentDir: File): File {
    val newFile = File(parentDir.absolutePath, this.name)
    return FileOutputStream(newFile).use { fos ->
        fos.write(this.inputStream().readBytes())
        fos.flush()
        newFile
    }
}

fun File.getAsBitmap(
    shouldAdjustRotation: Boolean = false,
    options: BitmapFactory.Options? = null
): Bitmap {

    val bitmap = BitmapFactory.decodeFile(this.absolutePath, options)

    return if (shouldAdjustRotation) {
        val exif = ExifInterface(this.absolutePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
        val matrix = Matrix().apply {
            when (orientation) {
                6 -> postRotate(90f)
                3 -> postRotate(180f)
                8 -> postRotate(270f)
            }
        }
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    } else {
        bitmap
    }
}
