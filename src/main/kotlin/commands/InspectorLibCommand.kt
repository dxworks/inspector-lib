package commands

import java.io.File

const val AGE_COMMAND = "age"
const val VULNERABILITY_COMMAND = "vulnerabilities"
const val SUMMARY_COMMAND = "summary"
const val RULE_COMMAND = "rules"
const val HELP_COMMAND = "help"

abstract class InspectorLibCommand {

    abstract fun parse(args: Array<String>): Boolean

    abstract fun execute()

    abstract fun usage(): String

    fun fileOrFolderExists(fileOrFolderPath: String): Boolean {
        val file = File(fileOrFolderPath)
        if (!file.exists()) System.err.println("Could not find path $fileOrFolderPath")
        return file.exists()
    }
}