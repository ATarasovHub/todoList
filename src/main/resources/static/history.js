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
