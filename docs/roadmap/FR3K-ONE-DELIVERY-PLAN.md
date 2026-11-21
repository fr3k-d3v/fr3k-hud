# FR3K ONE — Delivery Plan
Updated 2026-11-21

## Workstreams
1. Provenance and security: identify exact HUD, FR3K Overlay / Filament, CORTEX and Divine Arsenal source commits; verify APK signer/hash; inventory secrets; rotate exposed credentials; record SBOM.
2. Fabric contracts: define typed capability/job/event/evidence schemas and contract tests; reuse Fleet job state machine.
3. Android shell: HUD module host, status dashboard, permissions, job timeline and safe stop.
4. Keep the independent FR3K Overlay / Filament character behind the Track A module boundary. Keep stock Mora and mora-texture-lab in research and off the daily NX809J.
5. CORTEX/Discord and Android Phone Control submit typed requests. Route remote execution through Fleet. Keep the daily NX809J bootloader locked and use Shizuku by default for supported actions that require it. Root, bootloader unlock, flashing, xOverlay and xOverlay-phonecontrol are out of scope.
6. Specialist modules: Divine Arsenal, research, REA, local inference and other FR3K capabilities.
7. BLACKWAVE bridge: separate auth domain, allow-list and policy; no shared execution authority.
8. Release: physical-device matrix, reproducible build, signing, SBOM, security tests, staged rollout and rollback.

## First execution backlog
- [ ] Fresh Fleet device list and health; identify Omarchy, X1, Contabo and Android test devices.
- [ ] Identify the exact Track A source revision and verify the candidate build's SHA-256, package, min/target SDK, signer certificate and embedded version.
- [ ] Inspect HUD architecture, module boundaries, manifest, service ownership and build.
- [ ] Inspect CORTEX command ingress and ensure all execution delegates to Fleet.
- [ ] Inspect Phone Control permission state and supported action inventory.
- [ ] Inspect Divine Arsenal latest REA integration, tests and workflow traces.
- [ ] Inspect BLACKWAVE API and prove separation of credentials, policy and device authority.
- [ ] Implement module/job contract tests and a small vertical slice: HUD → Job API → Fleet → evidence → HUD timeline.
- [ ] Integrate the independent Track A character as an isolated module. Verify GLB import, rendering and voice behavior before any packaging decision.
- [ ] Add CI gates: unit, integration, static analysis, secret scan, dependency/SBOM, Android build and device tests.

## Release gates
Complete the P0–P6 [architecture gates](../architecture/FR3K-ONE-UNIFIED-PLATFORM.md) before production promotion. Every status update must name the commit, test, target and evidence. Mark unknowns explicitly.
