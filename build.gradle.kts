import org.gradle.kotlin.dsl.libs

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    //Dagger Hilt
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false

    // Google Services
    alias(libs.plugins.googleServices) apply false

    // Kotlin Serialization
    alias(libs.plugins.kotlinSerialization) apply false

    // Firebase Crashlytics
    id("com.google.firebase.crashlytics") version "3.0.2" apply false

    //Secrets Gradle
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false

    id("com.android.library") version "8.7.2" apply false
}


buildscript {

    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }

}