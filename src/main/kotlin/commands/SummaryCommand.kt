package commands

import dtos.Dependency
import services.createLicenseFile

class SummaryCommand : InspectorLibCommand() {

    private val ageCommand = AgeCommand()
    private val vulnerabilityCommand = VulnerabilityCommand()

    override fun consumeDependencies(dependencies: List<Dependency>) {
        ageCommand.consumeDependencies(dependencies)
        vulnerabilityCommand.consumeDependencies(dependencies)

        createLicenseFile(dependencies)
    }
}
