# Image Minifier

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/dc71549baf8f479b8586ef00a98b5fd4)](https://app.codacy.com/gh/jeziellago/image-minifier?utm_source=github.com&utm_medium=referral&utm_content=jeziellago/image-minifier&utm_campaign=Badge_Grade)

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
    .minify({ minified ->
        /* success */
    }, { error ->
        /* error */
    })
        
```
### Using Kotlin Flow
```kotlin
MinifierFactory.create(context)
    .withImage(originalFile)
    .addTransformations {
        resize(1200, 720)
        convertTo(CompressFormat.JPEG)
    }
    .minify()
    .catch { /* error */ }
    .collect { minified -> 
       /* success */
    }
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
    implementation 'com.github.jeziellago:image-minifier:0.1.0'
}
```
