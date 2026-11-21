# FR3K ONE — Unified Platform Architecture
Updated 2026-11-21
Status: Architecture baseline / implementation plan. This document does not claim production integration.

## Mission
Unify the operator experience across FR3K HUD, the independent FR3K Overlay / Filament character, CORTEX, phone control, Fleet, Hermes/voice, Divine Arsenal and compatible FR3K services. BLACKWAVE remains a separate system with its own authority and safety boundary.

## Architectural decision
Build a unified product experience and shared contracts first; do not perform a destructive repository or APK merge. Keep existing applications and services independently deployable behind versioned interfaces. The first deliverable is a HUD-centred operator shell with feature modules. Consolidation into a single APK is a later packaging decision, only after dependency, signing, lifecycle, resource and device compatibility tests.

## System boundaries
- **FR3K HUD**: primary Android operator shell, floating UI, navigation, status surfaces and module host.
- **FR3K Overlay / Filament** hosts the independent 3D character for Track A. Character voice and persona stay within the module. Stock Mora and mora-texture-lab are research-only and must stay off the daily NX809J.
- **CORTEX**: Discord and command-ingress adapter. Parses/normalises user intent and submits jobs to the authoritative API. It does not own a scheduler or directly bypass policy.
- **Phone Control** uses explicit user grants and scoped Android capabilities. Audit device actions. Keep the daily NX809J bootloader locked and default to Shizuku for supported actions that require it.
- **Fleet MCP**: authoritative remote device execution gateway and device registry. It remains the only route for remote machine commands.
- **Hermes / voice**: model, conversation and tool-planning layer. It proposes typed actions; the job authority validates and dispatches them.
- **Divine Arsenal**: specialist research/workspace module. Integrates through a constrained plugin API; no privileged device control by default.
- **FR3K Fabric (logical layer)**: shared identity, capability registry, job API, event stream, evidence store references and policy checks. Implement using existing Fleet infrastructure where possible; do not create a competing scheduler.
- **BLACKWAVE**: separate product, repository, device authority, radio policy and release lifecycle. Connect only through a narrow authenticated bridge with explicit allow-listed read/command operations. No shared credentials, unrestricted shell, or shared physical authority.

## Core operating loop
OBSERVE → VERIFY → RECONCILE → IMPROVE → TEST → RECORD.
Every operation has a stable job ID, idempotency key, declared target, capability, authority class, dependencies, lease/fencing token where needed, timeout, bounded retries, and immutable terminal result. Preserve raw output/evidence before derived interpretation.

## Job lifecycle
RECEIVED → VALIDATED → QUEUED → LEASED → RUNNING → VERIFYING → SUCCEEDED | FAILED | BLOCKED | CANCELLED | EXPIRED
Terminal states are immutable. A heartbeat is liveness only, not progress. A process running is not success. A command exit code is not proof of the requested external state. Retries must be idempotent or explicitly compensated.

## Authority model
- OBSERVE: read-only inventory, health, logs and evidence.
- AUTO: bounded, reversible, pre-authorised software actions on enrolled targets.
- APPROVAL: actions requiring a scoped, time-bound human approval.
- PHYSICAL: RF transmission, actuators, destructive storage changes, and other consequential physical or irreversible actions. Separate policy and explicit authorisation required.
Fail closed when identity, target, capability, lease, policy or evidence is ambiguous.

## Shared contracts
See the draft [module and job contracts](../contracts/fr3k-one-module-and-job-contracts.md). All modules use versioned JSON schemas over authenticated local IPC or HTTPS. No module invokes another module's private database or shell directly.
- DeviceDescriptor: device_id, display_name, platform, agent_version, capabilities[], trust_state, last_seen, evidence_refs[].
- CapabilityDescriptor: capability_id, version, input_schema, output_schema, authority_class, risk, reversible, supported_platforms[].
- JobRequest: job_id, idempotency_key, origin, requested_by, target_ids[], capability_id, capability_version, input, authority_class, dependencies[], deadline, approval_ref.
- JobEvent: job_id, sequence, timestamp, state, progress, message, evidence_refs[], worker_id.
- JobResult: job_id, terminal_state, started_at, finished_at, exit_status, verification_status, evidence_refs[], limitations[].
- EvidenceRef: evidence_id, kind, sha256, media_type, captured_at, source_device, storage_uri, redaction_status.

