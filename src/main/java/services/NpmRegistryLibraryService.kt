package services

import com.google.api.client.http.GenericUrl
import dtos.Dependency
import dtos.LibraryInformation
import dtos.LibraryVersion
import dtos.NpmResponseDto
import org.dxworks.utils.java.rest.client.RestClient

class NpmRegistryLibraryService : LibraryService, RestClient(NPM_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency): LibraryInformation? {
        return dependency.name?.let {
            httpClient.get(GenericUrl(getApiPath(it))).parseAs(NpmResponseDto::class.java)
                .let { res ->
                    LibraryInformation().apply {
                        name = res.name
                        description = res.description
                        issuesUrl = res.bugs?.url?.let { listOf(it) } ?: emptyList()
                        licences = res.license?.let { listOf(it) } ?: emptyList()
                        reposUrl = res.repository?.url?.let { listOf(it) } ?: emptyList()
                        versions = extractLibraryVersions(res)
                    }
                }
        }

//        val lastVersion = responseVersions?.entries?.last()
//        var lastDependencyDate = Date(0)
//
//        var information = ""
//
//        responseTime?.forEach {
//            if (lastVersion?.key == it.key) {
//                lastDependencyDate = convertISO8061ToDate(it.value)
//            }
//        }
//
//        responseTime?.forEach {
//            if (it.key == dependency.version) {
//                val dependencyDate = convertISO8061ToDate(it.value)
//                information =
//                    "${dependency.name},${dependency.version},$dependencyDate,${lastVersion?.key},$lastDependencyDate,${
//                        differenceBetweenDates(dependencyDate, lastDependencyDate)
//                    }"
//            }
//        }
    }

    private fun extractLibraryVersions(res: NpmResponseDto): List<LibraryVersion> {
        val versions = res.versions
        val releaseDates = res.time

        return versions.map { (versionId, _) ->
            LibraryVersion().apply {
                version = versionId
                timestamp = releaseDates[versionId]?.let { convertISO8061ToDate(it) }
                isLatest = versionId == res.distTags?.get("latest")
            }
        }
    }

    companion object {
        private const val NPM_SEARCH_BASE_URL = "https://registry.npmjs.com/"
    }
}