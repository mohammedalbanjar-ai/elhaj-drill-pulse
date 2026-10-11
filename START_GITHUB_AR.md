# نشر Elhaj Drill Pulse v1.0.0 على GitHub: دليل للمبتدئين

## الطريقة المفضلة: GitHub Desktop (بدون أوامر)

1. حمّل **GitHub Desktop** من https://desktop.github.com وثبّته، ثم سجّل الدخول بالحساب الذي أنشأته.
2. فك ضغط ملف `Elhaj_Drill_Pulse_v1_GitHub_Project.zip` في مجلد واضح على الكمبيوتر.
3. داخل GitHub Desktop اختر **File → New repository**، وسمّه `elhaj-drill-pulse`. اختر مكان الحفظ (Local path). لا تضف ملفات عينة حقيقية. اضغط **Create repository**.
4. افتح مجلد المستودع المحلي الجديد من **Repository → Show in Explorer**، وانسخ **محتويات** المجلد المستخرج إليه، بما في ذلك المجلد المخفي `.github`. لا تنسخ ملف ZIP نفسه ولا مجلد المشروع كاملاً كمجلد فرعي. إن احتوى المجلد الجديد على README أو LICENSE افتراضيين، استبدلهما بنسختي الحزمة.
5. ارجع إلى GitHub Desktop. ستظهر الملفات في قائمة **Changes**. راجعها، واكتب `Initial public release v1.0.0` في Summary، ثم اضغط **Commit to main**.
6. اضغط **Publish repository**، واجعل الاسم `elhaj-drill-pulse`، ثم **أزل علامة** `Keep this code private` لكي يصبح المستودع **Public**. اضغط **Publish Repository**.
7. افتح **Repository → View on GitHub**. سترى الصفحة، وصورة البرنامج، والملفات المصدرية.

### نشر البرنامج كصفحة ويب

افتح المستودع على GitHub → **Settings → Pages** → تحت **Build and deployment** اختر `Deploy from a branch`، ثم `main` و`/(root)` → **Save**. قد يستغرق ظهور الموقع عدة دقائق.

عنوان الموقع المعتاد:

`https://YOUR-USERNAME.github.io/elhaj-drill-pulse/`

استبدل `YOUR-USERNAME` باسم حسابك الفعلي.

### وضع أول إصدار Release v1.0.0 (اختياري)

في المستودع على GitHub افتح **Releases → Draft a new release**، ثم أنشئ tag باسم **`v1.0.0`**، وعنوان الإصدار **`Elhaj Drill Pulse v1.0.0`**، وأضف ملخص المميزات قبل الضغط على **Publish release**. لا تنشر APK فعلياً قبل اختباره على الجهاز.

### عند تطوير البرنامج مستقبلاً

افتح GitHub Desktop، عدل الملفات داخل المجلد المحلي، اختبر التطبيق، ثم **Commit** وأخيراً **Push origin**. يُفضّل استخدام Branch للتعديلات الكبيرة والدمج بعد اختبارها.

### تنبيهات قبل النشر

- GitHub **Public** يعني أن الشفرة والملفات المنشورة يمكن للجميع الاطلاع عليها ونسخها وفق الترخيص.
- لا ترفع بيانات المشاريع الحقيقية أو ملفات الشركات (`.csv`, `.xlsx`, `.xlsm`, `.tif`) أو رموز API أو كلمات مرور.
- المكتبات المضمنة لها تراخيص خاصة؛ اقرأ `THIRD_PARTY_NOTICES.md`. الصور الفضائية المعروضة عند الاتصال ليست جزءاً من الملفات المنشورة.
- لا تقم برفع ملف ZIP وحده وتتوقع تشغيل الموقع. يجب أن يظهر `index.html` في **جذر** المستودع.
- رفع المشروع إلى حسابك يحتاج تسجيل دخولك أنت؛ هذه الحزمة ليست منشورة حالياً على حسابك.
