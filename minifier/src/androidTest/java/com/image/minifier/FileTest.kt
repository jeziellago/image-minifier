package com.image.minifier

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File

open class FileTest {

    @get:Rule
    val fileRule = TemporaryFolder()

    lateinit var originalFile: File

    @Before
    fun setup() {
        val originalImg = getFileFromResources("original_img.png")
        originalFile = fileRule.newFile("ORIGINAL_TEST_FILE.png")
        originalImg.copyTo(originalFile, overwrite = true)
    }

    @After
    fun tearDown() {
        fileRule.delete()
    }
}