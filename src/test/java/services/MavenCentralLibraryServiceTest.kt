package services

import dtos.Dependency
import dtos.LibraryInformation
import org.junit.jupiter.api.Test
import services.age.MavenCentralLibraryService
import kotlin.test.assertEquals

internal class MavenCentralLibraryServiceTest {
    val mavenCentralLibraryService = MavenCentralLibraryService()

    @Test
    internal fun `test maven request for dxworks`() {

        val information = mavenCentralLibraryService.getInformation(Dependency().also {
            it.name = "junit:junit"
            it.version = "4.12"
        })

        val expected = LibraryInformation().also {
            it.name = "junit:junit"
        }

        assertEquals(expected.name, information?.name)
    }
}

