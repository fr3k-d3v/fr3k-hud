# FR3K ONE — Integration Register
Updated 2026-11-21
Status vocabulary includes VERIFIED, OBSERVED, PARTIAL, PLANNED, UNKNOWN and BLOCKED.

| System | Intended responsibility | Integration decision | Current evidence / status | Next verification |
|---|---|---|---|---|
| FR3K HUD | Android operator shell | Primary UI host | Repo exists; current build/runtime not established in this pass — UNKNOWN | Review the current build and device verification records |
| FR3K Overlay / Filament | Independent 3D character and persona | Track A. Keep the character independently deployable alongside HUD | Filament port is in draft review. HUD integration and daily-device deployment are unverified — PLANNED | Review the Track A draft and verify asset provenance, render lifecycle and device behavior |
| Stock Mora and mora-texture-lab | Vendor character research and texture experiments | Research only. Do not deploy either to the daily NX809J | Mora lab consolidation is in draft review — OBSERVED | Review research outputs in the lab |
| CORTEX | Discord command ingress | Adapter to shared Job API | Repo exists; full deployed feature state not established — UNKNOWN | Inspect code, service, Discord scopes, job routing |
| Phone Control | Android interaction adapter | Capability-scoped device module. Keep the NX809J bootloader locked and use Shizuku by default for supported actions | Current permission and runtime state are unverified — UNKNOWN | Verify grants, supported actions and behavior after grant revocation |
| Fleet MCP | Remote execution + device registry | Sole remote execution authority | Current inventory and availability are unverified — UNKNOWN | Fresh inventory, health, capability and approval-path tests |
| Hermes / voice | Conversation and tool planning | Shared model/voice layer | Related repos exist; production routing not verified — UNKNOWN | Inspect endpoints, provider rotation, secret handling and tool policy |
| Divine Arsenal | Specialist research/workflow workspace | Plugin module; read-only first | Repo exists; latest REA integration and end-to-end readiness not verified here — UNKNOWN | Inspect current default branch, tests, workflow traces and integrations |
| BLACKWAVE | Radio/hardware system | Separate system with narrow bridge | Separate repos exist; current boundary implementation not verified — PLANNED | Review API boundary, credentials, authority and RF safety interlocks |
| FR3K Fabric | Contracts, identity, jobs, events | Logical shared layer reusing Fleet | Architecture proposed; not implemented by this document — PLANNED | Contract implementation and compatibility tests |

## Deployment scope
Track A runs without root. Keep the daily NX809J bootloader locked, use Shizuku by default for supported actions that need it, and exclude root, bootloader unlock and flashing steps. iOverlay, xOverlay and xOverlay-phonecontrol are out of scope.

## Related draft PRs
- [redmagic-11s-pro #5 — Mora Texture Lab](https://github.com/fr3k-d3v/redmagic-11s-pro/pull/5)
- [redmagic-11s-pro #6 — Hermes gitignore](https://github.com/fr3k-d3v/redmagic-11s-pro/pull/6)
- [android-overlay-pipeline #17 — Overlay 3D port](https://github.com/fr3k-d3v/android-overlay-pipeline/pull/17)
- [fr3k-waifu-studio #2 — Waifu Studio runtime](https://github.com/fr3k-d3v/fr3k-waifu-studio/pull/2)

## Evidence rules
A repository README is a claim about intended or implemented behaviour, not proof of current deployment. A successful build is not proof of device operation. A commit is not deployment. A heartbeat is not task progress. Every VERIFIED row must link to raw test output, commit SHA, target device and timestamp.
