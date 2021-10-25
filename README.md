# Playcraft

*Playcraft* is a Kotlin library for streaming video playback.

- [Getting Started](#GettingStarted)
    - [Android App Demo](#AndroidAppDemo)
    - [Android TV Demo](#AndroidTVDemo)



## Getting Started
1. Download this repository.
2. Drag all the aar files under folder *sdk* into your project's libs/.

   ![](https://i.imgur.com/ZcqPNl5.png)

3. Implement all aar files in libs/. in your build.gradle
   ```kotlin
   dependencies {
       ....

       // Include player library (aar in libs)
       implementation fileTree(dir: 'libs', include: ['*.jar', "*.aar"])

       // Player dependency library
       implementation "com.google.android.material:material:1.4.0"
       implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
       implementation "com.squareup.retrofit2:retrofit:2.6.4"
       implementation "com.squareup.retrofit2:converter-gson:2.6.4"
       implementation "com.google.code.gson:gson:2.8.6"
       implementation "com.google.android.gms:play-services-cast-framework:20.0.0"
       implementation "com.squareup.okhttp3:okhttp:4.5.0"
       implementation "com.google.ads.interactivemedia.v3:interactivemedia:3.24.0"
       implementation "com.github.bumptech.glide:glide:4.12.0"
       kapt "com.github.bumptech.glide:compiler:4.12.0"
       implementation "io.reactivex.rxjava2:rxjava:2.2.15"
       implementation "io.reactivex.rxjava2:rxandroid:2.1.1"
       implementation "io.reactivex.rxjava2:rxkotlin:2.3.0"
       implementation "androidx.room:room-runtime:2.3.0"
       kapt "androidx.room:room-compiler:2.3.0"
   }
 
   ```

3. Add compile options in your build.gradle
   ```kotlin
   plugins {
       ...
       id 'kotlin-kapt'
   }

   android {
      ...
      compileOptions {
           sourceCompatibility JavaVersion.VERSION_1_8
           targetCompatibility JavaVersion.VERSION_1_8
       }

       kotlinOptions {
           jvmTarget = '1.8'
       }

       packagingOptions {
           exclude 'META-INF/*.kotlin_module'
       }
   }
    ```

4. Add permission in AndroidManifest.xml.
   ```xml =
   <manifest xmlns:android="http://schemas.android.com/apk/res/android"
       package="...">

       <uses-permission android:name="android.permission.INTERNET" />
   ```

## Android App Demo

- Add PaaSView in your layout.
   ```xml
    <androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="<http://schemas.android.com/apk/res/android>"
    xmlns:app="<http://schemas.android.com/apk/res-auto>"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <com.kkstream.android.paas.player.common.service.PaaSView
        android:id="@+id/PaaSView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent" />
   </androidx.constraintlayout.widget.ConstraintLayout>
   ```
- Prepare PaaSParameter and set to PaaSView

   ```kotlin
    val paramData = PaaSParameter(
        hostUrl = "${VIDEO_HOST_SERVER_URL_PATH}",
        contentId = "${VIDEO_CONTENT_ID}",
        contentType = VIDEO_CONTENT_TYPE,
        accessToken = "${ACCRSS_TOKEN}",
        deviceId = InjectorUtils.provideDeviceId(context),
        customHeader = mapOf("key","value")
    )

    try {
        paasProvider = PaaSView.setup(
            paasParameter = paramData,
            lifecycle = lifecycle
        )
    } catch (e: PaaSErrorEvent) {
        Log.e(TAG, e.getErrorMessage(requireContext()))
    }
   ```

   More description about parameters:

   - `hostUrl`: The host url, the player will auto verify that the url is available or not.
   - `contentId`: The id is unique and it is used to fetch content data and related resources
   - `contentType`: An enum(ContentType) value that includes Videos, Lives and Offline
   - `accessToken`: Authorization token
   - `deviceId`: Settings.Secure.ANDROID_ID
   - `customHeader`: To identify the device type or playback situation. This is depend on each project


## Android TV Demo 
- Add fragment in your layout.
   ```xml
   <fragment
        android:id="@+id/paasTVFragment"
        android:name="com.kkstream.android.paas.player.stb.PaasTVFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    ```
- Using PaasTVFragment to show player and play video in your FragmentActivity 
   ```kotlin
   // Sample code on onCreate() lifecycle

   // 1. Got paasTVFragment from xml or you can add it to your FragmentActivity.
   paasTVFragment = supportFragmentManager.findFragmentById(R.id.paasTVFragment) as PaasTVFragment

   // 2. Setup parameter to play video.
   paasTVFragment.setup(paramData, lifecycle)

   // 3. Setup progress bar and focus color for showing.
   paasTVFragment.setPaasThemeColorStyle(R.style.UpdateCustomizeIconColor)
   ```

   Note color style need to have paasThemeColor
   ```xml
    <style name="UpdateCustomizeIconColor">
        <item name="paasThemeColor">@color/paas_tv_theme_color</item>
    </style>
    ```

- Bypassing key event to PaasTVFragment in your FragmentActivity 
   ```kotlin
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if (isFragmentHandleOnKeyUpEvent(keyCode, event)) {
            // If isFragmentHandleOnKeyUpEvent return true, it means we handle the onKeyUp event.
            // So we return true to tell parent we has handled it.
            true
        } else {
            super.onKeyUp(keyCode, event)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (isFragmentHandleOnKeyDownEvent(keyCode, event)) {
            // If isFragmentHandleOnKeyUpEvent return true, it means we handle the onKeyUp event.
            // So we return true to tell parent we has handled it.
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    /**
     * For bypassing key event to paasTVFragment.
     */
    private fun isFragmentHandleOnKeyUpEvent(keyCode: Int, event: KeyEvent?): Boolean {
        return (paasTVFragment as? CustomPaaSTVFragmentInterface)?.onKeyUp(keyCode, event, null)
            ?: false
    }

    /**
     * For bypassing key event to paasTVFragment.
     */
    private fun isFragmentHandleOnKeyDownEvent(keyCode: Int, event: KeyEvent?): Boolean {
        return (paasTVFragment as? CustomPaaSTVFragmentInterface)?.onKeyDown(keyCode, event, null)
            ?: false
    }
  ```

