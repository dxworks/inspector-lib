package services.age

import com.google.api.client.http.GenericUrl
import com.google.api.client.util.Key
import dtos.Dependency
import dtos.LibraryInformation
import dtos.LibraryVersion
import dtos.MavenCentralResponseDto
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.dxworks.utils.java.rest.client.RestClient
import services.convertTimestampToDate
import java.io.StringReader

class MavenCentralLibraryService : LibraryService, RestClient(MAVEN_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency): LibraryInformation? {
        return dependency.name?.let {
            val (group, artifact) = dependency.name!!.split(":")

            httpClient.get(MavenSearchUrl("g:\"$group\" AND a:\"$artifact\"", 100))
                .parseAs(MavenCentralResponseDto::class.java)
                .let { res ->
                    val pomUrl = "https://search.maven.org/remotecontent?filepath=${
                        group.replace(
                            ".",
                            "/"
                        )
                    }/$artifact/${dependency.version}/$artifact-${dependency.version}.pom"
                    try {
                        val metaData =
                            httpClient.get(GenericUrl(pomUrl))
                                .parseAsString()
                        val mavenModel = MavenXpp3Reader().read(StringReader(metaData))
                        LibraryInformation().apply {
                            project = dependency.project
                            name = "$group:$artifact"
                            description = mavenModel.description ?: ""
                            issuesUrl = mavenModel.issueManagement?.let { listOf(it.url) } ?: emptyList()
                            licences = mavenModel.licenses.map { it.name }
                            reposUrl = mavenModel.scm?.let { listOf(it.connection) } ?: emptyList()
                            versions = extractLibraryVersions(res)
                        }
                    } catch (e: Exception) {
                        println("Could not get pom for ${dependency.name} at $pomUrl")
                        LibraryInformation().apply {
                            name = dependency.name
                            versions = extractLibraryVersions(res)
                        }
                    }
                }
        }
    }

    private fun extractLibraryVersions(res: MavenCentralResponseDto): List<LibraryVersion> {
        val docs = res.response!!.docs!!

        return docs.map { data ->
            LibraryVersion().apply {
                version = data.v
                timestamp = convertTimestampToDate(data.timestamp)
                isLatest = data.v == docs.first().v
            }
        }
    }

    companion object {
        private const val MAVEN_SEARCH_BASE_URL = "https://search.maven.org/solrsearch/select"
    }

    class MavenSearchUrl(
        @Key
        private val q: String,
        @Key
        private val rows: Int,
        @Key
        private val start: Int = 0
    ) : GenericUrl(MAVEN_SEARCH_BASE_URL) {
        @Key
        private val wt: String = "json"

        @Key
        private val core: String = "gav"
    }


}
