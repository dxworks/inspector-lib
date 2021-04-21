package services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dtos.VulnerabilityResponseDto
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path


private const val AGE_CSV_HEADER =
    "dependency,current_version,release_time_current_version,latest_version,release_time_latest_version,time_difference"

private const val SUMMARY_CSV_HEADER =
    "dependency,current_version,release_time_current_version,latest_version,release_time_latest_version,time_difference_latest_version,time_difference_current_time,vulnerabilities_description,vulnerabilities_reference,vulnerabilities_cvssScore,vulnerabilities_mean_cvssScore"

private const val PROJECT_SUMMARY_CSV_HEADER =
    "project,dependency,current_version,release_time_current_version,latest_version,release_time_latest_version,time_difference_latest_version,time_difference_current_time,vulnerabilities_description,vulnerabilities_reference,vulnerabilities_cvssScore,vulnerabilities_mean_cvssScore"

private const val LICENCES_CSV_HEADER =
    "licence,appearance"

fun writeAgeInResultsFile(content: List<String>) {
    val resultsPath = Path.of("results")
    if (!Files.exists(resultsPath)) {
        resultsPath.toFile().mkdirs()
    }

    var fileWriter: FileWriter? = null

    try {
        fileWriter = FileWriter("results/dependency_age.csv")
        fileWriter.append(AGE_CSV_HEADER)
        fileWriter.append('\n')

        content.forEach {
            fileWriter.append(it)
            fileWriter.append('\n')
        }
    } catch (e: Exception) {
        println("Writing result file error!")
        e.printStackTrace()
    } finally {
        try {
            fileWriter!!.flush()
            fileWriter.close()
        } catch (e: IOException) {
            println("Flushing/closing error!")
            e.printStackTrace()
        }
    }
}

fun writeVulnerabilitiesInResultsFile(vulnerabilities: List<VulnerabilityResponseDto>) {
    val resultsPath = Path.of("results")
    if (!Files.exists(resultsPath)) {
        resultsPath.toFile().mkdirs()
    }

    val mapper = jacksonObjectMapper()

    mapper.writerWithDefaultPrettyPrinter().writeValue(File("results/dependency_vulnerability.json"), vulnerabilities)
}

fun writeSummaryResultFile(content: List<String>) {
    val resultsPath = Path.of("results")
    if (!Files.exists(resultsPath)) {
        resultsPath.toFile().mkdirs()
    }

    var fileWriter: FileWriter? = null

    try {
        fileWriter = FileWriter("results/summary.csv")
        fileWriter.append(SUMMARY_CSV_HEADER)
        fileWriter.append('\n')

        content.forEach {
            fileWriter.append(it)
            fileWriter.append('\n')
        }
    } catch (e: Exception) {
        println("Writing result file error!")
        e.printStackTrace()
    } finally {
        try {
            fileWriter!!.flush()
            fileWriter.close()
        } catch (e: IOException) {
            println("Flushing/closing error!")
            e.printStackTrace()
        }
    }
}

fun writeSummaryResultFileWithProjectName(content: List<String>) {
    val resultsPath = Path.of("results")
    if (!Files.exists(resultsPath)) {
        resultsPath.toFile().mkdirs()
    }

    var fileWriter: FileWriter? = null

    try {
        fileWriter = FileWriter("results/project_summary.csv")
        fileWriter.append(PROJECT_SUMMARY_CSV_HEADER)
        fileWriter.append('\n')

        content.forEach {
            fileWriter.append(it)
            fileWriter.append('\n')
        }
    } catch (e: Exception) {
        println("Writing result file error!")
        e.printStackTrace()
    } finally {
        try {
            fileWriter!!.flush()
            fileWriter.close()
        } catch (e: IOException) {
            println("Flushing/closing error!")
            e.printStackTrace()
        }
    }
}

fun writeLicencesResultFile(licenses: Map<String, Long>) {
    val resultsPath = Path.of("results")
    if (!Files.exists(resultsPath)) {
        resultsPath.toFile().mkdirs()
    }

    var fileWriter: FileWriter? = null

    try {
        fileWriter = FileWriter("results/licences.csv")
        fileWriter.append(LICENCES_CSV_HEADER)
        fileWriter.append('\n')

        licenses.forEach {
            if(it.key.isNotEmpty()) {
                fileWriter.append(it.key)
                fileWriter.append(',')
                fileWriter.append(it.value.toString())
                fileWriter.append('\n')
            }
        }

    } catch (e: Exception) {
        println("Writing result file error!")
        e.printStackTrace()
    } finally {
        try {
            fileWriter!!.flush()
            fileWriter.close()
        } catch (e: IOException) {
            println("Flushing/closing error!")
            e.printStackTrace()
        }
    }
}