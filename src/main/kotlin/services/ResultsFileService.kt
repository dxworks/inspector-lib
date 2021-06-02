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
        val distinctDeps = dependencies.distinct()
        val oneDep = distinctDeps.first()
        resultFile.appendText(oneDep.data.keys.joinToString(",") { it })
        resultFile.appendText("\n")

        resultFile.appendText(distinctDeps.joinToString("\n") {
            "${it.name},${it.data.values.joinToString(",")}"
        })
    }
}

fun createLicenseFile(dependencies: List<Dependency>) {
    val contentString = dependencies.mapNotNull { it.libraryInformation }
        .flatMap { lib -> lib.licences.filterNotNull().map { Pair(it.toLowerCase(), lib.name) } }
        .groupBy { it.first }
        .entries
        .joinToString("\n") { "\"${it.key}\",${it.value.size},${it.value.distinct().map { it.second }}" }

    /*
    val newCotent = mutableListOf<String>()

    dependencies.forEach {
        if (it.libraryInformation != null) {
            if (it.libraryInformation!!.licences.isNotEmpty())
            newCotent.add("\"${it.libraryInformation!!.licences[0]!!.toLowerCase()}\",${it.name},${it.version}")
        }
    }

    val content = newCotent.toTypedArray().groupingBy { it }.eachCount().entries.joinToString("\n") { "${it.key},${it.value}" }
    */


    val resultsPath = Path.of("results")
    if (!Files.exists(resultsPath)) {
        resultsPath.toFile().mkdirs()
    }

    File("results/licenses.csv").writeText("License,Usages,Libraries\n$contentString")
    //File("results/newLicenses.csv").writeText("License,Library Name,Library Version,Library Occurrence\n$content")
}