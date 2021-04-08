package services

import dtos.Dependency
import org.junit.jupiter.api.Test

internal class PypiLibraryServiceTest {
    val pypiLibraryService = PypiLibraryService()

    @Test
    internal fun `pypi information for tensorflow`() {
        pypiLibraryService.getInformation(Dependency().also {
            it.name = "tensorflow"
            it.version = "1.0.1"
        })
    }
}