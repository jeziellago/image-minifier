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
package com.image.minifier.transformation

import android.graphics.Bitmap
import com.image.minifier.getAsBitmap
import com.image.minifier.getExtensionAsBitmapFormat
import java.io.File

class ResizeTransformation(
    private val width: Int,
    private val height: Int
) : ImageTransformation {

    override fun apply(source: File, shouldCheckForRotation: Boolean): File {
        val sourceBitmap: Bitmap = source.getAsBitmap(shouldCheckForRotation)
        val resizedBmp = createBitmap(sourceBitmap)

        return source.apply {
            outputStream().use { fos ->
                with(resizedBmp) {
                    compress(getExtensionAsBitmapFormat(), 100, fos)
                    recycle()
                }
            }
        }
    }

    private fun createBitmap(sourceBitmap: Bitmap): Bitmap {
        return if (width < sourceBitmap.width || height < sourceBitmap.height) {

            val (newWidth, newHeight) = if (width < height) {
                width to calculateScaledSize(sourceBitmap.width, width)
            } else {
                calculateScaledSize(sourceBitmap.height, height) to height
            }

            Bitmap.createScaledBitmap(sourceBitmap, newWidth, newHeight, false)
                .apply { sourceBitmap.recycle() }
        } else {
            sourceBitmap
        }
    }

    private fun calculateScaledSize(original: Int, target: Int): Int {
        return ((target.toFloat() / original.toFloat()) * original).toInt()
    }
}
