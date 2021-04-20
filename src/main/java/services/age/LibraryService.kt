package services.age

import dtos.Dependency
import dtos.LibraryInformation

interface LibraryService {
    fun getInformation(dependency: Dependency): LibraryInformation?
}