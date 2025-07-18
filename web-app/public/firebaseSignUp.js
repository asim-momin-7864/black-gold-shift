// firebaseConfig.js
import { initializeApp } from "https://www.gstatic.com/firebasejs/11.0.1/firebase-app.js";
import { getAuth } from "https://www.gstatic.com/firebasejs/11.0.1/firebase-auth.js";

const firebaseConfig = {
    apiKey: "AIzaSyCFBVthl8KSOF4zR8Vkymjr72Vxff98h_s",
    authDomain: "coalminesapp.firebaseapp.com",
    databaseURL: "https://coalminesapp-default-rtdb.firebaseio.com",
    projectId: "coalminesapp",
    storageBucket: "coalminesapp.appspot.com",
    messagingSenderId: "749490773831",
    appId: "1:749490773831:web:c1119e7a06b52d1e9a7662",
    measurementId: "G-LTGH0F3RNK"
};

// Initialize Firebase and export the auth instance
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
