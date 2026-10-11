# Contributing to Elhaj Drill Pulse

Thanks for improving the project! Contributions and bug reports are welcome.

## Suggested workflow

1. Open an **Issue** describing the bug or intended change; avoid real company CSVs.
2. Create a branch (`fix/...` or `feature/...`) from `main`.
3. Make the smallest safe change. Keep browser-first operation and do not upload user data.
4. Test imports, coordinate systems, maps, calculations and report exports in Chromium, as applicable.
5. Open a Pull Request and document before/after behavior and limitations.

## Scientific standards

- Make descriptive, observational and interpolated results visually distinguishable.
- Avoid implying inferred structures, ore bodies, mineral resources or economic grades are established facts.
- Retain support for censored assays, unit checks and explicit CRS selection.
- When updating a geochemical statistic or spatial interpolation, include synthetic or open reproducible benchmarks.
- Do not include proprietary customer data or credentials in an issue, branch or commit.

## Versioning

Bug fixes: 1.0.1; additive features: 1.1.0; breaking changes: 2.0.0. Update `CHANGELOG.md` with each release.
