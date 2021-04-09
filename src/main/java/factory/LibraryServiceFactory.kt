package factory

import services.LibraryService
import services.MavenCentralLibraryService
import services.NpmRegistryLibraryService
import services.PypiLibraryService

class LibraryServiceFactory {
    fun createLibraryService(provider: String?): LibraryService? {
        return when (provider) {
            "maven", "gradle" -> MavenCentralLibraryService()
            "npm" -> NpmRegistryLibraryService()
            "pypi" -> PypiLibraryService()
            else -> null
        }
    }
}