package com.mcpintelligence.fr3k.integrations.termux

import java.net.URI

/**
 * Strict argument policy for the named Termux jobs. Quoting prevents shell
 * metacharacters from turning data into commands. Validation also rejects
 * leading-option, malformed-host and credential-bearing URL attacks.
 *
 * This does NOT authorize raw Termux execution. Call sites must still enforce
 * user grants and the gateway/Fleet approval policy for consequential work.
 */
internal object TermuxJobPolicy {
    private val hostPattern = Regex("[A-Za-z0-9][A-Za-z0-9.-]{0,251}[A-Za-z0-9]")
    private val userPattern = Regex("[A-Za-z_][A-Za-z0-9_-]{0,31}")

    fun quote(argument: String): String {
        require(argument.isNotEmpty() && argument.length <= 4096) { "invalid argument length" }
        require(argument.none { it == '\u0000' || it == '\n' || it == '\r' }) { "control characters not allowed" }
        return "'" + argument.replace("'", "'\\''") + "'"
    }

    fun host(input: String): String {
        require(input.length in 2..253 && hostPattern.matches(input) && !input.contains("..")) { "invalid host" }
        return input
    }

    fun user(input: String): String {
        require(userPattern.matches(input)) { "invalid SSH username" }
        return input
    }

    fun port(input: String): Int {
        val number = input.toIntOrNull()
        require(number != null && number in 1..65535) { "invalid SSH port" }
        return number
    }

    fun gitHttpsUrl(input: String): String {
        require(input.length in 12..2048) { "invalid URL length" }
        val url = try { URI(input) } catch (_: Exception) { throw IllegalArgumentException("invalid git URL") }
        require(url.scheme.equals("https", ignoreCase = true) &&
            url.host != null && url.rawUserInfo == null &&
            (url.port == -1 || url.port in 1..65535) &&
            url.rawFragment == null) { "only credential-free HTTPS git URLs allowed" }
        return input
    }

    fun filesystemPath(input: String): String {
        require(input.isNotBlank() && input.length <= 1024 &&
            !input.startsWith("-") && input.none { it == '\u0000' || it == '\n' || it == '\r' }) {
            "invalid path"
        }
        return input
    }
}
