// login.js
import { initializeApp } from "https://www.gstatic.com/firebasejs/11.0.1/firebase-app.js";
import {
  getAuth,
  signInWithEmailAndPassword,
} from "https://www.gstatic.com/firebasejs/11.0.1/firebase-auth.js";

// Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCFBVthl8KSOF4zR8Vkymjr72Vxff98h_s",
  authDomain: "coalminesapp.firebaseapp.com",
  databaseURL: "https://coalminesapp-default-rtdb.firebaseio.com",
  projectId: "coalminesapp",
  storageBucket: "coalminesapp.appspot.com",
  messagingSenderId: "749490773831",
  appId: "1:749490773831:web:c1119e7a06b52d1e9a7662",
  measurementId: "G-LTGH0F3RNK",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

// Login Function
async function loginUser(event) {
  event.preventDefault(); // Prevent the default form submission
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  try {
    // Authenticating user
    const userCredential = await signInWithEmailAndPassword(
      auth,
      email,
      password
    );
    alert("Login Successful!");
    window.location.href = "./Dashboard_page1.html"; // Redirect to dashboard after successful login
  } catch (error) {
    alert(`Error: ${error.message}`);
    console.error("Login error:", error); // Log the error for debugging
  }
}

// Event listener for the login form submission
document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  if (loginForm) {
    loginForm.addEventListener("submit", loginUser); // Attach the event listener
  }
});
