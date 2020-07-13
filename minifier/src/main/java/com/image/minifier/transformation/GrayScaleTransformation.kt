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
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import com.image.minifier.getAsBitmap
import com.image.minifier.getExtensionAsBitmapFormat
import java.io.File

class GrayScaleTransformation : ImageTransformation {

    override fun apply(source: File, shouldCheckForRotation: Boolean): File {
        val sourceBitmap: Bitmap = source.getAsBitmap(shouldCheckForRotation)

        val saturation = ColorMatrix().apply { setSaturation(0f) }
        val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(saturation) }

        val bmp = Bitmap.createBitmap(sourceBitmap.width, sourceBitmap.height, sourceBitmap.config)

        Canvas(bmp).drawBitmap(sourceBitmap, 0f, 0f, paint)

        sourceBitmap.recycle()

        return source.apply {
            outputStream().use { fos ->
                with(bmp) {
                    compress(getExtensionAsBitmapFormat(), 100, fos)
                    recycle()
                }
            }
        }
    }
}
