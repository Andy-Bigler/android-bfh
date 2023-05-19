# Mini Project Library

<img src="./assets/logo.png" height=150px>

## APK

Currently, the release build is not working.
Please get the APK from here **20_Library/app/release/app-release.apk**

## Signing

The packages are signed only with the **Signature Scheme V2** and not with the scheme v1.
To verify the APK signature, you have to use the `apksigner` binary instead of `jarsigner` binary.

Verify signature:

```bash
apksigner verify --verbose app-release.apk
```

## How to use

### Export and Import data

You can use the provided test data file here if you want:
**20_Library/app/src/main/java/com/example/library/testData/books.json**

Just put it somewhere on your phone and import it from the app.

<img src="./assets/export_import.jpg" height=300px>

### Different tabs depending on book status

<img src="./assets/reading.jpg" height=300px>
<img src="./assets/read.jpg" height=300px>
<img src="./assets/want_to_read.jpg" height=300px>

### Add a new book

<img src="./assets/add.jpg" height=300px>

### Edit an existing book

<img src="./assets/edit.jpg" height=300px>

## Disclaimer

The data flow of this application is horrible I know.
I just wanted to do something as simple as possible.
In the future I would like to extend this and switch to an API.
Also, I need to learn quite a bit of Android UI logic and best practices.