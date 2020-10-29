package com.image.minifier.transformation

import com.image.minifier.FileTest
import com.image.minifier.assertBitmapEquals
import com.image.minifier.getAsBitmap
import com.image.minifier.getBitmapFromResources
import org.junit.Test

class GrayScaleTransformationTest : FileTest() {

    @Test
    fun withOriginalImg_ShouldChangeToGrayscale() {
        // Given
        val transformation = GrayScaleTransformation()
        val expectedGrayscaleBmp = getBitmapFromResources("grayscale.png")

        // When
        val resultFile = transformation.apply(originalFile)

        val grayScaleBmp = resultFile.getAsBitmap()

        // Then
        assertBitmapEquals(expectedGrayscaleBmp, grayScaleBmp)
    }
}