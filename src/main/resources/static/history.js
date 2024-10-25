async function fetchHistory() {
    const responseFromController = await fetch('/history');
    const historyOBJ = await responseFromController.json();

    const historyList = document.getElementById('task-history');
    historyList.innerHTML = '';

    historyOBJ.forEach(historyObj => {
        const row = document.createElement('tr');

        row.innerHTML = `<td>${historyObj.id}</td>` +
            `<td>${historyObj.name}</td>` +
            `<td>${historyObj.deadline}</td>`;

        historyList.appendChild(row);
    });
}

document.addEventListener('DOMContentLoaded', fetchHistory);

document.getElementById("sort-id").addEventListener('click',idSortieren);
document.getElementById("sort-name").addEventListener('click',nameSortieren);
document.getElementById("sort-deadline").addEventListener('click',deadlineSortieren);

async function deadlineSortieren() {
    const responseFromController = await fetch('/history');
    const historyOBJ = await responseFromController.json();

    historyOBJ.sort((a, b) => a.deadline.localeCompare(b.deadline));

    const historyList = document.getElementById('task-history');
    historyList.innerHTML = '';

    historyOBJ.forEach(historyObj => {
        const row = document.createElement('tr');

        row.innerHTML = `<td>${historyObj.id}</td>` +
            `<td>${historyObj.name}</td>` +
            `<td>${historyObj.deadline}</td>`;

        historyList.appendChild(row);
    });
}



async function nameSortieren() {
    const responseFromController = await fetch('/history');
    const historyOBJ = await responseFromController.json();

    historyOBJ.sort((a, b) => a.name.localeCompare(b.name));

    const historyList = document.getElementById('task-history');
    historyList.innerHTML = '';

    historyOBJ.forEach(historyObj => {
        const row = document.createElement('tr');

        row.innerHTML = `<td>${historyObj.id}</td>` +
            `<td>${historyObj.name}</td>` +
            `<td>${historyObj.deadline}</td>`;

        historyList.appendChild(row);
    });
}

async function idSortieren() {
    const responseFromController = await fetch('/history');
    const historyOBJ = await responseFromController.json();

    historyOBJ.sort((a, b) => a.id - b.id);

    const historyList = document.getElementById('task-history');
    historyList.innerHTML = '';

    historyOBJ.forEach(historyObj => {
        const row = document.createElement('tr');

        row.innerHTML = `<td>${historyObj.id}</td>` +
            `<td>${historyObj.name}</td>` +
            `<td>${historyObj.deadline}</td>`;

        historyList.appendChild(row);
    });
}