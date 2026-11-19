plugins {
    id("com.android.library") version "9.1.1"
    `maven-publish`
}

group = "com.github.tbtsfastad"
version = System.getenv("VERSION") ?: "1.0.0"

android {
    namespace = "fan.app.state"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
            }
        }
    }
}
