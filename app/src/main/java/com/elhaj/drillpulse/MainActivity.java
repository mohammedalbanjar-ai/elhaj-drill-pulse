package com.elhaj.drillpulse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Collections;

/**
 * Local-only launcher for the offline Drill Pulse workbench.
 * INTERNET permission only for user-enabled Esri Satellite with a Google host allowlist; no telemetry.
 * CSV import and optional user-initiated report exports use Android's document picker.
 */
public final class MainActivity extends Activity {
    private static final int PICK_CSV = 4001;
    private static final int SAVE_USER_FILE = 4002;
    private static final String HOME = "file:///android_asset/index.html";
    private WebView web;
    private ValueCallback<Uri[]> fileCallback;
    private byte[] pendingExport;
    private volatile boolean satelliteNetworkAllowed = false;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        web = new WebView(this);
        web.setBackgroundColor(0xff101927);
        web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setContentView(web);

        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true); // The 3D workstation depends on JS.
        settings.setDomStorageEnabled(false); // No persistent project storage.
        settings.setDatabaseEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(true); // file input uses Android document provider.
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);
        settings.setMediaPlaybackRequiresUserGesture(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setTextZoom(100);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("UTF-8");
        web.setOverScrollMode(View.OVER_SCROLL_NEVER);

        web.addJavascriptInterface(new Exports(), "ElhajAndroid");
        web.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return !HOME.equals(request.getUrl().toString());
            }
            @Override public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest req) {
                String scheme = req.getUrl().getScheme();
                if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                    String host = req.getUrl().getHost();
                    if ("https".equalsIgnoreCase(scheme) && satelliteNetworkAllowed && isSatelliteHost(host)) {
                        return super.shouldInterceptRequest(view, req);
                    }
                    return new WebResourceResponse("text/plain", "UTF-8", 403,
                        "Remote content blocked", Collections.emptyMap(),
                        new ByteArrayInputStream(new byte[0]));
                }
                return super.shouldInterceptRequest(view, req);
            }
            @Override public void onPageFinished(WebView view, String url) {
                if (HOME.equals(url)) view.evaluateJavascript(EXPORT_HOOK, null);
            }
        });
        web.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView view,
                    ValueCallback<Uri[]> callback, FileChooserParams params) {
                if (fileCallback != null) fileCallback.onReceiveValue(null);
                fileCallback = callback;
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,
                    params.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE);
                try { startActivityForResult(intent, PICK_CSV); }
                catch (Exception ex) {
                    fileCallback.onReceiveValue(null);
                    fileCallback = null;
                    Toast.makeText(MainActivity.this, "تعذر فتح الملفات", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });
        web.loadUrl(HOME);
    }

    /**
     * The original HTML exports via dynamically generated Blob URLs and anchor clicks.
     * WebView doesn't automatically download blob: URLs, so a local-only script captures
     * ONLY explicit <a download> actions and opens Android's save picker.
     */
    private static final String EXPORT_HOOK =
        "(function(){if(window.__elhajAndroidExport)return;window.__elhajAndroidExport=true;" +
        "var refs=new Map(),cr=URL.createObjectURL.bind(URL),rr=URL.revokeObjectURL.bind(URL);" +
        "URL.createObjectURL=function(b){var u=cr(b);refs.set(u,b);return u};" +
        "URL.revokeObjectURL=function(u){setTimeout(function(){refs.delete(u);rr(u)},5000)};" +
        "document.addEventListener('click',function(e){" +
        "var a=e.target.closest&&e.target.closest('a[download]');if(!a)return;" +
        "var u=a.href,b=refs.get(u),name=a.download||'Elhaj_Report.html';" +
        "if(!b&&u.indexOf('data:')!==0)return;" +
        "e.preventDefault();e.stopImmediatePropagation();" +
        "if(b){var r=new FileReader();r.onload=function(){" +
        "var s=String(r.result),k=s.indexOf(',');" +
        "ElhajAndroid.saveFile(name,s.slice(k+1));};r.readAsDataURL(b)}" +
        "else{var k=u.indexOf(',');if(k>0)ElhajAndroid.saveFile(name,u.slice(k+1))}" +
        "},true);})()";

    private static boolean isSatelliteHost(String h) {
        if (h == null) return false;
        String[] domains = {"unpkg.com", "cdn.jsdelivr.net", "services.arcgisonline.com"};
        for (String d : domains) if (h.equals(d) || h.endsWith("." + d)) return true;
        return false;
    }

    public final class Exports {
        @JavascriptInterface public void setSatelliteMode(boolean enabled) {
            satelliteNetworkAllowed = enabled;
        }
        @JavascriptInterface public void saveFile(String name, String base64) {
            if (base64 == null || base64.length() > 35_000_000) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                    "الملف كبير جداً للحفظ من هذه النسخة", Toast.LENGTH_LONG).show());
                return;
            }
            byte[] decoded;
            try { decoded = Base64.decode(base64, Base64.DEFAULT); }
            catch (Exception ex) { return; }
            final String cleaned = (name == null || name.isEmpty()) ? "Elhaj_Report.html" :
                name.replaceAll("[^\\p{L}\\p{N}._ -]", "_");
            final String filename = cleaned.substring(0, Math.min(cleaned.length(), 110));
            runOnUiThread(() -> {
                pendingExport = decoded; // Data exists only while awaiting user's Save action.
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                String ext = filename.toLowerCase();
                String mime = ext.endsWith(".csv") ? "text/csv" :
                    ext.endsWith(".png") ? "image/png" :
                    ext.endsWith(".json") ? "application/json" : "text/html";
                intent.setType(mime);
                intent.putExtra(Intent.EXTRA_TITLE, filename);
                try { startActivityForResult(intent, SAVE_USER_FILE); }
                catch (Exception ex) {
                    pendingExport = null;
                    Toast.makeText(MainActivity.this, "تعذر فتح نافذة الحفظ", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CSV) {
            if (fileCallback == null) return;
            Uri[] results = null;
            if (resultCode == RESULT_OK && data != null) {
                if (data.getClipData() != null) {
                    int n = data.getClipData().getItemCount();
                    results = new Uri[n];
                    for (int i = 0; i < n; i++) results[i] = data.getClipData().getItemAt(i).getUri();
                } else if (data.getData() != null) results = new Uri[]{data.getData()};
            }
            fileCallback.onReceiveValue(results);
            fileCallback = null;
        }
        if (requestCode == SAVE_USER_FILE) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null && pendingExport != null) {
                try (OutputStream out = getContentResolver().openOutputStream(data.getData())) {
                    if (out != null) {
                        out.write(pendingExport);
                        Toast.makeText(this, "تم حفظ التقرير بناءً على طلبك", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "تعذر حفظ الملف", Toast.LENGTH_LONG).show();
                }
            }
            pendingExport = null;
        }
    }

    @Override public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setTitle("إنهاء الجلسة")
            .setMessage("إغلاق التطبيق سيمسح الآبار والبيانات المستوردة من الجلسة الحالية. هل تريد الخروج؟")
            .setNegativeButton("إلغاء", null)
            .setPositiveButton("خروج", (d, i) -> { eraseSession(); finish(); })
            .show();
    }

    private void eraseSession() {
        satelliteNetworkAllowed = false;
        if (web == null) return;
        web.loadUrl("about:blank");
        web.clearHistory();
        web.clearCache(true);
        web.clearFormData();
        WebStorage.getInstance().deleteAllData();
    }

    @Override protected void onDestroy() {
        if (fileCallback != null) { fileCallback.onReceiveValue(null); fileCallback = null; }
        pendingExport = null;
        if (web != null) {
            eraseSession();
            web.removeJavascriptInterface("ElhajAndroid");
            web.destroy();
            web = null;
        }
        super.onDestroy();
    }
}
