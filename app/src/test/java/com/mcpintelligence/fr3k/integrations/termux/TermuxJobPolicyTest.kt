package com.mcpintelligence.fr3k.integrations.termux

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TermuxJobPolicyTest {
    private fun expectInvalid(block: () -> Unit) {
        val rejected = try { block(); false } catch (_: IllegalArgumentException) { true }
        assertTrue("invalid data must fail closed", rejected)
    }

    @Test fun quotesShellMetacharactersAsData() {
        val quoted = TermuxJobPolicy.quote("safe'; touch /tmp/unsafe; #")
        assertTrue(quoted.startsWith("'safe"))
        assertTrue(quoted.contains("'\\''"))
        assertTrue(quoted.endsWith("#'"))
    }

    @Test fun rejectsControlCharacters() {
        expectInvalid { TermuxJobPolicy.quote("ok\nsecond-command") }
        expectInvalid { TermuxJobPolicy.quote("ok\u0000bad") }
        expectInvalid { TermuxJobPolicy.quote("") }
    }

    @Test fun refusesOptionAndInjectionHosts() {
        expectInvalid { TermuxJobPolicy.host("-oProxyCommand=evil") }
        expectInvalid { TermuxJobPolicy.host("example.com; touch /tmp/x") }
        expectInvalid { TermuxJobPolicy.host("example..com") }
        assertEquals("example.com", TermuxJobPolicy.host("example.com"))
    }

    @Test fun restrictsSSHIdentityAndPort() {
        expectInvalid { TermuxJobPolicy.user("root;echo hacked") }
        expectInvalid { TermuxJobPolicy.port("22;echo hacked") }
        expectInvalid { TermuxJobPolicy.port("0") }
        expectInvalid { TermuxJobPolicy.port("65536") }
        assertEquals(2222, TermuxJobPolicy.port("2222"))
        assertEquals("deploy_user", TermuxJobPolicy.user("deploy_user"))
    }

    @Test fun refusesUnsafeGitSchemesAndCredentials() {
        expectInvalid { TermuxJobPolicy.gitHttpsUrl("file:///etc/passwd") }
        expectInvalid { TermuxJobPolicy.gitHttpsUrl("ssh://github.com/repo") }
        expectInvalid { TermuxJobPolicy.gitHttpsUrl("https://user:secret@github.com/org/repo") }
        assertEquals("https://github.com/org/repo.git",
            TermuxJobPolicy.gitHttpsUrl("https://github.com/org/repo.git"))
    }

    @Test fun restrictsOptionStylePaths() {
        expectInvalid { TermuxJobPolicy.filesystemPath("-rf") }
        expectInvalid { TermuxJobPolicy.filesystemPath("a\nb") }
        assertEquals("/tmp/my files", TermuxJobPolicy.filesystemPath("/tmp/my files"))
    }
}
