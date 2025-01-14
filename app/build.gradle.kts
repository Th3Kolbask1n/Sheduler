plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    id ("kotlin-kapt")
}

android {
    namespace = "com.alexp.sheduler"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alexp.sheduler"
        minSdk = 26
        targetSdk = 33
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
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation (libs.androidx.appcompat.v140)
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.constraintlayout.v212)
    implementation (libs.androidx.recyclerview)
    implementation (libs.androidx.cardview)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation (libs.material.v150)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)

    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.room.paging)


    implementation(libs.picasso)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.dagger2)
    ksp(libs.dagger2.compiler)
    ksp(libs.dagger2.android.processor)

    implementation (libs.kotlin.stdlib)

    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)

    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)
    ksp("androidx.room:room-compiler:2.6.1")


}