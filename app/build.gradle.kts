plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.tiendaza"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tiendaza"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }

        // Habilitar JUnit 5 para tests unitarios
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {

    // ====================================================================
    // CORE ANDROID
    // ====================================================================
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // ====================================================================
    // JETPACK COMPOSE
    // ====================================================================
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Debug tools para Compose
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // ====================================================================
    // NAVIGATION
    // ====================================================================
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ====================================================================
    // VIEWMODEL & LIFECYCLE
    // ====================================================================
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // ====================================================================
    // NETWORKING - RETROFIT & OKHTTP
    // ====================================================================
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ====================================================================
    // JSON SERIALIZATION
    // ====================================================================
    implementation("com.google.code.gson:gson:2.10.1")

    // ====================================================================
    // COROUTINES
    // ====================================================================
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // ====================================================================
    // IMAGE LOADING
    // ====================================================================
    implementation("io.coil-kt:coil-compose:2.6.0")

    // ====================================================================
    // DATA PERSISTENCE
    // ====================================================================
    implementation("androidx.datastore:datastore-preferences:1.0.0")


    // ====================================================================
    // TESTING - UNIT TESTS (test/)
    // ====================================================================

    // Kotest Framework
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-property:5.9.1")

    // JUnit 5 (Jupiter)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")

    // MockK para mocking
    testImplementation("io.mockk:mockk:1.13.12")

    // Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")

    // Architecture Components Testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Turbine - Testing de Flows
    testImplementation("app.cash.turbine:turbine:1.1.0")


    // ====================================================================
    // TESTING - INSTRUMENTED TESTS (androidTest/)
    // ====================================================================

    // JUnit 4 (requerido para Android Instrumented Tests)
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")

    // Android Test Runner & Rules
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")

    // Espresso para UI testing
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose UI Testing
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // MockK para Android Tests
    androidTestImplementation("io.mockk:mockk-android:1.13.12")

    // Coroutines Test para Android
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
}

// ====================================================================
// CONFIGURACIÓN DE TESTS
// ====================================================================
tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
        showExceptions = true
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
    }
}