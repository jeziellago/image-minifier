package com.image.minifier.transformation

import com.image.minifier.FileTest
import com.image.minifier.getAsBitmap
import org.junit.Assert.assertEquals
import org.junit.Test

class ResizeTransformationTest : FileTest() {

    @Test
    fun withOriginalImg_ShouldChangeSizeTo50x50() {
        // Given
        val width = 50
        val height = 50
        val transformation = ResizeTransformation(width, height)

        // When
        val resizedFile = transformation.apply(originalFile)

        val resizedBmp = resizedFile.getAsBitmap()
        resizedBmp.recycle()
        // Then
        assertEquals("Img width changed", width, resizedBmp.width)
        assertEquals("Img height changed", height, resizedBmp.height)
    }
}