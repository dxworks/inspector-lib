package services

import com.google.api.client.http.GenericUrl
import dtos.Dependency
import dtos.PypiResponseDto
import dtos.PypiResponseInfoDto
import dtos.PypiResponseReleasesDto
import org.dxworks.utils.java.rest.client.RestClient
import java.util.*

class PypiLibraryService : LibraryService, RestClient(PYPI_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency) {

        var responseInfo: PypiResponseInfoDto? = null
        var responseReleases: Map<String, List<PypiResponseReleasesDto>>? = null

        dependency.name?.let {
            httpClient.get(GenericUrl(getApiPath("$it/json"))).parseAs(PypiResponseDto::class.java)
                .let { res ->
                    responseInfo = res.info
                    responseReleases = res.releases
                }
        }

        var latestVersion = responseInfo?.version

        var lastDependencyDate : Date? = null

        responseReleases?.forEach {
            if(latestVersion == it.key) {
                lastDependencyDate = TimeConverterService().convertISO_8061ToDate(it.value.first().upload_time_iso_8601)
            }
        }

        responseReleases?.forEach {
            if(dependency.version == it.key) {
                val dependencyDate = TimeConverterService().convertISO_8061ToDate(it.value.first().upload_time_iso_8601)
                println("Dependency ${dependency.name}:${dependency.version} was released on: $dependencyDate")
                println("The last version is: $latestVersion and was released on: $lastDependencyDate")
                println("The time difference between the last version and the currently used version is: ${TimeDifferenceService().differenceBetweenDates(dependencyDate, lastDependencyDate)}")
            }
        }
    }

    companion object {
        private const val PYPI_SEARCH_BASE_URL = "https://pypi.org/pypi"
    }
}