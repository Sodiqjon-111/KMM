plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")

    kotlin("plugin.serialization") version "1.8.0"

}
android{
    buildFeatures{
        compose=true
    }
}

kotlin {
    android{
        compilations.all{
            kotlinOptions{
                jvmTarget="1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    val coroutinesVersion  ="1.6.4"
    val ktorVersion ="2.2.1"
    val koinVersion ="3.3.2"

    sourceSets {
        //commonMain sourceSet
        val commonMain by getting {
            //Common dependencies
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0") // add this
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                //Use api so that the android app can use it as well
                api("io.insert-koin:koin-core:$koinVersion")
            }
        }


        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        //AndroidMain sourceSet
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")

                api("io.insert-koin:koin-android:$koinVersion")

                // Room
                val room_version = "2.5.0"

                implementation("androidx.room:room-ktx:$room_version")
                implementation("androidx.room:room-runtime:$room_version")
//               annotationProcessor("androidx.room:room-compiler:$room_version")
//                kapt("androidx.room:room-compiler:$room_version")
            }
        }

      //  val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        //iOSMain sourceSet
        val iosMain by creating {
            dependencies{
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }

            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.projects.moviesapp"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}