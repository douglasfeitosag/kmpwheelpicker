import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("maven-publish")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "dev.douglasfeitosa.kmpwheelpicker"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
}

publishing {
    publications {
        withType<MavenPublication> {
            groupId = "io.github.SEU_USUARIO"
            artifactId = "kmpwheelpicker"
            version = "0.1.0"
        }
    }
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