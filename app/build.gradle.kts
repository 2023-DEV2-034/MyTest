plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
        .version(libs.versions.ksp.version.get())
}

android {
    namespace = "dev.test.tictactoe"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.test.tictactoe"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Modules
    implementation(project(":feature:game"))

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.graphics)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    // Architecture components
    implementation(libs.lifecycle.runtime.ktx)
    ksp(libs.lifecycle.compiler)

    // Coroutines
    implementation(platform(libs.kotlinx.coroutines.bom))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Hilt Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
    debugImplementation(libs.compose.ui.tooling)
    testImplementation(platform(libs.kotlinx.coroutines.bom))
    testImplementation(libs.kotlinx.coroutines.test)

    // Detekt
    detekt(libs.detekt.cli)

    // Miscellaneous
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}
