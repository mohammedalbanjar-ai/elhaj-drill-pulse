# ELHAJ DRILL PULSE · v1.0.0

**Open-source geological exploration workspace** | برنامج مفتوح المصدر لعرض وتحليل بيانات الاستكشاف المعدني

![Elhaj Drill Pulse desktop workspace](docs/workspace-desktop.png)

> **Public release v1.0.0**: this is the **first publicly named release**. It packages the features and modern UI developed during earlier internal prototypes. It does not imply all scientific methods have been independently validated.

## About | عن المشروع

**Elhaj Drill Pulse** helps exploration geologists import, visualize, compare and report geological and geochemical exploration data in an Arabic-friendly, desktop-first interface. It is a self-contained `index.html` application. Core imported-data processing is local in the browser.

يجمع البرنامج في مساحة واحدة الآبار الاستكشافية، والخنادق، والعينات السطحية، والتحليل الجيوكيميائي والخرائط والتقارير. يعمل البرنامج محلياً دون الحاجة إلى حساب أو خادم لحفظ بياناتك.

## Key features | المزايا

- Drillholes: collar/survey/geology/assay imports; logs, profiles, cross-sections, 3D views and QA/QC tools.
- Trenches: horizontal geological logs, coordinates, assay switching and satellite-location maps.
- Surface samples: point maps, multi-element assays, univariate, multivariate and PCA exploration.
- Spatial geochemistry: anomaly screening, supported concentration interpolation and follow-up clusters.
- Technical reports: export views and locally generate reports.
- Offline-first: the main application opens from `index.html`. **Satellite imagery requires network access** and may transmit the displayed geographic extent to the imagery provider.

## Quick start | التشغيل

1. Download this project and extract its files, or download only `index.html`.
2. Open `index.html` in an up-to-date Chromium-based browser.
3. Import your own CSVs using the relevant geology module.
4. Select the correct coordinate reference system (CRS), import appropriate assay units, and review data-quality warnings.
5. Export results explicitly when you choose. **Do not commit private exploration records to this public repository.**

**No customer/company datasets are included.** Sample-file placeholder headings in the UI are not real assay results.

### Online demonstration

After the repository owner enables GitHub Pages (`Settings → Pages → Deploy from a branch → main / (root)`), the site is available at:

`https://YOUR-USERNAME.github.io/elhaj-drill-pulse/`

Replace `YOUR-USERNAME` with your GitHub username. No live site has been published from this package yet.

## Versioning and future development

- `v1.0.0`: first public baseline.
- `v1.0.1`, `v1.0.2`: bug fixes and verification improvements.
- `v1.1.0`: backward-compatible enhancements.
- `v2.0.0`: substantial or breaking changes.

Future modifications should be made in a branch and proposed with a pull request before merging to `main`. See [`CONTRIBUTING.md`](CONTRIBUTING.md) and [`CHANGELOG.md`](CHANGELOG.md).

## Important geological limitations

- Spatial interpolations and follow-up clusters are **exploratory estimates**, not measured mineralization or resource estimates.
- Statistical association and PCA do not independently identify deposits, establish continuity, or justify mining decisions.
- Check CRS, assay units, censored / below-detection results, QA/QC, survey duplicates, and coordinate accuracy before relying on any interpretation.
- The scientific calculations and map rendering still require independent benchmark and device testing. This is **not certified resource-estimation software**.

## Privacy and map imagery

Imported CSVs are processed in the browser; the program does not automatically upload them to the GitHub repository. When satellite maps are enabled, external imagery requests disclose the geographic area viewed. Third-party basemap imagery **is not part of the open-source license** and is subject to its provider's terms and attribution.

## Repository structure

```text
index.html             Web application; no build required
app/                   Android WebView source project (debug build)
.github/workflows/     Optional GitHub Actions Android APK build
 docs/                  Screenshots
README.md              Project overview
START_GITHUB_AR.md     Beginner GitHub publishing guide (Arabic)
CHANGELOG.md           Public release history
CONTRIBUTING.md        How to propose changes
LICENSE                MIT license for original project code
THIRD_PARTY_NOTICES.md Bundled libraries and imagery restrictions
```

**Android note:** the source project builds a **debug APK**, not a verified Google Play release. Native Android and Windows wrappers have not been verified on every device. The web version is the baseline.

## License | الترخيص

The project-owned code is offered under the [MIT License](LICENSE), subject to the licenses of bundled third-party components. See [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md). Public repository means the source code is visible; do not upload non-public company data, credentials or private assets.
