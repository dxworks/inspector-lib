import commands.*

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

    inspectorLibCommand.execute()

    println("Inspector-Lib finished!")
}

private fun getInspectorLibCommand(command: String): InspectorLibCommand? {
    return when (command) {
        AGE_COMMAND -> AgeCommand()
        VULNERABILITY_COMMAND -> VulnerabilityCommand()
        SUMMARY_COMMAND -> SummaryCommand()
        HELP_COMMAND -> HelpCommand()
        else -> null
    }
}
