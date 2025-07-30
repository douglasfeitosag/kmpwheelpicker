import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinNativeCocoapods)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("org.jetbrains.compose") version "1.6.11"
    id("maven-publish")
}

kotlin {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    cocoapods {
        summary = "KMP Wheel Picker"
        homepage = "https://github.com/douglasfeitosag/kmpwheelpicker"
        ios.deploymentTarget = "15.2"
        version = "0.1.0"
        framework {
            baseName = "Shared"
            isStatic = true
        }
        extraSpecAttributes["source_files"] = "'src/iosMain/objc/**/*.{h,m}'"
        extraSpecAttributes["public_header_files"] = "'src/iosMain/objc/*.h'"
    }

    androidTarget()

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "Shared"
            xcf.add(this)
            isStatic = true
        }
        it.compilations["main"].cinterops.create("WheelPickerView") {
            defFile(project.file("src/iosMain/cinterop/wheelPickerView.def"))
            packageName("dev.douglasfeitosa.kmpwheelpicker")
            includeDirs {
                allHeaders(project.file("src/iosMain/objc"))
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.ui)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "dev.douglasfeitosa.kmpwheelpicker"
    compileSdk = 35
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.activity.ktx)
}

publishing {
    publications {
        withType<MavenPublication> {
            groupId = "io.github.douglasfeitosag"
            artifactId = "kmpwheelpicker"
            version = "0.1.0"
        }
    }
}

tasks {
    withType<CInteropProcess>().configureEach {
        dependsOn("generateDummyFramework", "podspec", "generateComposeResClass")
    }
}
