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
import com.image.minifier.toFileExtension
import java.io.File

class FormatTransformation(private val format: Bitmap.CompressFormat) : ImageTransformation {

    override fun apply(source: File, shouldCheckForRotation: Boolean): File {
        return source.convertPathToNewFormat(format).apply {
            outputStream().use { fos ->
                with(source.getAsBitmap(shouldCheckForRotation)) {
                    compress(format, 100, fos)
                    recycle()
                }
            }
        }
    }

    private fun File.convertPathToNewFormat(format: Bitmap.CompressFormat) =
        File(absolutePath.substringBeforeLast(".") + format.toFileExtension())

}
