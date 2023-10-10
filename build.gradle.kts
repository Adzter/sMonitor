import ProjectVersions.runeLiteVersion
import ProjectVersions.stormVersion

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    java
    checkstyle
    kotlin("jvm") version "1.6.21"
}

project.extra["GithubUrl"] = "https://github.com/DeadZonePlugins/plugins"

apply<BootstrapPlugin>()
apply<VersionPlugin>()

allprojects {
    group = "net.runelite.plugins"

    project.version = "3.1.0"
    project.extra["PluginProvider"] = "DeadZone"
    project.extra["ProjectUrl"] = "https://discord.gg/dzplugins"
    project.extra["ProjectSupportUrl"] = "https://discord.gg/dzplugins"
    project.extra["PluginLicense"] = "3-Clause BSD License"

    apply<JavaPlugin>()
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "checkstyle")

    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://repo.storm-client.net/dev/")
            credentials {
                username = "deadzone"
                password = "wHCsScM3N/vDxn4/KMt7kx2H0mK2Tm5VJeqdSMSLJOmr8f3ey6d2J7+mkKVW4dDt"
            }
        }
        maven {
            url = uri("https://repo.runelite.net")
        }
    }

    dependencies {
        annotationProcessor(Libraries.lombok)
        annotationProcessor(Libraries.pf4j)

        compileOnly("net.storm:http-api:$stormVersion")
        compileOnly("net.storm:runelite-api:$stormVersion")
        compileOnly("net.runelite:client:$runeLiteVersion")
        /*compileOnly("net.runelite:http-api:1.6.11-SNAPSHOT")
        compileOnly("net.runelite:runelite-api:$runeLiteVersion+")
        compileOnly("net.runelite:client:$runeLiteVersion-SNAPSHOT")*/

        compileOnly(Libraries.apacheCommonsText)
        compileOnly(Libraries.gson)
        compileOnly(Libraries.guice)
        compileOnly(Libraries.lombok)
        compileOnly(Libraries.okhttp3)
        compileOnly(Libraries.pf4j)
        compileOnly(Libraries.slf4japi)
        compileOnly(Libraries.slf4jsimple)
        compileOnly(Libraries.rxjava)
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType<Jar> {
            doLast {
                copy {
                    from("./build/libs/")
                    into("../release/")
                }
            }
        }

        withType<AbstractArchiveTask> {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
            dirMode = 493
            fileMode = 420
        }

        compileKotlin {
            kotlinOptions.jvmTarget = "11"
        }
    }
}