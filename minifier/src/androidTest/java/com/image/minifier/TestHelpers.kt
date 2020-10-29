package com.image.minifier

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.ByteBuffer

fun currentContext(): Context =
    InstrumentationRegistry
        .getInstrumentation()
        .targetContext

fun getFileFromResources(filename: String): File {
    return with(currentContext()){
        assets.open(filename).toFile(cacheDir.absolutePath, filename)
    }
}

fun getBitmapFromResources(filename: String): Bitmap {
    return with(currentContext()){
        val options = BitmapFactory.Options().apply {
            inPreferredConfig = Bitmap.Config.ARGB_8888
            inSampleSize = 2 // Downsample the image to avoid running out of memory on pre-API 21.
        }
        getFileFromResources(filename).getAsBitmap(options = options)
    }
}

fun assertBitmapEquals(expectedBmp: Bitmap, actualBmp: Bitmap) {
    val expected = ByteBuffer.allocate(expectedBmp.byteCount)
    expectedBmp.copyPixelsToBuffer(expected)

    val actual = ByteBuffer.allocate(actualBmp.byteCount)
    actualBmp.copyPixelsToBuffer(actual)

    assertEquals(expected, actual)
}

private fun InputStream.toFile(parentPath: String, filename: String): File {
    val file = File(parentPath, filename)
    return FileOutputStream(file).use { fos ->
        fos.write(this.readBytes())
        fos.flush()
        file
    }
}