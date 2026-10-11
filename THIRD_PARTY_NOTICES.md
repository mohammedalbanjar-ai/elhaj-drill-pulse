# Third-party libraries and imagery

The standalone `index.html` contains bundled third-party JavaScript code and retains embedded license notices. The repository `LICENSE` applies to original Elhaj Drill Pulse code **only**, not as a relicense of third-party assets.

Known components identified in the HTML bundle:

- **plotly.js v3.3.1**, Copyright 2012–2025 Plotly, Inc. — MIT. See https://github.com/plotly/plotly.js/blob/master/LICENSE.txt
- **MapLibre GL JS v4.7.1** (included in Plotly's browser bundle), BSD 3-Clause. See https://github.com/maplibre/maplibre-gl-js/blob/v4.7.1/LICENSE.txt
- Other transitive dependencies may appear inside the Plotly bundle and have their own credits; retain the original embedded notices. **License audit should be completed before commercial redistribution**.

Live **Esri World Imagery** (and any map provider's basemaps) is served over the network and is **not included** in this repository. Access, attribution and any export/use restrictions follow provider terms. Do not bulk-download or redistribute basemap imagery without authorization.

This is an initial notice, not a claim that every bundled transitive dependency or image resource has been exhaustively audited.
