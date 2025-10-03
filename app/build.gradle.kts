plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("kotlin-kapt")
}
android {
    namespace = "com.larsson.voicenote_android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.larsson.voicenote_android"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//composeOptions {
//    kotlinCompilerExtensionVersion '1.5.12' // 👈 Match with Compose Compiler version
//}

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":audioplayer"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.media3.session)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.compose.ui.text.google.fonts)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.lifecycle.extensions)

    debugImplementation(libs.androidx.lifecycle.runtime.ktx)
    debugImplementation(libs.androidx.lifecycle.viewmodel.savedstate)

    implementation(libs.accompanist.systemuicontroller)

    // DI - koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.compose)

    implementation(libs.androidx.material3)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // lottie
    implementation(libs.lottie.compose)
    // constraint layout
    implementation(libs.androidx.constraintlayout.compose)

    // Room
    //    def room_version = "2.7.1"
    implementation(libs.bundles.room)
//    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
//    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
}