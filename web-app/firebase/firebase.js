// firebase.js  (CDN ESM — static site)
// Put this at project root (same folder as your html files) and update the firebaseConfig with your new keys.

import { initializeApp } from "https://www.gstatic.com/firebasejs/12.6.0/firebase-app.js";
import { getAuth } from "https://www.gstatic.com/firebasejs/12.6.0/firebase-auth.js";
import { getFirestore } from "https://www.gstatic.com/firebasejs/12.6.0/firebase-firestore.js";
import { getStorage } from "https://www.gstatic.com/firebasejs/12.6.0/firebase-storage.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/12.6.0/firebase-analytics.js"; // optional

const firebaseConfig = {
  apiKey: "AIzaSyDf8dZC4GY-TuMTtk23DKkcEeAUxdiIfdU",
  authDomain: "black-gold-shift.firebaseapp.com",
  projectId: "black-gold-shift",
  storageBucket: "black-gold-shift.firebasestorage.app",
  messagingSenderId: "1075088195518",
  appId: "1:1075088195518:web:e1b8f5ebc4cfffb88043ef",
  measurementId: "G-4ZSTES71WK"
};

const app = initializeApp(firebaseConfig);

// optional analytics (only works in browser environments)
let analytics;
try { analytics = getAnalytics(app); } catch (e) { /* analytics may fail in some contexts — safe to ignore */ }

export const auth = getAuth(app);
export const db = getFirestore(app);
export const storage = getStorage(app);
export default app;
