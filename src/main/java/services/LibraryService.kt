package services

import dtos.Dependency

interface LibraryService {
    fun getInformation(dependency: Dependency): String
}