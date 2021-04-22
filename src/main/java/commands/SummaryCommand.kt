package commands

import dtos.Dependency
import java.io.File

class SummaryCommand : InspectorLibCommand() {

    private val ageCommand = AgeCommand()
    private val vulnerabilityCommand = VulnerabilityCommand()

    override fun consumeDependencies(dependencies: List<Dependency>) {
        ageCommand.consumeDependencies(dependencies)
        vulnerabilityCommand.consumeDependencies(dependencies)

        createLicenseFile(dependencies)
    }


    private fun createLicenseFile(dependencies: List<Dependency>) {
        val contentString = dependencies.mapNotNull { it.libraryInformation }
            .flatMap { it.licences }
            .filterNotNull()
            .map { it.toLowerCase() }
            .distinct()
            .groupingBy { it }
            .eachCount()
            .entries
            .joinToString("\n") { "\"${it.key}\",${it.value}" }

        File("results/licenses.csv").writeText("License,Usages\n$contentString")
    }
}
