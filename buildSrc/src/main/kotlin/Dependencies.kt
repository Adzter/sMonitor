object ProjectVersions {
    const val stormVersion = "1.6.2"
    const val runeLiteVersion = "1.10.9"
    const val apiVersion = "^1.0.0"
}

object Libraries {
    private object Versions {
        //const val guice = "5.0.1"
        const val guice = "4.1.0"
        //const val javax = "1.3.2"
        const val lombok = "1.18.20"
        const val pf4j = "3.6.0"
        const val slf4j = "1.7.25"
        const val gson = "2.8.5"
        const val apacheCommonsText = "1.2"
        const val okhttp3 = "4.9.1"
        const val rxjava = "3.1.2"
    }

    const val guice = "com.google.inject:guice:${Versions.guice}"
    //const val javax = "javax.annotation:javax.annotation-api:${Versions.javax}"
    const val lombok = "org.projectlombok:lombok:${Versions.lombok}"
    const val pf4j = "org.pf4j:pf4j:${Versions.pf4j}"
    const val slf4japi = "org.slf4j:slf4j-api:${Versions.slf4j}"
    const val slf4jsimple = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    const val rxjava = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
    const val apacheCommonsText = "org.apache.commons:commons-text:${Versions.apacheCommonsText}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
}
