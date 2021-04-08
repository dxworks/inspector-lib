package services

import com.google.api.client.http.GenericUrl
import com.google.api.client.util.Key
import dtos.Dependency
import dtos.MavenCentralResponseDto
import dtos.MavenCentralResponsesDocsDto
import org.dxworks.utils.java.rest.client.RestClient

class MavenCentralLibraryService : LibraryService, RestClient(MAVEN_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency) {
        val (group, artifact) = dependency.name!!.split(":")

        var responseDocs: List<MavenCentralResponsesDocsDto>?

        httpClient.get(MavenSearchUrl("g:\"$group\" AND a:\"$artifact\"", 100)).parseAs(MavenCentralResponseDto::class.java)
            .let { res ->
                responseDocs = res.response?.docs
            }

        responseDocs?.forEach{
            if (dependency.version == it.v) {
                val dependencyDate = TimeConverterService().convertTimestampToDate(it.timestamp)
                println("Dependency ${dependency.name}:${dependency.version} was released on: $dependencyDate")
                val lastDependencyDate = TimeConverterService().convertTimestampToDate(responseDocs!![0].timestamp)
                println("The last version is: ${responseDocs!![0].v} and was released on: $lastDependencyDate")
                println("The time difference between the last version and the currently used version is: ${TimeDifferenceService().differenceBetweenDates(dependencyDate, lastDependencyDate)}")
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