package services

import dtos.Dependency
import org.junit.jupiter.api.Test

internal class MavenCentralLibraryServiceTest {
    val mavenCentralLibraryService = MavenCentralLibraryService()

    @Test
    internal fun `test maven request for dxworks`() {

        mavenCentralLibraryService.getInformation(Dependency().also {
            it.name = "junit:junit:3.7"
        })
    }
}

