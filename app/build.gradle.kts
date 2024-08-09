import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.suitmedia.portalsuitmedia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.suitmedia.portalsuitmedia"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()

        val localPropertiesFile = project.rootProject.file("local.properties")

        properties.load(localPropertiesFile.inputStream())
        val baseUrl:String = properties.getProperty("BASE_URL")

        buildConfigField(
            "String",
            "BASE_URL",
            "\"$baseUrl\""
        )
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
        buildConfig = true
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
    implementation("androidx.paging:paging-runtime-ktx:3.1.0")
    implementation(libs.circleimageview)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converterGson)
    implementation(libs.loggingInterceptor)

    //lifecycle
    implementation(libs.lifecycleRuntimeKtx)

    //coroutines
    implementation(libs.lifecycleViewModelKtx)
    implementation(libs.coroutinesCore)
    implementation(libs.lifecycleLivedataKtx)
    implementation(libs.coroutinesAndroid)

    //Glide
    implementation(libs.glide)
    annotationProcessor(libs.glideCompiler)

    //Refresh layout
    implementation(libs.swiperefreshlayout)


}