## Security
Use per-device identity and short-lived scoped tokens. Store provider keys in OS-backed secure storage or server-side secret management; never embed secrets in APKs, Git, logs or prompts. Separate user identity from device identity. Require explicit consent for camera/microphone and visible indicators. Redact secrets and PII before public evidence publication. Rotate any exposed credentials before further deployment. Apply least privilege, signed updates, dependency scanning and audit trails.

## Android composition strategy
1. Keep the HUD shell and Track A character independently deployable.
2. Define a stable module lifecycle: install, capability declaration, permission request, start, pause, stop, health, upgrade, rollback.
3. Avoid competing foreground services, overlay windows, audio focus, wake locks and microphone ownership.
4. Route all voice sessions through a shared VoiceSession interface; creature persona changes context/voice configuration only.
5. Start with push-to-talk; hands-free listening is a separately permissioned and battery-tested feature.
6. Build a combined APK only after manifest merge, ABI, SDK, signing, foreground-service and physical-device qualification.

## Integration phases and gates
### P0 — Baseline and safety
Inventory exact source repos, branches, APK hashes, signing identities, dependencies, current devices and live services. Back up working APKs. Identify and rotate exposed credentials. No production changes.
Exit: provenance matrix, secret incident closed, reproducible build paths or explicit UNKNOWN.

### P1 — Contracts and orchestration
Publish schemas, module manifest, capability registry, job state machine, authority model and event/evidence references. Reuse Fleet's existing job execution and approvals.
Exit: contract tests, idempotency tests, terminal-state tests, duplicate submission test and audit evidence.

### P2 — HUD shell
Implement module registry, navigation, device/status view, job timeline, permission centre and safe stop controls.
Exit: Android unit/UI tests; overlay permission and lifecycle tests on supported devices.

### P3 — Track A character module
Integrate the independent Filament character through the HUD module boundary. Verify the GLB import and render lifecycle, Studio handoff, and voice-driven animation.

Exit when supported-device checks cover rendering, overlay lifecycle, microphone and audio focus, and crash regressions.

### P4 — CORTEX and phone control
Discord commands become JobRequests. Phone actions use scoped capabilities and evidence capture. No bypass of Fleet.
Exit: authorisation, replay, expiry, duplicate command and permission revocation tests.

### P5 — Divine Arsenal and specialist plugins
Expose read-only research, REA and workflow capabilities first; grant write capabilities individually after verification.
Exit: plugin contract and isolation tests.

### P6 — Release qualification
Reproducible build, signing provenance, SBOM, dependency scan, migration/rollback, device matrix, signed release artifacts and verified deployment.
Exit: release checklist complete; production status independently confirmed.

## BLACKWAVE boundary
BLACKWAVE stays separately versioned, deployed and governed. The bridge exposes only explicitly allow-listed typed operations and evidence summaries. RF receive/measurement may be surfaced as observations. Transmission and physical actuation remain under BLACKWAVE's own authority, interlocks and approvals. FR3K ONE cannot silently promote an observation into a transmit command.

## Known evidence and unknowns
- Track A has a draft Filament port. Current status and related drafts are recorded in the [integration register](FR3K-ONE-INTEGRATION-REGISTER.md).
- Refresh the Fleet inventory before scheduling device qualification.
- UNKNOWN: actual current HUD app build state, CORTEX deployed state, Divine Arsenal live integration state, Android phone-control permissions and current BLACKWAVE bridge state.

## Non-goals
Track A excludes iOverlay, xOverlay, xOverlay-phonecontrol, and any root, bootloader unlock or flashing steps. Repository consolidation, destructive migration, unrestricted Discord shell access and shared BLACKWAVE credentials are outside the platform plan. Confirm deployment through device evidence.
