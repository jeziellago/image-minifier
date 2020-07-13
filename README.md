# Image Minifier
![](minifier_logo.png)

Minifier is a lightweight android library for image resizing, format changing and quality focusing in reduce file size.
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
