package com.image.minifier.transformation

import android.graphics.Bitmap
import com.image.minifier.FileTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FormatTransformationTest : FileTest() {

    @Test
    fun withOriginalImg_ShouldChangeFormatToJPEG() {
        // Given
        val formatTransformation = FormatTransformation(Bitmap.CompressFormat.JPEG)

        // When
        val formattedFile = formatTransformation.apply(originalFile)

        // Then
        assertEquals("jpg", formattedFile.name.substringAfter("."))
    }
}