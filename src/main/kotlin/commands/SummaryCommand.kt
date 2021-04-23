package commands

import dtos.Dependency
import services.createLicenseFile

class SummaryCommand : WithFilesCommand() {

    private val ageCommand = AgeCommand()
    private val vulnerabilityCommand = VulnerabilityCommand()

    override fun consumeDependencies(dependencies: List<Dependency>) {
        ageCommand.consumeDependencies(dependencies)
        vulnerabilityCommand.consumeDependencies(dependencies)

        createLicenseFile(dependencies)
    }

    override fun usage(): String ="inspector-lib summary <path_to_file>..."
}
