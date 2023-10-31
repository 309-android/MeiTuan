plugins {
    id("com.android.application")
}

android {
    namespace = "com.androidClass.meituan"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.androidClass.meituan"
        minSdk = 28
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.squareup.okhttp3:okhttp:3.14.1")
    implementation ("com.google.code.gson:gson:2.8.2")
    implementation ("org.greenrobot:eventbus:3.2.0")
    implementation ("com.alibaba:fastjson:1.2.76")

    implementation ("com.google.android.material:material:1.4.0")


}