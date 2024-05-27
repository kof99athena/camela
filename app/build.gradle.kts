plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.anehta.camela"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.anehta.camela"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    val fragment_version = "1.7.0"
    val camerax_version = "1.4.0-alpha05"
    val hilt_version = "2.50"
    val jUnit_version = "4.13.2"
    val androidXTest_version = "1.5.0"
    val mockito_version = "5.12.0"
    val mockitoKotlin_version = "3.2.0"

    // Kotlin - Fragment
    implementation("androidx.fragment:fragment-ktx:$fragment_version")

    //hilt
    implementation("com.google.dagger:hilt-android:${hilt_version}")
    kapt("com.google.dagger:hilt-android-compiler:${hilt_version}")
    //implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    //Junit
    // Required -- JUnit 4 framework
    testImplementation("junit:junit:$jUnit_version")
    // Optional -- Robolectric environment
    testImplementation("androidx.test:core:$androidXTest_version")
    // Optional -- Mockito framework
    testImplementation("org.mockito:mockito-core:$mockito_version")
    // Optional -- mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlin_version")
    // Optional -- Mockk framework
    testImplementation("io.mockk:mockk:$mockito_version")


    //camera
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-video:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}")
    implementation("androidx.camera:camera-extensions:${camerax_version}")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}