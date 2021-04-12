package services

import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path


private val CSV_HEADER =
    "dependency,current_version,release_time_current_version,latest_version,release_time_latest_version,time_difference"

fun writeInResultsFile(content: List<String>) {

    val resultsPath = Path.of("results")
    if(!Files.exists(resultsPath)) {
        resultsPath.toFile().mkdirs()
    }

    var fileWriter: FileWriter? = null

    try {
        fileWriter = FileWriter("results/dependency_age.csv")
        fileWriter.append(CSV_HEADER)
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
