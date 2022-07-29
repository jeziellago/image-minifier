# Image Minifier
![CI](https://github.com/jeziellago/image-minifier/workflows/CI/badge.svg?branch=master)

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
    .minifyWith(Dispatchers.Default)
    .onSuccess { minified -> /* success */ }
    .onFailure { error -> /* failure */ }
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
    implementation 'com.github.jeziellago:image-minifier:0.2.0'
}
```
