package services

import dtos.Dependency

fun createPackageUrl(dependency: Dependency): String? {
    return when (dependency.provider) {
        "maven", "gradle" -> createMavenPackageUrl(dependency)
        "npm" -> createNpmPackageUrl(dependency)
        "pypi" -> createPypiPackageUrl(dependency)
        else -> null
    }
}

fun createMavenPackageUrl(dependency: Dependency): String? {
    val (group, artifact) = dependency.name!!.split(":")

    if(dependency.version.isNullOrEmpty() || dependency.version!!.contains("@"))
        return null

    if(group.isEmpty())
        return "pkg:maven/ /$artifact@${dependency.version}"

    return "pkg:maven/$group/$artifact@${dependency.version}"
}

fun createNpmPackageUrl(dependency: Dependency): String {
    return "pkg:npm/${dependency.name?.replace("@", "%40")}@${dependency.version}"
}

fun createPypiPackageUrl(dependency: Dependency): String? {
    if(dependency.version.isNullOrEmpty())
        return null
    return "pkg:pypi/${dependency.name?.replace("@", "%40")}@${dependency.version}"
}