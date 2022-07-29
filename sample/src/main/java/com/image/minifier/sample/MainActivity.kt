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
package com.image.minifier.sample

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.image.minifier.MinifierFactory
import com.image.minifier.colorGrayScale
import com.image.minifier.getAsBitmap
import com.image.minifier.resize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val source = assets.open("android.png")
            .toFile(this.cacheDir.absolutePath, "android_original.png")

        val originalSize = source.length() / 1024

        runBlocking {
            MinifierFactory.create(this@MainActivity)
                .withImage(source)
                .addTransformations {
                    resize(1200, 1200)
                    colorGrayScale()
                }
                .minifyWith(Dispatchers.Default)
                .onSuccess { minified ->
                    findViewById<TextView>(R.id.txtDescription).text =
                        "Original: $originalSize kb\n" +
                                "After minifier: ${minified.length() / 1024} kb"
                    findViewById<ImageView>(R.id.image).setImageBitmap(minified.getAsBitmap())
                }.onFailure { findViewById<TextView>(R.id.txtDescription).text = "Error: $it" }
        }
    }

    private fun InputStream.toFile(parentPath: String, filename: String): File {
        val file = File(parentPath, filename)
        return FileOutputStream(file).use { fos ->
            fos.write(this.readBytes())
            fos.flush()
            file
        }
    }
}


