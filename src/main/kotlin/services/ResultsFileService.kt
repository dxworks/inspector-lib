package services

import dtos.Dependency
import java.io.File
import java.nio.file.Files
import java.nio.file.Path


fun writeDependenciesByProject(dependencies: List<Dependency>) {
    if (dependencies.isNotEmpty()) {
        val resultsPath = Path.of("results")
        if (!Files.exists(resultsPath)) {
            resultsPath.toFile().mkdirs()
        }

        val resultFile = File("results/inspector-lib-result-by-project.csv")
        resultFile.writeText("Project,Library,")
        val oneDep = dependencies.first()
        resultFile.appendText(oneDep.data.keys.joinToString(",") { it })
        resultFile.appendText("\n")

        resultFile.appendText(dependencies.joinToString("\n") {
            "${it.project},${it.name},${it.data.values.joinToString(",")}"
        })
    }
}

fun writeDependencies(dependencies: List<Dependency>) {
    if (dependencies.distinct().isNotEmpty()) {
        val resultsPath = Path.of("results")
        if (!Files.exists(resultsPath)) {
            resultsPath.toFile().mkdirs()
        }

        val resultFile = File("results/inspector-lib-overall-result.csv")
        resultFile.writeText("Library,")
        val oneDep = dependencies.first()
        resultFile.appendText(oneDep.data.keys.joinToString(",") { it })
        resultFile.appendText("\n")

        resultFile.appendText(dependencies.joinToString("\n") {
            "${it.name},${it.data.values.joinToString(",")}"
        })
    }
}

fun createLicenseFile(dependencies: List<Dependency>) {
    val contentString = dependencies.mapNotNull { it.libraryInformation }
        .flatMap { it.licences }
        .filterNotNull()
        .map { it.toLowerCase() }
        .distinct()
        .groupingBy { it }
        .eachCount()
        .entries
        .joinToString("\n") { "\"${it.key}\",${it.value}" }

    val resultsPath = Path.of("results")
    if (!Files.exists(resultsPath)) {
        resultsPath.toFile().mkdirs()
    }

    File("results/licenses.csv").writeText("License,Usages\n$contentString")
}