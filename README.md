# Black-Gold-Shift — Shift Management System for Coal Mines

> A beginner-friendly Android + Web project that digitizes shift handover logs, generates PDF handover reports, and improves safety & handover clarity for coal-mine operations.

![License](https://img.shields.io/badge/License-MIT-yellow.svg) ![Language](https://img.shields.io/badge/Language-Java-brightgreen.svg) ![Backend](https://img.shields.io/badge/Backend-Firebase-orange.svg)

---

## Table of contents

* [About](#about)
* [Features](#features)
* [Screenshots](#screenshots)
* [Architecture](#architecture)
* [Files I inspected](#files-i-inspected)
* [Getting started (Android)](#getting-started-android)
* [Firebase setup](#firebase-setup)
* [Build & run (CLI)](#build--run-cli)
* [Project structure & important files](#project-structure--important-files)
* [Tips for beginners](#tips-for-beginners)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)

---

# About

Black-Gold-Shift is a simple mobile + web system for coal-mine shift handovers. Supervisors log shift activities (overburden, coal mined, drill, explosives, etc.), the data is stored centrally (Firebase), and the app can auto-generate PDF handover reports for the next shift and for managers / ERP integration.

This project was developed as a PBL (Project Based Learning) and aligns with safety/operational goals described in the project synopsis. 

---

# Features

* Firebase Authentication (Email/password)
* Create / Edit / View shift logs (Overburden, Coal Mined, Drill, Explosives, etc.)
* Store structured logs in Firestore (and/or Realtime DB)
* Auto-generate PDF handover reports (for offline viewing / audits)
* Simple web dashboard for managers (optional)
* DGMS-aware Safety Management Plan integration (SMP) and role-based access. 

---

# Screenshots

> **Note:** Add your screenshots in `screenshots/` (root). The README below references those filenames — add actual images with these names.

Recommended filenames:

```
screenshots/
  01_login.png
  02_date_shift.png
  03_dashboard.png
  04_explosive_info.png
  05_coalmined.png
  06_drill_info.png
  07_architecture.png
  08_flowchart.png
```

Embed these in README (example):

```md
## Screenshots

![Login](screenshots/01_login.png)
![Date & Shift](screenshots/02_date_shift.png)

![Dashboard](screenshots/03_dashboard.png)
![Explosive Info](screenshots/04_explosive_info.png)

![CoalMined](screenshots/05_coalmined.png)
![Drill Info](screenshots/06_drill_info.png)
```

---

# Architecture

High-level architecture uses Firebase as the central backend:

* Firebase Auth for login
* Firestore / Realtime DB for storing logs
* Cloud Functions (optional) to create PDFs / server logic
* Hosting for the web dashboard / ERP connectors

Place the architecture & flowchart images in `screenshots/` and include them here:

```md
## Architecture

![Architecture](screenshots/07_architecture.png)
![Flowchart](screenshots/08_flowchart.png)
```

---

# Files I inspected

I reviewed the core app entry files and the PBL synopsis to prepare this README and suggestions:

* `AndroidManifest.xml` — app entry, permissions and activity declarations. 
* `MainActivity.java` — splash / launcher flow and starter logic. 
* `activity_main.xml` — main layout sample & UI elements. 
* Project synopsis / PBL document (requirements, goals, and testing notes). 

---

# Getting started (Android)

## Prerequisites

* Android Studio (latest stable)
* JDK 11+ (or as required by your Gradle)
* Android device or emulator
* Firebase project (you will need `google-services.json`)

## Steps to run locally

1. **Clone the repo**

```bash
git clone https://github.com/YOUR_USERNAME/black-gold-shift.git
cd black-gold-shift
```

2. **Open in Android Studio**

* File → Open → select the project root
* Let Gradle sync and resolve dependencies

3. **Add Firebase config**

* Create a Firebase project, add an Android app with the same package name as in `AndroidManifest.xml`.
* Download `google-services.json` and place it at `app/google-services.json`.

4. **Build & Run**

* Select device / emulator → Run
  or run from terminal (see below).

---

# Firebase setup

1. Go to [Firebase Console](https://console.firebase.google.com/) → Create project.
2. Add Android app:

   * Package name must match `AndroidManifest.xml`. 
   * Download `google-services.json` → put into `app/`.
3. Enable **Authentication** → Email / Password (or other providers you plan to use).
4. Create **Firestore** (or Realtime DB). Start in test mode during development, then secure with rules before deployment.
5. (Optional) Add **Cloud Functions** for server-side PDF generation and scheduled tasks.
6. (Optional) Configure **Hosting** for the web dashboard.

---

# Build & run (CLI)

From project root:

```bash
# build debug APK
./gradlew assembleDebug

# install on a connected device
./gradlew installDebug
```

---

# Project structure (example)

```
black-gold-shift/
├─ app/
│  ├─ src/main/AndroidManifest.xml
│  ├─ java/.../MainActivity.java
│  ├─ res/layout/activity_main.xml
│  └─ google-services.json (local)
├─ web-app/              # optional web dashboard
├─ screenshots/
├─ docs/
└─ README.md
```

Important: keep `google-services.json` out of public repos if you prefer — add to `.gitignore` or provide a sample `google-services.json.example`.

---

# Tips for beginners (practical)

* **Text sizes:** use `sp` for `android:textSize`, `dp` for padding/margins.
* **Accessibility:** add `android:contentDescription` to `ImageView`s.
* **Layouts:** `ConstraintLayout` gives better responsiveness; `LinearLayout` is fine for simple screens.
* **Vector drawables:** prefer vectors (`.xml`) for icons to reduce APK size.
* **Security:** never commit API keys or `google-services.json` if repository is public — either keep local or use environment variables.
* **.gitignore** (basic):

```
*.iml
/.gradle
/local.properties
/.idea
/build
/app/build
```

---

# Contributing

1. Fork the repo
2. Create a feature branch: `git checkout -b feature/awesome`
3. Commit changes: `git commit -m "Add awesome feature"`
4. Push and open a PR

Keep changes focused and include screenshots for UI modifications.

---

# Common troubleshooting

* **Gradle sync fails:** update Gradle wrapper and Android Gradle plugin to compatible versions.
* **Firebase auth issues:** ensure package name matches and SHA-1 (if using Google sign-in) is configured in Firebase console.
* **APK not installing:** check `minSdkVersion` and device compatibility.

---

# Future enhancements (suggested)

* Role-based permissions (fine-grained supervisor vs manager roles)
* Offline caching with local DB (Room) & sync when online
* CSV export in addition to PDF
* Scheduled reports, email distribution, or ERP webhooks
* Multi-language support (i18n)

---

# License

This project is provided as a starter. Add a `LICENSE` file (MIT recommended for student projects).

---

# Contact

If you want help adding screenshots, creating badges, or generating a PPT from this README, tell me which one and I’ll generate it next.

---

**Ready to use:** copy the full contents of this file into `README.md`, add your screenshots to `screenshots/` using the filenames above, then run:

```bash
git add README.md screenshots/*
git commit -m "Add README and screenshots"
git push origin main
```

Good luck — this README is written to be **clean, beginner-friendly, and ready for submission**.
