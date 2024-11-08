import org.gradle.api.JavaVersion.VERSION_11
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //Dagger Hilt
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)

    // Google Services
    alias(libs.plugins.googleServices)

    id("com.google.firebase.crashlytics")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")


}

android {
    namespace = "com.tp.firebase.tp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tp.firebase.tp"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val properties = Properties()
        properties.load(FileInputStream(rootProject.file("local.properties")))
        buildConfigField(
            "String",
            "API_KEY",
            properties.getProperty("API_KEY")
        )
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
        sourceCompatibility = VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

}

dependencies {

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

    //Gemini AI
    implementation(libs.generativeai)

    //Dagger Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)


    //Secrets Gradle
    implementation(libs.secrets.gradle.plugin)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    testImplementation(libs.room.testing)
    ksp(libs.room.compiler)

    // Lottie
    implementation(libs.lottie.compose)

    // Coil
    implementation(libs.coil.compose)

    // Retrofit
//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(libs.converter.gson)

    // Material extend icons
    implementation(libs.androidx.material.icons.extended)

}