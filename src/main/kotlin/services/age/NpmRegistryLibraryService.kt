package services.age

import com.google.api.client.http.GenericUrl
import dtos.Dependency
import dtos.LibraryInformation
import dtos.LibraryVersion
import dtos.NpmResponseDto
import org.dxworks.utils.java.rest.client.RestClient
import services.convertISO8061ToDate

class NpmRegistryLibraryService : LibraryService, RestClient(NPM_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency): LibraryInformation? {
        return dependency.name?.let {
            httpClient.get(GenericUrl(getApiPath(it))).parseAs(NpmResponseDto::class.java)
                .let { res ->
                    LibraryInformation().apply {
                        project = dependency.project
                        name = dependency.name
                        description = res.description
                        issuesUrl = extractIssuesUrl(res)
                        licences = res.license?.let { listOf(it) } ?: emptyList()
                        reposUrl = extractReposUrl(res)
                        versions = extractLibraryVersions(res)
                    }
                }
        }
    }

    private fun extractIssuesUrl(res: NpmResponseDto): List<String> {
        val bugs = res["bugs"]

        return try {
            when (bugs) {
                is String -> listOf(bugs)
                is Map<*, *> -> listOf((bugs["url"] as String))
                else -> emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun extractReposUrl(res: NpmResponseDto): List<String> {
        val repos = res["repository"]

        return try {
            when (repos) {
                is String -> listOf(repos)
                is Map<*, *> -> listOf((repos["url"] as String))
                else -> emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
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