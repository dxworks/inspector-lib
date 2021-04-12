package services

import com.google.api.client.http.GenericUrl
import dtos.Dependency
import dtos.LibraryInformation
import dtos.LibraryVersion
import dtos.PypiResponseDto
import org.dxworks.utils.java.rest.client.RestClient

class PypiLibraryService : LibraryService, RestClient(PYPI_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency): LibraryInformation? {

        return dependency.name?.let {
            httpClient.get(GenericUrl(getApiPath("$it/json"))).parseAs(PypiResponseDto::class.java)
                .let { res ->
//                    val responseInfo = res.info
//                    val responseReleases = res.releases
//
//                    val latestVersion = responseInfo?.version
//                    val lastDependencyDate =
//                        responseReleases?.let { it[latestVersion]?.firstOrNull()?.uploadTimeIso8601 }
//                            ?.let { convertISO8061ToDate(it) }
//
//                    val dependencyDate = responseReleases?.let {
//                        it[dependency.version]?.firstOrNull()?.uploadTimeIso8601?.let {
//                            convertISO8061ToDate(
//                                it
//                            )
//                        }
//                    }
//
//
//
//                    lastDependencyDate
//                    "${dependency.name},${dependency.version},$dependencyDate,$latestVersion,$lastDependencyDate,${
//                        differenceBetweenDates(dependencyDate, lastDependencyDate)
//                    }"

                    LibraryInformation().apply {
                        name = res.info?.name
                        description = res.info?.summary
                        licences = res.info?.license?.let { listOf(it) } ?: emptyList()
                        reposUrl = res.info?.homePage?.let { listOf(it) } ?: emptyList()
                        issuesUrl = res.info?.bugtrackUrl?.let { listOf(it) } ?: emptyList()
                        versions = res.releases.entries.map { (version, versionData) ->
                            LibraryVersion().also {
                                it.version = version
                                it.timestamp = versionData.firstOrNull()?.uploadTimeIso8601
                                    ?.let { convertISO8061ToDate(it) }
                                it.isLatest = version == res.info?.version
                            }
                        }
                    }
                }
        }
    }

    companion object {
        private const val PYPI_SEARCH_BASE_URL = "https://pypi.org/pypi"
    }
}