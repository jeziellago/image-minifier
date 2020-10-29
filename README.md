# Image Minifier
![CI](https://github.com/jeziellago/image-minifier/workflows/CI/badge.svg?branch=master)  [![Codacy Badge](https://app.codacy.com/project/badge/Grade/fb95312d03504525b38f4789a3ae58d3)](https://www.codacy.com/gh/jeziellago/image-minifier/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jeziellago/image-minifier&amp;utm_campaign=Badge_Grade)
![](minifier_logo.png)

Minifier is a lightweight (**21KB**) android library for image resizing, format changing and quality focusing in reduce file size.
## How it works?
With an image file, apply one or multiples transformations:
```kotlin
MinifierFactory.create(context)
    .withImage(originalFile)
    .addTransformations {
        resize(1200, 720)
        convertTo(CompressFormat.JPEG)
    }
    .minify {
        onSuccess { minified -> /* success */ }
        onFailure { error -> /* failure */ }
    }
        
```
or use coroutines:
```kotlin
val minifiedFile: File = MinifierFactory.create(context)
    .withImage(originalFile)
    .addTransformations {
        resize(1200, 720)
        convertTo(CompressFormat.JPEG)
    }
    .minify(Dispatchers.IO)
```
## Transformations
- Resize
```kotlin
resize(1200, 720)
```
- Format
```kotlin
convertTo(CompressFormat.JPEG)
```
- Gray scale
```kotlin
colorGrayScale()
```
- Quality
```kotlin
quality(80)
```
## Dependencies
- Project `build.gradle`
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
- Module `build.gradle`
```
dependencies {
    implementation 'com.github.jeziellago:image-minifier:0.1.1'
}
```
