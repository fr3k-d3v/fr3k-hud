# FR3K ONE — Integration Register
Updated: 2026-11-16
Status vocabulary: VERIFIED / OBSERVED / PARTIAL / PLANNED / UNKNOWN / BLOCKED.

| System | Intended responsibility | Integration decision | Current evidence / status | Next verification |
|---|---|---|---|---|
| FR3K HUD | Android operator shell | Primary UI host | Repo exists; current build/runtime not established in this pass — UNKNOWN | Inspect repo tree, build, install and device test |
| FR3K Overlay Full | Floating creature media + persona | Preserve as isolated module | Nov 7 APK located on Omarchy; SHA-256 verified as ec1d582fcffb046f93b132a9a42bdb75f66df34ccb4fdf06ad28fd1d2e44857c; package/signer/source commit still UNKNOWN — PARTIAL | Hash, aapt metadata, signer, source provenance, playback tests |
| CORTEX | Discord command ingress | Adapter to shared Job API | Repo exists; full deployed feature state not established — UNKNOWN | Inspect code, service, Discord scopes, job routing |
| Phone Control | Android interaction adapter | Capability-scoped device module | Existing device-control stack referenced; current permission/runtime state not established — UNKNOWN | Inventory enrolled phones, permission and action evidence |
| Fleet MCP | Remote execution + device registry | Sole remote execution authority | Connected Fleet tools available; prior inventory showed 9 devices, 3 online at observation time — OBSERVED | Fresh inventory, health, capability and approval-path tests |
| Hermes / voice | Conversation and tool planning | Shared model/voice layer | Related repos exist; production routing not verified — UNKNOWN | Inspect endpoints, provider rotation, secret handling and tool policy |
| Divine Arsenal | Specialist research/workflow workspace | Plugin module; read-only first | Repo exists; latest REA integration and end-to-end readiness not verified here — UNKNOWN | Inspect current default branch, tests, workflow traces and integrations |
| BLACKWAVE | Radio/hardware system | Separate system with narrow bridge | Separate repos exist; current boundary implementation not verified — PLANNED | Review API boundary, credentials, authority and RF safety interlocks |
| FR3K Fabric | Contracts, identity, jobs, events | Logical shared layer reusing Fleet | Architecture proposed; not implemented by this document — PLANNED | Contract implementation and compatibility tests |

## Explicit exclusions
- iOverlay
- xOverlay
Neither is the target creature application for this project. The target is FR3K Overlay Full.

## Evidence rules
A repository README is a claim about intended or implemented behaviour, not proof of current deployment. A successful build is not proof of device operation. A commit is not deployment. A heartbeat is not task progress. Every VERIFIED row must link to raw test output, commit SHA, target device and timestamp.
