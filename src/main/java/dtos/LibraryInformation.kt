package dtos

import java.time.ZonedDateTime

class LibraryInformation {
    var project: String? = null
    var name: String? = null
    var versions: List<LibraryVersion> = emptyList()
    var reposUrl: List<String> = emptyList()
    var issuesUrl: List<String> = emptyList()
    var licences: List<String?> = emptyList()
    var description: String? = null

}

class LibraryVersion {
    var version: String? = null
    var timestamp: ZonedDateTime? = null
    var isLatest: Boolean = false
}
