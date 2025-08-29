plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
    id("com.google.dagger.hilt.android") version "2.57.1"
}

android {
    namespace = "com.sdk.todoapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sdk.todoapp"
        minSdk = 26
        targetSdk = 36
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    ksp("com.google.dagger:hilt-compiler:2.57.1")
    implementation ("com.google.dagger:hilt-android:2.57.1")
    kapt ("com.google.dagger:hilt-compiler:2.57.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation ("androidx.room:room-runtime:2.7.2")
    implementation ("androidx.room:room-ktx:2.7.2")
    kapt ("androidx.room:room-compiler:2.7.2")
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.9.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}