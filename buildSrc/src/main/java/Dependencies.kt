object VersionsDigest {
    const val junit = "4.13"

    const val gson = "2.8.6"

    const val retrofit = "2.9.0"

    const val okhttp = "4.12.0"

    const val worker = "2.9.0"

    /**
     * [Documentation](https://developer.android.com/jetpack/androidx)
     *
     * Актуальные версии для всех пакетов AndroidX можно посмотреть
     * [здесь](https://developer.android.com/jetpack/androidx/versions).
     *
     * Недавние релизы можно посмотреть [здесь](https://developer.android.com/jetpack/androidx/versions/stable-channel).
     */
    object AndroidX {

        const val core = "1.13.1"

        const val appcompat = "1.6.1"

        const val acitivity = "1.8.0"

        const val material = "1.12.0"

        const val constraintLayout = "2.1.4"

        const val lifecycle = "2.6.2"

        const val navigation = "2.7.6"

        const val security = "1.0.0-alpha02"

        const val room = "2.6.0"
    }

    object Dagger {
        const val dagger = "2.48"
        const val daggerCompiler = "2.48"
    }
}

object Dependencies {

    const val junit = "junit:junit:${VersionsDigest.junit}"
    const val gson = "com.google.code.gson:gson:${VersionsDigest.gson}"
    const val worker = "com.squareup.okhttp3:logging-interceptor:${VersionsDigest.okhttp}"

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${VersionsDigest.AndroidX.core}"
        const val appcompat = "androidx.appcompat:appcompat:${VersionsDigest.AndroidX.appcompat}"
        const val acitivity = "androidx.activity:activity:${VersionsDigest.AndroidX.acitivity}"
        const val material =
            "com.google.android.material:material:${VersionsDigest.AndroidX.material}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${VersionsDigest.AndroidX.constraintLayout}"
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime-ktx:${VersionsDigest.AndroidX.lifecycle}"
        const val lifecycleLivedata =
            "androidx.lifecycle:lifecycle-livedata-ktx:${VersionsDigest.AndroidX.lifecycle}"
        const val lifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${VersionsDigest.AndroidX.lifecycle}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${VersionsDigest.AndroidX.navigation}"
        const val navigationUi =
            "androidx.navigation:navigation-ui-ktx:${VersionsDigest.AndroidX.navigation}"
        const val security =
            "androidx.security:security-crypto:${VersionsDigest.AndroidX.security}"
        const val room = "androidx.room:room-runtime:${VersionsDigest.AndroidX.room}"
        const val roomKtx = "androidx.room:room-ktx:${VersionsDigest.AndroidX.room}"
        const val roomCompiler = "androidx.room:room-compiler:${VersionsDigest.AndroidX.room}"
    }

    object Dagger {
        const val dagger = "com.google.dagger:dagger:${VersionsDigest.Dagger.dagger}"
        const val daggerCompiler =
            "com.google.dagger:dagger-compiler:${VersionsDigest.Dagger.daggerCompiler}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${VersionsDigest.retrofit}"
        const val converter = "com.squareup.retrofit2:converter-gson:${VersionsDigest.retrofit}"
    }

    object Okhttp {
        const val logginInterceptor = "com.squareup.okhttp3:logging-interceptor:${VersionsDigest.okhttp}"
    }
}
