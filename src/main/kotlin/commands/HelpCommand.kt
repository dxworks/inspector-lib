package commands

import java.util.stream.Collectors
import java.util.stream.Stream


class HelpCommand : InspectorLibCommand() {
    override fun parse(args: Array<String>): Boolean {
        return if (args.size != 1) false else HELP_COMMAND == args[0]
    }

    override fun execute() {
        var usage = "Inspector-Lib usage guide:\n"

        usage += "List of the commands:\n"

        usage += Stream.of(
            AgeCommand(),
            VulnerabilityCommand(),
            SummaryCommand(),
            HelpCommand()
        )
            .map<Any>(InspectorLibCommand::usage)
            .map { s: Any -> "\t" + s }
            .collect(Collectors.joining("\n"))

        println(usage)
    }

    override fun usage(): String = "inspector-lib help"
}