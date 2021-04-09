package services

import com.google.api.client.http.GenericUrl
import dtos.Dependency
import dtos.PypiResponseDto
import dtos.PypiResponseInfoDto
import dtos.PypiResponseReleasesDto
import org.dxworks.utils.java.rest.client.RestClient
import java.util.*

class PypiLibraryService : LibraryService, RestClient(PYPI_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency): String {

        var responseInfo: PypiResponseInfoDto? = null
        var responseReleases: Map<String, List<PypiResponseReleasesDto>>? = null

        dependency.name?.let {
            httpClient.get(GenericUrl(getApiPath("$it/json"))).parseAs(PypiResponseDto::class.java)
                .let { res ->
                    responseInfo = res.info
                    responseReleases = res.releases
                }
        }

        val latestVersion = responseInfo?.version
        var lastDependencyDate = Date(0)

        var information = ""

        responseReleases?.forEach {
            if (latestVersion == it.key) {
                lastDependencyDate = TimeConverterService().convertISO8061ToDate(it.value.first().upload_time_iso_8601)
            }
        }

        responseReleases?.forEach {
            if (dependency.version == it.key) {
                val dependencyDate = TimeConverterService().convertISO8061ToDate(it.value.first().upload_time_iso_8601)
                information =
                    "${dependency.name},${dependency.version},$dependencyDate,$latestVersion,$lastDependencyDate,${
                        TimeDifferenceService().differenceBetweenDates(dependencyDate, lastDependencyDate)
                    }"
            }
        }

        return information
    }

    companion object {
        private const val PYPI_SEARCH_BASE_URL = "https://pypi.org/pypi"
    }
}