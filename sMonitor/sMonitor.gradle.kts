version = project.version

project.extra["PluginName"] = "sMonitor"
project.extra["PluginDescription"] = "Logging of service activities"

dependencies {
    //implementation(project(":DeadZoneAPI"))
}

tasks {
    jar {
        manifest {
            attributes(mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
    }
}