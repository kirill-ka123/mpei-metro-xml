plugins {
    androidApplication
    kotlinAndroid
    kapt
    kotlinParcelize
}

android {
    namespace = ConfigData.packageName
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = ConfigData.packageName
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.lifecycleRuntime)
    implementation(Dependencies.AndroidX.lifecycleLivedata)
    implementation(Dependencies.AndroidX.lifecycleViewModel)
    implementation(Dependencies.AndroidX.navigationFragment)
    implementation(Dependencies.AndroidX.navigationUi)
    implementation(Dependencies.AndroidX.security)

    implementation(Dependencies.AndroidX.room)
    implementation(Dependencies.AndroidX.roomKtx)
    kapt(Dependencies.AndroidX.roomCompiler)

    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.daggerCompiler)

    implementation(Dependencies.Gson.gson)

    testImplementation(Dependencies.junit)
}