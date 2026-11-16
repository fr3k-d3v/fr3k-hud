# FR3K ONE — Delivery Plan
Date: 2026-11-16

## Workstreams
1. Provenance and security: identify exact HUD, Overlay Full, CORTEX and Divine Arsenal source commits; verify APK signer/hash; inventory secrets; rotate exposed credentials; record SBOM.
2. Fabric contracts: define typed capability/job/event/evidence schemas and contract tests; reuse Fleet job state machine.
3. Android shell: HUD module host, status dashboard, permissions, job timeline and safe stop.
4. Creature module: port/adapt Overlay Full behind module interface; preserve media pipeline and ordered playback; voice session and per-creature persona.
5. Control adapters: CORTEX/Discord and Android Phone Control submit typed requests; no direct shell bypass.
6. Specialist modules: Divine Arsenal, research, REA, local inference and other FR3K capabilities.
7. BLACKWAVE bridge: separate auth domain, allow-list and policy; no shared execution authority.
8. Release: physical-device matrix, reproducible build, signing, SBOM, security tests, staged rollout and rollback.

## First execution backlog
- [ ] Fresh Fleet device list and health; identify Omarchy, X1, Contabo and Android test devices.
- [ ] Obtain approval for the held Fleet diagnostic job or use an authorised alternative; do not bypass.
- [ ] Verify Overlay Full APK SHA-256, package, min/target SDK, signer certificate and embedded version.
- [ ] Locate exact source repository/branch/commit corresponding to the APK.
- [ ] Inspect HUD architecture, module boundaries, manifest, service ownership and build.
- [ ] Inspect CORTEX command ingress and ensure all execution delegates to Fleet.
- [ ] Inspect Phone Control permission state and supported action inventory.
- [ ] Inspect Divine Arsenal latest REA integration, tests and workflow traces.
- [ ] Inspect BLACKWAVE API and prove separation of credentials, policy and device authority.
- [ ] Implement module/job contract tests and a small vertical slice: HUD → Job API → Fleet → evidence → HUD timeline.
- [ ] Integrate Overlay Full as an isolated module; test playback/voice before any APK merge.
- [ ] Add CI gates: unit, integration, static analysis, secret scan, dependency/SBOM, Android build and device tests.

## Release gates
No production promotion until P0-P6 architecture gates in FR3K-ONE-UNIFIED-PLATFORM.md pass. Every status update must name the commit, test, target and evidence. Mark unknowns explicitly.
