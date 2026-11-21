# FR3K ONE — Module and Job Contracts
Draft v0.1. The examples describe proposed document shapes. Check the implementation for available endpoints and capabilities.

## Track A module scope

- The FR3K Overlay / Filament character is an independent module. HUD remains the operator shell.
- Stock Mora and mora-texture-lab are research-only. Do not deploy either to the daily NX809J.
- iOverlay, xOverlay and xOverlay-phonecontrol are out of scope.

## Module manifest
    {
      "schema_version": "1.0",
      "module_id": "org.fr3k.example",
      "module_version": "0.1.0",
      "display_name": "Example Module",
      "entrypoints": ["android", "discord", "web"],
      "capabilities": [],
      "permissions": [],
      "health_endpoint": "/health",
      "minimum_fabric_api": "1.0"
    }

## Job request
    {
      "schema_version": "1.0",
      "job_id": "stable-uuid",
      "idempotency_key": "origin-scoped-key",
      "origin": "hud|cortex|voice|plugin",
      "requested_by": "user-or-service-principal",
      "target_ids": ["device-id"],
      "capability_id": "device.observe",
      "capability_version": "1.0",
      "input": {},
      "authority_class": "OBSERVE",
      "dependencies": [],
      "deadline": "RFC3339 timestamp",
      "approval_ref": null
    }

## Required invariants
1. Duplicate idempotency key + same canonical payload returns the existing job.
2. Same idempotency key + different payload is rejected.
3. Every target and capability is resolved against the trusted registry.
4. Authority class cannot be increased by a worker or module.
5. Approval is bound to job, target, capability, input digest, expiry and approver.
6. A worker lease has expiry and fencing token; stale workers cannot write results.
7. Terminal states are immutable.
8. Evidence is append-only and content-addressed.
9. A missing verifier produces UNKNOWN/BLOCKED, never implicit success.
10. Cancellation is a request; report whether the worker confirmed termination.

## Android permissions and job handling

- Keep the daily NX809J bootloader locked. Root, bootloader unlock and flashing are outside the supported Android job scope, including for approved jobs.
- Use Shizuku by default for supported actions that require it. Verify that it is available and that the user has granted access before dispatching those actions.
- Reject out-of-scope actions. Mark a supported job BLOCKED if its required grant is unavailable or revoked. Record the missing prerequisite in the job result.

## Event envelope
    {
      "schema_version": "1.0",
      "job_id": "stable-uuid",
      "sequence": 1,
      "occurred_at": "RFC3339 timestamp",
      "state": "VERIFYING",
      "progress": null,
      "message": "Verifier started",
      "worker_id": "worker-id",
      "evidence_refs": []
    }

## Evidence record
    {
      "evidence_id": "sha256-or-uuid",
      "kind": "stdout|stderr|screenshot|log|measurement|artifact|attestation",
      "sha256": "hex-digest",
      "media_type": "text/plain",
      "captured_at": "RFC3339 timestamp",
      "source_device": "device-id",
      "storage_uri": "private-evidence-uri",
      "redaction_status": "not-required|pending|redacted"
    }

## Authority classes
- OBSERVE: read-only.
- AUTO: bounded, reversible, pre-authorised software operations.
- APPROVAL: requires a current, scoped approval.
- PHYSICAL: separately governed physical/RF/actuator operations; never inferred from AUTO.
