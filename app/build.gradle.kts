plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.appchiasecongthucnauan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appchiasecongthucnauan"
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.microsoft.signalr:signalr:5.0.11")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
}


