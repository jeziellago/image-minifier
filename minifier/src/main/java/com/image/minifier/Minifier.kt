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
@file:Suppress("unused")

package com.image.minifier

import android.graphics.Bitmap
import android.net.Uri
import com.image.minifier.transformation.FormatTransformation
import com.image.minifier.transformation.GrayScaleTransformation
import com.image.minifier.transformation.ImageTransformation
import com.image.minifier.transformation.QualityTransformation
import com.image.minifier.transformation.ResizeTransformation
import kotlinx.coroutines.flow.Flow
import java.io.File

interface Minifier {

    fun withImage(imageFile: File): Minifier

    fun withImage(imageUri: Uri): Minifier

    fun addTransformation(transformation: ImageTransformation): Minifier

    fun addTransformations(transformations: Minifier.() -> Unit): Minifier

    fun minify(onSuccess: (File) -> Unit, onError: (Throwable) -> Unit)

    fun minify(): Flow<File>
}

fun Minifier.resize(width: Int, height: Int) {
    addTransformation(ResizeTransformation(width, height))
}

fun Minifier.convertTo(format: Bitmap.CompressFormat) {
    addTransformation(FormatTransformation(format))
}

fun Minifier.quality(quality: Int) {
    addTransformation(QualityTransformation(quality))
}

fun Minifier.colorGrayScale() {
    addTransformation(GrayScaleTransformation())
}
