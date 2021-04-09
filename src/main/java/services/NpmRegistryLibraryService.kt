package services

import com.google.api.client.http.GenericUrl
import dtos.Dependency
import dtos.NpmResponseDto
import dtos.NpmResponseVersionDto
import org.dxworks.utils.java.rest.client.RestClient
import java.util.*

class NpmRegistryLibraryService : LibraryService, RestClient(NPM_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency): String {

        var responseVersions: Map<String, NpmResponseVersionDto>? = null
        var responseTime: Map<String, String>? = null

        dependency.name?.let {
            httpClient.get(GenericUrl(getApiPath(it))).parseAs(NpmResponseDto::class.java)
                .let { res ->
                    responseVersions = res.versions
                    responseTime = res.time
                }
        }

        val lastVersion = responseVersions?.entries?.last()
        var lastDependencyDate = Date(0)

        var information = ""

        responseTime?.forEach {
            if (lastVersion?.key == it.key) {
                lastDependencyDate = TimeConverterService().convertISO_8061ToDate(it.value)
            }
        }

        responseTime?.forEach {
            if (it.key == dependency.version) {
                val dependencyDate = TimeConverterService().convertISO_8061ToDate(it.value)
                information =
                    "${dependency.name},${dependency.version},$dependencyDate,${lastVersion?.key},$lastDependencyDate,${
                        TimeDifferenceService().differenceBetweenDates(dependencyDate, lastDependencyDate)
                    }"
            }
        }

        return information
    }

    companion object {
        private const val NPM_SEARCH_BASE_URL = "http://registry.npmjs.com/"
    }
}