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

import android.content.Context
import android.net.Uri
import androidx.annotation.WorkerThread
import com.image.minifier.transformation.ImageTransformation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import kotlin.properties.Delegates

internal class MinifierImpl internal constructor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher
) : Minifier {

    private var imageFile: File by Delegates.notNull()

    private val cacheDir: File = File("${context.cacheDir.absolutePath}/image-minifier/")
        .apply { if (!exists()) mkdirs() }

    private val transformations: MutableSet<ImageTransformation> = mutableSetOf()

    override fun withImage(imageUri: Uri) = apply {
        val imagePath: String = checkNotNull(imageUri.path) { "Uri.path is null in $imageUri" }
        imageFile = File(imagePath)
    }

    override fun withImage(imageFile: File): Minifier = apply { this.imageFile = imageFile }

    override fun addTransformations(transformations: Minifier.() -> Unit) = apply {
        apply(transformations)
    }

    override fun addTransformation(transformation: ImageTransformation) = apply {
        transformations.add(transformation)
    }

    @WorkerThread
    override fun minify(onSuccess: (File) -> Unit, onError: (Throwable) -> Unit) = try {
        onSuccess(minifyImage())
    } catch (e: Throwable) {
        onError(e)
    }

    override fun minify(): Flow<File> = flow { emit(minifyImage()) }
        .flowOn(dispatcher)

    private fun minifyImage(): File {
        require(transformations.isNotEmpty()) { "Minifier require any transformation to work." }
        var source = imageFile.copyToDir(cacheDir)

        transformations.forEachIndexed { index, transformation ->
            source = transformation.apply(source, shouldCheckForRotation = (index == 0))
        }

        return source.copyToDir(imageFile.parentFile!!).apply {
            source.delete()
            transformations.clear()
        }
    }
}
