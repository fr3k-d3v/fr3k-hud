# HUD Android release checklist

- Inspect the final APK and record its application ID, `versionName` and
  `versionCode`. For HUD v0.4.16 these are `com.mcpintelligence.fr3k.hud`,
  `0.4.16` and `416`. Increase `versionCode` for a new release.
- Record the Git tag and the exact source commit used for the build.
- Verify the APK signature and record the signing certificate's SHA-256
  fingerprint. Label debug builds as debug and use the project's authorized
  release signing process for release builds. Keep signing material out of Git.
- Generate a SHA-256 sidecar from the final signed APK with
  `sha256sum HUD.apk > HUD.apk.sha256`. Verify it with
  `sha256sum -c HUD.apk.sha256` and publish it alongside the APK.
- Compare the candidate's signing certificate with the installed build before
  planning an in-place update. The September v0.4.16 APK and the 21 November
  master rebuild used different Android Debug certificates. Android would
  reject an in-place `adb install -r` even though the package and versions match.
