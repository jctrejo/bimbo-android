plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safe.args)
}

android {
    namespace = "com.bimbo.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bimbo.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    android {
        buildTypes {
            // Configuración para el build de 'release'
            getByName("release") {
                // Habilita la ofuscación y reducción de código con R8/ProGuard
                isMinifyEnabled = true

                // Habilita la eliminación de recursos no usados para reducir tamaño APK
                isShrinkResources = true

                // Archivos de reglas ProGuard/R8 que se aplican en la ofuscación
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"), // reglas estándar recomendadas
                    "proguard-rules.pro" // reglas personalizadas del proyecto
                )
            }

            // Configuración para el build de 'debug'
            getByName("debug") {
                // Deshabilita la ofuscación para facilitar el debugging
                isMinifyEnabled = false

                // Deshabilita la eliminación de recursos para evitar problemas durante desarrollo
                isShrinkResources = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    // Habilita características específicas del build, como View Binding
    buildFeatures {
        // Activa View Binding para generar automáticamente clases binding
        // que facilitan el acceso a las vistas sin usar findViewById
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

    // Retrofit2
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // okhttp3
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp.urlconnection)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Glide
    implementation(libs.glide.v4120)

    // Lottie
    implementation(libs.lottie)
}