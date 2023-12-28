plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("com.google.firebase.appdistribution")

}

android {
    namespace = "com.example.jainshaadi"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.jainshaadi"
        minSdk = 24
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
}


dependencies {
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.facebook.android:facebook-login:latest.release")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation("com.google.firebase:firebase-functions:20.4.0")
    implementation("com.google.firebase:firebase-crashlytics:18.6.0")
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation("com.google.firebase:firebase-perf:20.5.1")
    implementation("com.google.firebase:firebase-config:21.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.yalantis:ucrop:2.2.6")
    implementation ("com.google.android.flexbox:flexbox:3.0.0")
    implementation ("com.airbnb.android:lottie:6.2.0")
    implementation ("com.google.firebase:firebase-messaging:23.4.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-inappmessaging-display")
    implementation ("com.google.android.gms:play-services-auth-api-phone:18.0.1")
    implementation ("com.google.android.play:app-update:2.1.0")

}


