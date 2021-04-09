package services

import dtos.Dependency
import dtos.LibraryInformation
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NpmRegistryLibraryServiceTest {
    val npmRegistryLibraryService = NpmRegistryLibraryService()

    @Test
    internal fun `npm registry information for jest`() {
        val information = npmRegistryLibraryService.getInformation(Dependency().also {
            it.name = "jest"
        })

        val expected = LibraryInformation().also {
            it.name = "jest"
        }

        assertEquals(expected.name, information?.name)
    }
}