import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// androidApp/build.gradle.kts

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("org.jetbrains.compose") version "1.6.11"
    kotlin("android")
}

android {
    namespace = "dev.douglasfeitosa.kmpwheelpicker.demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.douglasfeitosa.kmpwheelpicker.demo"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
}
dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.v183)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.material3.android)
    implementation(project(":shared"))
}

// **Adicione este trecho** para que o Kotlin use JVM-1.8 também:
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}