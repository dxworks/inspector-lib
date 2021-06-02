import commands.*

val helpCommand = HelpCommand()

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        System.err.println("No command found!")
        helpCommand.execute()
        return
    }
    val command = args[0]
    val inspectorLibCommand = getInspectorLibCommand(command)
    if (inspectorLibCommand == null) {
        System.err.println("Invalid command!")
        helpCommand.execute()
        return
    }
    val isValidInput = inspectorLibCommand.parse(args)
    if (!isValidInput) {
        System.err.println("Input is not valid!")
        helpCommand.execute()
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
        RULE_COMMAND -> RuleCommand()
        HELP_COMMAND -> HelpCommand()
        else -> null
    }
}
