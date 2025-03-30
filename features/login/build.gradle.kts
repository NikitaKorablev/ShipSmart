plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
}

android {
    namespace = "com.shipsmartapp.login"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.designSystem)

    // Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
//    implementation(libs.androidx.activity)
    implementation("androidx.activity:activity-ktx:1.10.1")
//    implementation(libs.androidx.activity.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}