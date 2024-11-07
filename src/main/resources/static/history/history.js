let currentSortOrder = {
    id: 'asc',
    name: 'asc',
    deadline: 'asc'
};

async function fetchHistory() {
    let responseFromController = await fetch('/history');
    let historyOBJ = await responseFromController.json();

    renderHistory(historyOBJ);
}

document.addEventListener('DOMContentLoaded', fetchHistory);

document.getElementById("sort-id").addEventListener('click', idSortieren);
document.getElementById("sort-name").addEventListener('click', nameSortieren);
document.getElementById("sort-deadline").addEventListener('click', deadlineSortieren);

async function deadlineSortieren() {
    let responseFromController = await fetch('/history');
    let historyOBJ = await responseFromController.json();

    const sortIndicator = document.querySelector('#sort-deadline .sort-indicator');

    if (currentSortOrder.deadline === 'asc') {
        historyOBJ.sort((a, b) => a.deadline.localeCompare(b.deadline));
        currentSortOrder.deadline = 'desc';
        sortIndicator.classList.add('flipped');


    } else {
        historyOBJ.sort((a, b) => b.deadline.localeCompare(a.deadline));
        currentSortOrder.deadline = 'asc';
        sortIndicator.classList.remove('flipped');

    }

    renderHistory(historyOBJ);
}

async function nameSortieren() {
    let responseFromController = await fetch('/history');
    let historyOBJ = await responseFromController.json();

    const sortIndicator = document.querySelector('#sort-name .sort-indicator');

    if (currentSortOrder.name === 'asc') {
        historyOBJ.sort((a, b) => a.name.localeCompare(b.name));
        currentSortOrder.name = 'desc';
        sortIndicator.classList.add('flipped');
    } else {
        historyOBJ.sort((a, b) => b.name.localeCompare(a.name));
        currentSortOrder.name = 'asc';
        sortIndicator.classList.remove('flipped');
    }

    renderHistory(historyOBJ);
}

async function idSortieren() {
    const responseFromController = await fetch('/history');
    const historyOBJ = await responseFromController.json();

    const sortIndicator = document.querySelector('#sort-id .sort-indicator');

    if (currentSortOrder.id === 'asc') {
        historyOBJ.sort((a, b) => a.id - b.id);
        currentSortOrder.id = 'desc';
        sortIndicator.classList.add('flipped');
    } else {
        historyOBJ.sort((a, b) => b.id - a.id);
        currentSortOrder.id = 'asc';
        sortIndicator.classList.remove('flipped');

    }

    renderHistory(historyOBJ);
}

function renderHistory(historyOBJ) {
    const historyList = document.getElementById('task-history');
    historyList.innerHTML = '';

    historyOBJ.forEach(historyObj => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${historyObj.id}</td>
            <td>${historyObj.name}</td>
            <td>${historyObj.deadline}</td>
        `;
        historyList.appendChild(row);
    });
}
