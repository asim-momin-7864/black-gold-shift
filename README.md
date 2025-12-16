# Black Gold Shift — README

> **Short:** My first hackathon project (revived). A small frontend app for logging daily coal-mining shift data, visualizing it with charts, and exporting a shift log PDF.
> Built with plain HTML/CSS/JS + Firebase (Firestore + Auth). I recently fixed it up so it runs again and added a simple seeder.

I’m a beginner — this repo is honest about that. I’ll show what I did, what’s working, and what I left disabled to keep the demo stable.

---

## Table of contents

* [Origin story](#origin-story)
* [What it does (features)](#what-it-does-features)
* [Quick start (run locally)](#quick-start-run-locally)
* [Seeding test data](#seeding-test-data)
* [Project structure (important files)](#project-structure-important-files)
* [Data model (example document)](#data-model-example-document)
* [What I changed in the revival](#what-i-changed-in-the-revival)
* [Limitations & known issues](#limitations--known-issues)
* [Security & credentials](#security--credentials)
* [How to re-enable cloud upload (notes)](#how-to-re-enable-cloud-upload-notes)
* [Screenshots / diagrams / gif (placeholders)](#screenshots--diagrams--gif-placeholders)
* [If you want to help / next steps](#if-you-want-to-help--next-steps)
* [License](#license)

---

## Origin story

I built this during my first-ever hackathon with only HTML/CSS/JS knowledge. We used Firebase because it let us ship auth, database and file upload without a backend. The project won the hackathon for solving a real coal-mining shift reporting problem.

Two years later I revived it to make it demo-ready again. I fixed many bugs, consolidated Firebase initialization, added a seeder, and kept things simple — no backend, no production infra.

---

## What it does (features)

* Login / Signup (Firebase Auth)
* Dashboard with charts visualizing daily shift metrics (Chart.js)
* Generate a detailed shift log as a PDF (jsPDF + html2canvas) and download locally
* Seeder page to populate demo Firestore documents (so charts and PDF show realistic data)
* (Disabled) Cloud upload of PDFs — commented out with instructions to restore

---

## Quick start (run locally)

You only need a browser + static server.

1. Clone the repo:

   ```bash
   git clone <repo-url>
   cd black-gold-shift
   ```

2. Put your Firebase web config in `firebase.js` (project root). This is the single file that initializes Firebase.

3. Serve files over HTTP (ES modules need this):

   * Using Python 3:

     ```bash
     python -m http.server 8000
     ```
   * Or use VS Code Live Server.

4. Open:

   * Seeder: `http://localhost:8000/seed.html` → click **Seed Demo Data**
   * App: `http://localhost:8000/index.html` → login → Dashboard → Analysis

---

## Seeding test data

Use `seed.html` to create demo documents in Firestore. Seeder writes both simple numeric fields (for charts) and structured rows (for PDF tables). The seeder creates:

* Today (DDMMYYYYM)
* Yesterday
* A sample historical ID

This makes dashboard charts and PDF tables work without manual data entry.

---

## Project structure (important files)

* `index.html`, `login.html`, `sign_up.html` — UI pages
* `firebase.js` — centralized Firebase initializer (update with your web config)
* `chartConfig.js` — all chart rendering code (safe fallbacks included)
* `Dashboard_page2.html` / `Dashboard_page2.js` — PDF / Handlebars template rendering
* `GeneratePDF.js` — PDF generation and local download (cloud upload is commented)
* `seed.html` — demo data seeder (black & yellow themed)
* `firebase.json` — hosting config (if you want to deploy)

---

## Data model (example document)

One Firestore document is stored at `ShiftLog/{DDMMYYYYM}`. Example (shortened):

```json
{
  "washery_Cleancoal": 1200,
  "washery_Rawcoal": 1500,
  "dispatch_Rail_Actual": 420,
  "dispatch_Road_Actual": 310,
  "overburden_Shovel_Solidquantity": 1100,
  "overburden_excavator": [{ "si":1, "no":"EX-01", "benchNo":"B1", "qbSolid":1100, "rehandling":160, "total":1260 }],
  "coal_tipper": [{ "si":1, "nameNo":"TP-C1", "benchNo":"CB1", "without": {"trips":60, "coalQty_T":900}, "with": {"trips":58, "coalQty_T":880}}]
  // ...many more flat fields and arrays (see seed.html)
}
```

Charts read the flat numeric fields. The PDF/Handlebars needs some flat keys (these are mapped from the arrays in the seeder so the PDF renders correctly).

---

## What I changed in the revival

* Consolidated all Firebase initialization into `firebase.js` (single source).
* Fixed mixed/duplicate Firebase SDK imports and versions (CDN ESM v12.x).
* Rewrote `chartConfig.js` to handle missing fields safely (`|| 0`) so charts don't crash.
* Added a `seed.html` that writes both the flat fields and structured arrays, and maps array first-row values to the flat keys required by the PDF template.
* Commented out the cloud-upload code in `GeneratePDF.js` instead of deleting it, with clear instructions to re-enable later.
* Added notes/comments across files for future maintainers.

I had help from a coding assistant while doing the revive. I used that help to debug and patch things quickly — the code and decisions are mine, but I’m being honest that I had guidance.

---

## Limitations & known issues

I kept this intentionally minimal. Things left as-is:

* **UI is not responsive.** It was built as a desktop dashboard for the hackathon. Mobile/tablet views are not fully tested.
* **PDF size can be large (MBs).** The PDF is created from an HTML snapshot (html2canvas) at higher scale for legibility; that makes file sizes bigger. You can reduce `scale` or lower image quality if you need smaller files.
* **Cloud PDF upload is disabled.** The upload code is commented in `GeneratePDF.js` and documented. It was disabled because free Firebase Storage is no longer available for this project.
* **No backend / server.** This is a static frontend project. If you want persistent public links for PDFs, you would need to add a server (S3 presigned URLs or an email service), which I chose not to do for this demo.
* **Not all functions are line-by-line documented.** I understand the overall flow and can explain how major parts work, but I didn’t rewrite every function to be perfect — this was kept pragmatic.

---

## Vulnerabilities & notes

* The Firebase web config is in the repo by design (it’s a client-side API key). These keys do **not** give admin access. Security depends on Firestore / Storage rules.
* I recommend **setting Firestore rules** appropriately if you deploy:

  * For demo: allow read, deny public writes.
  * For production: require auth and validate input.
* If you add server-side code (AWS keys, SendGrid, service accounts), **do not** commit those secrets. Use `.env` and `.gitignore` and store secrets in CI/CD settings.

---

## How to re-enable cloud upload (notes)

If you or a future teammate want to restore the PDF upload to cloud storage:

**Quick Firebase restore**

1. Re-enable Firebase Storage in Firebase Console.
2. Un-comment the `uploadPDFToFirebase()` function and its call in `GeneratePDF.js`. Ensure `firebase.js` exports `storage`.
3. Optionally save the download URL to the ShiftLog doc for mobile app retrieval.

**Better (recommended) long-term**

* Replace upload with S3 presigned uploads or Cloudflare R2 + short presigned GET links. That needs a small serverless function (I can provide code if you want later).

I commented the exact steps in `GeneratePDF.js`.

---

## Screenshots / data flow diagram / gif (keep it simple)

I’m not adding big videos — just small images and a tiny gif for the README. Add these files in the repo and reference them:

* `screenshots/dashboard.png` — dashboard showing all charts (one clean desktop shot)
* `screenshots/pdf-preview.png` — a screenshot of the generated PDF open in a viewer
* `screenshots/seeder.png` — the seeder page (optional)
* `diagrams/data-flow.png` — simple diagram (client → Firestore → PDF generation)
* `gifs/generate-pdf.gif` — short gif showing "Generate Log" → "Download PDF"

You can create the gif with a simple screen recorder (like ShareX or the built-in OS recorder) and add it to `gifs/`. In the README you can embed images like:

```md
![Dashboard screenshot](screenshots/dashboard.png)
![PDF preview](screenshots/pdf-preview.png)
![Data flow](diagrams/data-flow.png)

![Generate PDF gif](gifs/generate-pdf.gif)
```

(Keep images small and cropped; one dashboard shot + one PDF shot + one small gif is enough.)

---

## If you want to help / next steps

If you want to iterate later or accept help:

* Make the UI responsive (CSS grid / media queries)
* Add a tiny backend (serverless) for PDF storage or email delivery (I can supply a minimal serverless presign/send example)
* Improve Firestore schema & validation for production use

I can also prepare a short post draft (X/LinkedIn) once you’re ready to share.

---

## License

MIT — do what you want, but please keep the origin note if you reuse this project.

---

If you want, I’ll:

* Paste this README as a ready-to-commit `README.md`, or
* Add a short X/LinkedIn post draft and screenshots text you can copy-paste.

Which one next?
