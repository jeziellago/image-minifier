package com.image.minifier

import android.graphics.Bitmap
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File

class MinifierTest : FileTest() {

    @Test
    fun withTransformations_ShouldCreateBmp_whenReceiveGrayscaleAndResizeAndFormatTransformations() {
        // Given
        val expectedBitmap = getBitmapFromResources("grayscale_50_50.jpg")
        var result: Any? = null

        // When
        MinifierFactory.create(currentContext())
            .withImage(originalFile)
            .addTransformations {
                resize(50, 50)
                colorGrayScale()
                convertTo(Bitmap.CompressFormat.JPEG)
            }.minify {
                onSuccess { result = it }
            }

        // Then
        assertNotNull(result)
        val bmp = (result as File).getAsBitmap()
        assertBitmapEquals(expectedBitmap, bmp)
    }
}