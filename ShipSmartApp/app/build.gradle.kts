plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
    id("kotlin-kapt")
}

android {
    namespace = "com.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        project.ext.set("archivesBaseName", "ShipSmart-" + defaultConfig.versionName);
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.designSystem)
    implementation(projects.core.utils)
    implementation(projects.core.dbNetwork)
    implementation(projects.core.deliveryNetwork)
    implementation(projects.features.login)
    implementation(projects.features.packageSizeCollector)
    implementation(projects.features.deliveryChoosing)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout.v214)
    implementation(libs.material)

    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)

    // Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.constraintlayout)
}