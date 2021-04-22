import commands.*
import dtos.Dependency
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        System.err.println("No command found")
        return
    }
    val command = args[0]
    val inspectorLibCommand = getInspectorLibCommand(command)
    if (inspectorLibCommand == null) {
        System.err.println("Invalid command!")
        return
    }
    val isValidInput = inspectorLibCommand.parse(args)
    if (!isValidInput) {
        System.err.println("Input is not valid!")
        return
    }

    File("results").mkdirs()

    inspectorLibCommand.execute()


    inspectorLibCommand.dependencies.let {
        writeDependencies(it)
        writeDependenciesByProject(it)
    }
}

fun writeDependenciesByProject(dependencies: List<Dependency>) {
    if(dependencies.isNotEmpty()) {
        val resultFile = File("results/lib-summary-by-project.csv")
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
    if(dependencies.distinct().isNotEmpty()) {
        val resultFile = File("results/lib-summary.csv")
        resultFile.writeText("Library,")
        val oneDep = dependencies.first()
        resultFile.appendText(oneDep.data.keys.joinToString(",") { it })
        resultFile.appendText("\n")

        resultFile.appendText(dependencies.joinToString("\n") {
            "${it.name},${it.data.values.joinToString(",")}"
        })
    }
}

private fun getInspectorLibCommand(command: String): InspectorLibCommand? {
    return when (command) {
        AGE_COMMAND -> AgeCommand()
        VULNERABILITY_COMMAND -> VulnerabilityCommand()
        SUMMARY_COMMAND -> SummaryCommand()
        else -> null
    }
}
