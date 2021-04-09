package services

import dtos.Dependency
import dtos.LibraryInformation
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PypiLibraryServiceTest {
    val pypiLibraryService = PypiLibraryService()

    @Test
    internal fun `pypi information for tensorflow`() {
        val information = pypiLibraryService.getInformation(Dependency().also {
            it.name = "tensorflow"
        })

        val expected = LibraryInformation().also {
            it.name = "tensorflow"
        }

        assertEquals(expected.name, information?.name)
    }
}