# FR3K HUD × Voice Studio — E01–E12 integration contract
Status: DRAFT / NOT DEPLOYED. 2026-11-08 (Australia/Brisbane).

## Canonical authorities
- Development planning, PRs and sanitized evidence: GitHub. Voice Studio master: https://github.com/fr3k-d3v/fr3k-xiaozhi-claw/issues/34
- Authorized logical task lifecycle: existing FR3K gateway, with authenticated ingress and immutable event history.
- Remote dispatch and actual command execution: FR3K Fleet only. Gateway maps logical job ID to Fleet job ID and keeps a single dispatcher/retry owner.
- HUD: Android UI, observation, capability display, proposal/approval UI and task status. HUD must NOT independently schedule Fleet or falsely assert verification.
- Creature Overlay: persona, STT/TTS, transcripts and task rendering. Speech turn is not the task lifetime.
- Independent observer/verifier: evaluates postconditions from raw evidence and records separate verification status.

## Twelve enhancement integration gates
| ID | Capability | HUD ownership | Exit gate |
|---|---|---|---|
| E01 | App Learning | Consent and discover UI | Supported app/version/permission and procedure provenance |
| E02 | Shadow Learning | Explicit opt-in capture UI | Sensitive screens excluded; demonstrations reviewed |
| E03 | Screen Awareness | Foreground app/context and UI observations | Window/package bound; ambiguous/stale target refused; postcondition |
| E04 | Task Time Travel | Durable event timeline viewer | Process death/reconnect replay; hashes, cursor, no volatile-only log |
| E05 | Multi-Agent Problem Solving | Propose/review delegated plan | Isolated workers; dependencies; separate independent review |
| E06 | Intelligent Job Placement | Display Fleet resources and rationale | Real capacity + permissions + success receipts, not just online |
| E07 | Task Handover | Handover controls | Checkpoint/fencing/lease transfer, no duplicate execution |
| E08 | Self-Repairing Automation | Repair approval and proof UI | Diagnosis; bounded patch; tests; rollback |
| E09 | Bounded Autonomy | Mode and approval UI | Server-enforced OBSERVE/AUTO/APPROVAL/PHYSICAL boundaries |
| E10 | Offline Intelligence | Offline state and local-only actions | Persistent queued intent; conflict-safe reconciliation |
| E11 | Universal Interaction | HUD orb/keyboard/voice/creature unified task cards | All surfaces reference same task ID/permission |
| E12 | Independent Verification | Display predicate/results/evidence | Distinguish accepted/succeeded/verified/unknown |

## Minimum typed protocol (proposed; version after XC-003 ADR)
IntentProposal{schema_version,client_request_id,conversation_id,goal,target_ref,capability_id,args,postcondition,deadline}
CapabilitySnapshot{capability_id,version,argument_schema,target,observed_at,expires_at,privilege_evidence,availability}
ApprovalGrant{actor_ref,normalized_action_digest,target,policy_version,expires_at,nonce_single_use}
TaskHandle{gateway_job_id,idempotency_key,correlation_id,event_cursor,optional_fleet_job_id}
ProgressEvent{job_id,seq,stage,description}
ExecutionReceipt{job_id,adapter_version,operation_digest,fencing_token,exit_status,evidence_refs}
VerificationResult{job_id,predicate,observation,observer_version,evidence_sha256,status}

Use explicit non-success error states on HTTP failures. Block unknown fields, stale capability snapshots, invalid grants, changed payload idempotency conflicts and replays. Missing postcondition evidence => UNKNOWN, never VERIFIED.

## Priority fixes before remote jobs
1. In HermesProvider, never claim a failed message was 'saved locally' or 'will retry' without a real durable outbox. Surface failure as failure.
2. In TermuxBridge, do not concatenate caller inputs into shell commands without validated typed arguments, strict quoting and explicit permission; arbitrary Python execution is not a default safe named job.
3. VoiceStudio OverlayService and AndroidActionBridge: Boolean action API acceptance is not proof of effect. Show ACCEPTED, then verified only after separate observation.
4. Audit publicly exposed broadcast actions and network endpoint handling; enforce sender trust and target isolation. Capability registry does not grant authority.
5. Freeze/preserve existing dirty X1 trees; work in isolated branches. Never commit secrets, transcripts, screenshots or private topology.

## Release gating
A reviewed PR and green unit tests are necessary, not sufficient. Verify signed APK identity/hash, microphone/STT/TTS and user-visible transcript continuity, network failures, duplicate task submission, cancellation/revocation, real-phone postconditions, rollback, and power/network churn. A Git commit is not an APK deployment, a heartbeat is not progress, and a model response is not verified hardware state.

State of E01–E12 is OPEN/UNKNOWN until independently linked acceptance evidence exists. This document is a design/implementation contract, not proof of deployment.
