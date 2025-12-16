
// GeneratePDF.js (top)
import { storage } from "./firebase.js";
import { ref, uploadBytes, getDownloadURL } from "https://www.gstatic.com/firebasejs/12.6.0/firebase-storage.js";


let app;
if (!getApps().length) {
  app = initializeApp(firebaseConfig);
} else {
  app = getApps()[0];
}
const storage = getStorage(app);

document
  .getElementById("GeneratePDFButton")
  .addEventListener("click", function () {
    const getDate = () => {
      const today = new Date();
      const day = today.getDate().toString().padStart(2, "0");
      const month = (today.getMonth() + 1).toString().padStart(2, "0");
      const year = today.getFullYear();
      return `${day}${month}${year}M`;
    };

    const getDateForPDF = () => {
      const today = new Date();
      const day = today.getDate().toString().padStart(2, "0");
      const month = (today.getMonth() + 1).toString().padStart(2, "0");
      const year = today.getFullYear();
      return `${day}-${month}-${year}-M`;
    };

    let todaysDateForPDf = getDateForPDF();
    let todaysDate = getDate();

    const { jsPDF } = window.jspdf;
    const element = document.getElementById("all-table-container");

    // Capture the content with html2canvas at a high resolution
    html2canvas(element, { scale: 2 }).then((canvas) => {
      const imgData = canvas.toDataURL("image/png");
      const pdf = new jsPDF("p", "mm", "a4");

      const imgWidth = 190;
      const pageHeight = 297;
      const marginTop = 10;
      const marginLeft = 10;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;
      let heightLeft = imgHeight;
      let position = marginTop + 10;

      pdf.setFontSize(24);
      pdf.setTextColor(0, 0, 0);
      pdf.text("Coal Mining Shift Log", pdf.internal.pageSize.width / 2, 15, {
        align: "center",
      });
      pdf.setFontSize(12);
      pdf.text(
        `Date: ${todaysDateForPDf}`,
        pdf.internal.pageSize.width - marginLeft,
        15,
        {
          align: "right",
        }
      );

      pdf.addImage(imgData, "PNG", marginLeft, position, imgWidth, imgHeight);
      heightLeft -= pageHeight - marginTop;

      while (heightLeft >= 0) {
        position = heightLeft - imgHeight;
        pdf.addPage();
        pdf.addImage(imgData, "PNG", marginLeft, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;
      }

      const pdfBlob = pdf.output("blob");

      uploadPDFToFirebase(pdfBlob, todaysDate); // Call Firebase upload function here

      // Save PDF directly
      pdf.save(`${todaysDate}.pdf`);
    });
  });

// Function to upload PDF to Firebase Storage
async function uploadPDFToFirebase(pdfBlob, fileName) {
  const storageRef = ref(storage, `pdfs/${fileName}.pdf`);
  try {
    const snapshot = await uploadBytes(storageRef, pdfBlob);
    const downloadURL = await getDownloadURL(snapshot.ref);
    console.log("PDF uploaded successfully! Download URL:", downloadURL);
    alert("PDF uploaded successfully! The download URL is ready.");
  } catch (error) {
    console.error("PDF upload failed:", error);
    alert("Upload failed! Please try again.");
  }
}
