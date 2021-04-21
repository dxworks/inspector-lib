package commands

import dtos.Dependency
import dtos.LibraryInformation
import dtos.LibraryVersion
import factory.createLibraryService
import me.tongfei.progressbar.ProgressBarBuilder
import me.tongfei.progressbar.ProgressBarStyle
import services.DATE_FORMATTER
import services.age.LibraryService
import services.differenceBetweenDatesInMonths
import services.safeLet
import java.time.ZonedDateTime
import java.util.stream.Collectors

class AgeCommand : InspectorLibCommand() {

    private var dependenciesByProvider: Map<String, List<Dependency>> = emptyMap()

    override fun consumeDependencies(dependencies: List<Dependency>) {
        println("Getting library meta information...")

        dependenciesByProvider = dependencies.stream().collect(Collectors.groupingBy(Dependency::provider))
        ProgressBarBuilder()
            .setInitialMax(dependencies.size.toLong())
            .setUnit(" Dependencies", 1)
            .setTaskName("Getting age...")
            .setStyle(ProgressBarStyle.ASCII)
            .setUpdateIntervalMillis(100)
            .setPrintStream(System.err)
            .build().use { pb ->
                dependenciesByProvider.forEach { (key, value) ->
                    val libraryService = createLibraryService(key)
                    if (libraryService == null) {
                        println("Provider $key not supported!")
                        return@forEach
                    }
                    for (d in value) {
                        getLibraryInformation(libraryService, d)?.let { libraryInformation ->
                            d.libraryInformation = libraryInformation

                            d.data["Current Version"] = d.version.orEmpty()
                            val latestVersion = getLatestVersion(libraryInformation)
                            d.data["Latest Version"] = latestVersion?.version.orEmpty()

                            val usedVersionReleaseTime = getCurrentVersionReleaseTime(libraryInformation, d.version)
                            d.data["Current Version Release Time"] =
                                usedVersionReleaseTime?.let { DATE_FORMATTER.format(it) }.orEmpty()
                            val latestVersionReleaseTime = latestVersion?.timestamp
                            d.data["Latest Version Release Time"] =
                                latestVersionReleaseTime?.let { DATE_FORMATTER.format(it) }.orEmpty()

                            val `latest - current` =
                                safeLet(usedVersionReleaseTime, latestVersionReleaseTime) { used, latest ->
                                    differenceBetweenDatesInMonths(used, latest)
                                }?.toString().orEmpty()
                            d.data["Time to Latest Version (in months)"] = `latest - current`

                            val `now - current` = usedVersionReleaseTime?.let {
                                differenceBetweenDatesInMonths(it, ZonedDateTime.now())
                            }?.toString().orEmpty()
                            d.data["Time to Now (in months)"] = `now - current`
                        }

                        pb.step()

                    }
                }
            }
    }

    private fun getLibraryInformation(libraryService: LibraryService, d: Dependency): LibraryInformation? =
        try {
            libraryService.getInformation(d)
        } catch (e: Exception) {
            println("Could not get Library information for ${d.name} with version ${d.version}")
            e.printStackTrace()
            null
        }

    private fun getCurrentVersionReleaseTime(
        libraryInformation: LibraryInformation?,
        version: String?
    ): ZonedDateTime? {
        for (v in libraryInformation!!.versions) {
            if (version == v.version) {
                return v.timestamp
            }
        }
        return null
    }

    private fun getLatestVersion(libraryInformation: LibraryInformation?): LibraryVersion? {
        for (v in libraryInformation!!.versions) {
            if (v.isLatest) {
                return v
            }
        }
        return null
    }
}