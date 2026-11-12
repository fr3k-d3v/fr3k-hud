# FR3K capability evidence contract

This repository is one independently developed FR3K flagship. Its source-of-truth capability declaration is `.fr3k/flagship-status.json`.

## When implementation changes

Whenever work changes the implementation, test coverage, deployment state, operational readiness or scope of a catalogued capability:

1. Update the matching capability entry in `.fr3k/flagship-status.json` in the same change.
2. Keep the existing capability ID unchanged. The manifest must contain the complete exact capability set expected by the central FR3K portfolio catalog.
3. Select the honest source state: `implemented`, `in-development`, `source-reported`, `planned`, `not-tested`, `not-implemented`, `blocked`, `not-deployed` or `not-in-scope`.
4. Add one or more repository-relative evidence references. Each reference must point to an existing regular file in this repository and include a type and a concise note.
5. Update `updatedAt` to an ISO-8601 UTC timestamp when the declaration changes.
6. Run the relevant tests and let the FR3K Capability Manifest workflow validate the change.

## Evidence reference types

Allowed types are `source`, `test`, `documentation`, `workflow`, `artifact`, `acceptance`, `runtime`, `hardware` and `roadmap`.

References must be safe repository-relative file paths. Do not use absolute paths, parent-directory traversal, external URLs, symlinks, secrets or personal data. If a referenced file moves or is deleted, update the manifest in the same pull request.

## Authority boundaries

- A source manifest is a declaration by this repository, not an independent verification.
- The source manifest must never declare `verified`. CI rejects that state.
- Passing CI proves that the manifest is complete and its evidence paths exist. It does not prove that the evidence semantically establishes the capability.
- Independent `verified` status remains controlled by the curated FR3K portfolio evidence review.
- Do not claim physical-device acceptance from simulation, build success, a heartbeat or a source-code reference alone.

The workflow validates every push and pull request. Omarchy reconciles the default-branch manifest and checks every evidence path against the exact Git commit that supplied the manifest. The public portfolio receives only the source state, evidence-reference count and manifest timestamp; private evidence paths and contents are not copied into the public feed.

Central operating contract: https://github.com/fr3kchy/fr3k.dev/blob/main/docs/FLAGSHIP-STATUS-AUTOMATION.md
