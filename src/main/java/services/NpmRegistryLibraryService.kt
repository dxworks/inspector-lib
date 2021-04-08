package services

import com.google.api.client.http.GenericUrl
import dtos.Dependency
import dtos.NpmResponseDto
import dtos.NpmResponseVersionDto
import org.dxworks.utils.java.rest.client.RestClient
import java.util.*

class NpmRegistryLibraryService : LibraryService, RestClient(NPM_SEARCH_BASE_URL) {
    override fun getInformation(dependency: Dependency) {

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
        var lastDependencyDate : Date? = null

        responseTime?.forEach {
            if(lastVersion?.key == it.key) {
                lastDependencyDate = TimeConverterService().convertISO_8061ToDate(it.value)
            }
        }

        responseTime?.forEach {
            if (it.key == dependency.version) {
                val dependencyDate = TimeConverterService().convertISO_8061ToDate(it.value)
                println("Dependency ${dependency.name}:${dependency.version} was released on: $dependencyDate")
                println("The last version is: ${lastVersion?.key} and was released on: $lastDependencyDate")
                println("The time difference between the last version and the currently used version is: ${TimeDifferenceService().differenceBetweenDates(dependencyDate, lastDependencyDate)}")
            }
        }

    }

    companion object {
        private const val NPM_SEARCH_BASE_URL = "http://registry.npmjs.com/"
    }
}