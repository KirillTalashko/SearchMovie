plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.searchmovie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.searchmovie"
        minSdk = 24
        targetSdk = 34
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.process)

    //ShimmerFrameLayout
    implementation(libs.shimmer)

    // Dagger 2
    kapt(libs.dagger.compiler)
    implementation(libs.dagger)

    // SwipeRefreshLayout
    implementation(libs.androidx.swiperefreshlayout)

    // WorkManager
    implementation(libs.androidx.work.runtime)

    // WorkManager Coroutines
    implementation(libs.androidx.work.runtime.ktx)

    // network
    implementation(project(":network"))

    // database
    implementation(project(":database"))

    // common
    implementation(project(":common"))
}
