// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath (libs.hilt.android.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("com.google.firebase.crashlytics") version "3.0.3" apply false
    id("androidx.room") version "2.7.1" apply false
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
}