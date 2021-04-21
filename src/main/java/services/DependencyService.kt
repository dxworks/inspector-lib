package services

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dtos.Dependency
import java.io.File
import java.io.IOException

fun getDependenciesFromFiles(inspectorLibFiles: List<String>): List<Dependency> =
    inspectorLibFiles
        .map { File(it) }
        .flatMap { file ->
            when {
                file.isDirectory -> file.walk().filter { it.isFile }.filter { it.name.endsWith(".json") }.toList()
                file.isFile && file.name.endsWith(".json") -> listOf(file)
                else -> emptyList()
            }
        }.flatMap {
            getDependenciesFromFile(it).apply {
                entries.forEach { (proj: String, deps) -> deps.forEach { it.project = proj } }
            }.values
        }.flatten()

private fun getDependenciesFromFile(ruleFilePath: File): Map<String, List<Dependency>> {
    try {
        return jacksonObjectMapper().readValue(ruleFilePath, object : TypeReference<Map<String, List<Dependency>>>() {})
    } catch (e: IOException) {
        System.err.println(e.toString() + "Could not read dependency file!")
    }
    return emptyMap()
}