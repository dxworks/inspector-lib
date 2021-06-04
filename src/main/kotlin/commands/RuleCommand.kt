package commands

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dtos.Dependency
import dtos.ILPattern
import dtos.Rule
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class RuleCommand : WithFilesCommand() {

    override fun consumeDependencies(dependencies: List<Dependency>) {
        println("Creating rules...")

        val rules = mutableListOf<Rule>()

        dependencies.forEach { dep ->
            if (dep.name.isNullOrEmpty()) {
            } else {
                if(dep.name!!.contains(":")) {
                    val (group, artifact) = dep.name!!.split(":")
                    if(group.isNotEmpty()) {
                        val pattern = ILPattern(group, "substring", listOf("all"))

                        if(dep.provider.equals("maven") || dep.provider.equals("gradle"))
                            rules.add(Rule(listOf(group), listOf(pattern), listOf("java")))
                        if(dep.provider.equals("npm"))
                            rules.add(Rule(listOf(group), listOf(pattern), listOf("javascript", "typescript")))
                        if(dep.provider.equals("pypi"))
                            rules.add(Rule(listOf(group), listOf(pattern), listOf("python")))
                    }
                } else {
                    val pattern = ILPattern(dep.name!!, "substring", listOf("all"))

                    if(dep.provider.equals("maven") || dep.provider.equals("gradle"))
                        rules.add(Rule(listOf(dep.name!!), listOf(pattern), listOf("java")))
                    if(dep.provider.equals("npm"))
                        rules.add(Rule(listOf(dep.name!!), listOf(pattern), listOf("javascript", "typescript")))
                    if(dep.provider.equals("pypi"))
                        rules.add(Rule(listOf(dep.name!!), listOf(pattern), listOf("python")))
                }
            }
        }

        val distinctRules = rules.distinct()

        println("Writing rules to file...")

        val resultsPath = Path.of("results")
        if (!Files.exists(resultsPath)) {
            resultsPath.toFile().mkdirs()
        }

        val resultFile = File("results/rules.json")

        val mapper = jacksonObjectMapper()
        mapper.writerWithDefaultPrettyPrinter().writeValue(resultFile,distinctRules)
    }

    override fun usage(): String = "inspector-lib rules <path_to_file>..."
}