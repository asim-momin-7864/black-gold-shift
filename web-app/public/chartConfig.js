// chartConfig.js

import { db } from "./firebaseConfig.js";
import {
  doc,
  getDoc,
} from "https://www.gstatic.com/firebasejs/10.14.0/firebase-firestore.js";

// Function to fetch data and render charts
export async function renderCharts() {
  try {
    const formattedDate = getFormattedDate();
    const docRef = doc(db, "ShiftLog", formattedDate);
    const docSnap = await getDoc(docRef);
    const data = docSnap.exists() ? docSnap.data() : null;
    console.log(data);

    const newdataObject = {};

    for (const key in data) {
      const value = data[key];
      newdataObject[key] = isNaN(value) ? value : Number(value);
    }

    console.log("new", newdataObject);
    // Box value Manipulation
    document.getElementById("BoxValue1").innerText =
      newdataObject.coal_Surfaceminer_Minerno;
    document.getElementById("BoxValueName1").innerText = "No. Surface Minor";
    document.getElementById("BoxValue2").innerText =
      newdataObject.coal_Explosives_Explosivescharged +
      newdataObject.overburden_Explosives_Explosivescharged;
    document.getElementById("BoxValueName2").innerText = "Total Explosives";
    document.getElementById("BoxValue3").innerText =
      newdataObject.washery_Rawcoal;
    document.getElementById("BoxValueName3").innerText = "Total Raw Coal";
    document.getElementById("BoxValue4").innerText =
      newdataObject.dispatch_Rail_Actual + newdataObject.dispatch_Road_Actual;
    document.getElementById("BoxValueName4").innerText = "Total Coal Dispatch";

    if (!data) {
      console.error("Document not found!");
      return;
    }

    // Chart data and configuration
    renderPieChart(
      "pieChart1",
      "Coal Washery Section",
      ["Clean Coal", "Middling", "Rejected", "Slurry"],
      [
        data.washery_Cleancoal,
        data.washery_Midding,
        data.washery_Rejected,
        data.washery_Slurry,
      ],
      [
        "rgb(54, 162, 235)",
        "rgb(255, 205, 86)",
        "rgb(255, 99, 132)",
        "rgb(52, 168, 83)",
      ]
    );

    renderPieChart(
      "pieChart2",
      "Actual Coal Dispatch ",
      ["Rail", "Road"],
      [data.dispatch_Rail_Actual, data.dispatch_Road_Actual],
      ["rgb(66, 133, 244)", "rgb(234, 67, 53)"]
    );

    renderBarChart(
      "barChart3",
      "Coal Dispatch Section",
      ["Target", "Actual"],
      [
        {
          label: "Rail",
          data: [data.dispatch_Rail_Target, data.dispatch_Rail_Actual],
          backgroundColor: "rgba(66, 133, 244, 1)",
          borderColor: "rgba(66, 133, 244, 1)",
        },
        {
          label: "Road",
          data: [data.dispatch_Road_Target, data.dispatch_Road_Actual],
          backgroundColor: "rgba(234, 67, 53, 1)",
          borderColor: "rgba(234, 67, 53, 1)",
        },
      ]
    );

    renderPieChart(
      "pieChart4",
      "Overburden Excavator",
      ["Solid", "Rehandling"],
      [
        data.overburden_Shovel_Solidquantity,
        data.overburden_Shovel_Rehandalingquantity,
      ],
      ["rgb(255, 205, 86)", "rgb(255, 99, 132)"]
    );

    renderBarChart(
      "barChart5",
      "Drill Performance Metrics",
      ["Overburden Drill", "Coal Mining Drill"],
      [
        {
          label: "N. Shot holes",
          data: [
            data.overburden_Drill_Noofshotholesdrilled,
            data.coal_Drill_Noofshotholesdrilled,
          ],
          backgroundColor: "yellow",
        },
        {
          label: "Drill Meters",
          data: [
            data.overburden_Drill_Drillingmeter,
            data.coal_Drill_Drillingmeter,
          ],
          backgroundColor: "green",
        },
        {
          label: "Total",
          data: [data.overburden_Drill_Total, data.coal_Drill_Total],
          backgroundColor: "orange",
        },
      ]
    );

    renderBarChart(
      "barChart6",
      "Explosives Metric",
      ["Overburden Explosive", "Coal Mining Explosive"],
      [
        {
          label: "N. holes Charged",
          data: [
            data.overburden_Explosives_Noholescharged,
            data.coal_Explosives_Noholescharged,
          ],
          backgroundColor: "green",
        },
        {
          label: "Explos Charged",
          data: [
            data.overburden_Explosives_Explosivescharged,
            data.coal_Explosives_Explosivescharged,
          ],
          backgroundColor: "blue",
        },
        {
          label: "N. holes Blasted",
          data: [
            data.overburden_Explosives_NoholesBlasetedd,
            data.coal_Explosives_NoholesBlasetedd,
          ],
          backgroundColor: "orange",
        },
        {
          label: "Explos Blasted",
          data: [
            data.overburden_Explosives_Explosiveblasted,
            data.coal_Explosives_Explosiveblasted,
          ],
          backgroundColor: "red",
        },
      ]
    );

    renderPieChart(
      "pieChart7",
      "Overburden Tripper",
      ["Solid", "Rehandling"],
      [
        data.overburden_Tripper_Soilquantity,
        data.overburden_Tripper_Rehanquantity,
      ],
      ["rgb(52, 168, 83)", "rgb(255, 99, 132)"]
    );
  } catch (error) {
    console.error("Error fetching document:", error);
  }
}

// Helper function to format the current date for Firebase document ID
function getFormattedDate() {
  const today = new Date();
  const day = today.getDate().toString().padStart(2, "0");
  const month = (today.getMonth() + 1).toString().padStart(2, "0");
  const year = today.getFullYear();
  return `${day}${month}${year}M`;
}

// Chart rendering functions
function renderPieChart(canvasId, title, labels, data, backgroundColors) {
  const ctx = document.getElementById(canvasId).getContext("2d");
  new Chart(ctx, {
    type: "pie",
    data: {
      labels,
      datasets: [
        {
          data,
          backgroundColor: backgroundColors,
          hoverOffset: 4,
        },
      ],
    },
    options: {
      responsive: true,
      plugins: {
        title: {
          display: true,
          text: title,
          font: { size: 20, weight: "bold" },
        },
        legend: { position: "top" },
      },
    },
  });
}

function renderBarChart(canvasId, title, labels, datasets) {
  const ctx = document.getElementById(canvasId).getContext("2d");
  new Chart(ctx, {
    type: "bar",
    data: { labels, datasets },
    options: {
      responsive: true,
      plugins: {
        title: {
          display: true,
          text: title,
          font: { size: 20, weight: "bold" },
        },
        legend: { position: "top" },
      },
      scales: {
        y: { beginAtZero: true },
      },
    },
  });
}
