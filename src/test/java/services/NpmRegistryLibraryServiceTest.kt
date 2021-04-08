package services

import dtos.Dependency
import org.junit.jupiter.api.Test

internal class NpmRegistryLibraryServiceTest {
    val npmRegistryLibraryService = NpmRegistryLibraryService()

    @Test
    internal fun `npm registry information for jest`() {
        npmRegistryLibraryService.getInformation(Dependency().also {
            it.name = "jest"
            it.version = "^15.0.0"
        })
    }
}