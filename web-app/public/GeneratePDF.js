// GeneratePDF.js
// NOTE: PDF generation + local download is active.
// Historically this file also uploaded PDFs to Firebase Storage
// to provide a download link to Android users. That upload step
// is currently DISABLED (commented) because Firebase Storage
// is no longer available on the free plan for this project.


// The code below generates the PDF (jsPDF + html2canvas), saves locally,
// and previously called uploadPDFToFirebase(...) to upload to Storage.
// That upload call + upload function are retained but commented-out


import { /* storage */ } from "../firebase/firebase.js"; // storage import kept but commented in usage
// CDN Storage functions are left available (commented) in case of restore:
import {
  ref,
  uploadBytes,
  getDownloadURL,
} from "https://www.gstatic.com/firebasejs/12.6.0/firebase-storage.js";

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

    const todaysDateForPDF = getDateForPDF();
    const todaysDate = getDate();

    const { jsPDF } = window.jspdf;
    const element = document.getElementById("all-table-container");

    // Capture the DOM as a canvas and prepare the PDF.
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
      pdf.text("Coal Mining Shift Log", pdf.internal.pageSize.width / 2, 15, {
        align: "center",
      });

      pdf.setFontSize(12);
      pdf.text(
        `Date: ${todaysDateForPDF}`,
        pdf.internal.pageSize.width - marginLeft,
        15,
        { align: "right" }
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

      // === LOCAL DOWNLOAD (active) ===
      // This downloads directly to the user's device (zero infra).
      pdf.save(`${todaysDate}.pdf`);

      // === UPLOAD TO CLOUD (DISABLED) ===
      // Historically, we uploaded the PDF to Firebase Storage and saved
      // the download URL in Firestore so Android users could fetch it.
      // That upload step is currently disabled because Firebase Storage
      // billing changed and we removed the free tier.


      // Informational log so reviewers understand the current behaviour:
      console.info(
        "PDF generated and saved locally. Cloud upload is DISABLED (commented) — see README for details."
      );
    });
  });

