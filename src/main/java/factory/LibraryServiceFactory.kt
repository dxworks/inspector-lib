package factory

import services.age.LibraryService
import services.age.MavenCentralLibraryService
import services.age.NpmRegistryLibraryService
import services.age.PypiLibraryService

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