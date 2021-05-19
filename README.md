# Playcraft

*Playcraft* is a Kotlin library for streaming video playback.


## Getting Started

1. Download this repository.
2. Drag all the files under folder *sdk* into your project's libs/.
3. Implement all AAR files in libs/.
4. Prepare PaaSparameter and set it into PaaSView

```
    val kksPlayerServiceParamData = PaaSParameter(
        hostUrl = "${VIDEO_HOST_SERVER_URL_PATH}",
        contentId = "${VIDEO_CONTENT_ID}",
        contentType = VIDEO_CONTENT_TYPE,
        accessToken = "${ACCRSS_TOKEN}",
        deviceId = InjectorUtils.provideDeviceId(context),
    )

    try {
        paasProvider = kksPlayerView.setup(
            paasParameter = kksPlayerServiceParamData,
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

