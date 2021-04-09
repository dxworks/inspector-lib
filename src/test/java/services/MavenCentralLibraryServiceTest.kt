package services

import dtos.Dependency
import dtos.LibraryInformation
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class MavenCentralLibraryServiceTest {
    val mavenCentralLibraryService = MavenCentralLibraryService()

    @Test
    internal fun `test maven request for dxworks`() {

        val information = mavenCentralLibraryService.getInformation(Dependency().also {
            it.name = "junit:junit"
        })

        val expected = LibraryInformation().also {
            it.name = "junit:junit"
        }

        assertEquals(expected.name, information?.name)
    }
}

