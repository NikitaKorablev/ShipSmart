import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

android {
    namespace = "com.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        buildConfigField("String", "SB_URL", getSupabaseURL())
        buildConfigField("String", "SB_KEY", getSupabaseKey())

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
        buildConfig = true
    }
}

dependencies {
    // Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // Supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:3.1.1"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation(libs.ktor.client.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

fun getSupabaseURL(): String {
    val properties = Properties()
    file("../local.properties").inputStream().use { properties.load(it) }
    val key = properties.getProperty("SUPABASE_URL", "")
    return key
}

fun getSupabaseKey(): String {
    val properties = Properties()
    file("../local.properties").inputStream().use { properties.load(it) }
    val key = properties.getProperty("SUPABASE_KEY", "")
    return key
}