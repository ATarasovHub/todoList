async function fullTableWithTasks() {
    let responseFromAPI = await fetch('/tasks');
    let tasks = await responseFromAPI.json();

    renderTasks(tasks);
}

document.addEventListener('DOMContentLoaded', async function () {
    await fullTableWithTasks();

    document.querySelector('.add-task-btn').addEventListener('click', openModal);
    document.querySelector('.close-btn').addEventListener('click', closeModal);
    document.getElementById("taskSortieren").addEventListener('click', taskSortieren);
    document.getElementById("deadlineSortieren").addEventListener('click', deadlineSortieren);
});

function closeModal(){
    const modal = document.getElementById('taskModal');
    modal.style.display = 'none';
}

function openModal() {
    const modal = document.getElementById('taskModal');
    modal.style.display = 'flex';
}

document.getElementById("taskForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const taskName = document.getElementById("taskName").value;
    const taskDeadLineDateAndTime = document.getElementById("taskDeadlineDateAndTime").value;
    const now = new Date();
    const deadlineToCheck = new Date(taskDeadLineDateAndTime);

    if (deadlineToCheck > now) {
        await fetch('/tasks', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: taskName,
                deadline: taskDeadLineDateAndTime
            })
        });

        await fullTableWithTasks();

        document.getElementById("taskName").value = '';
        document.getElementById("taskDeadlineDateAndTime").value = '';

        await closeModal();
    } else {
        alert('Please enter a future deadline.');
    }
});

async function moveDataToHistory(taskId) {
    const response = await fetch(`/tasks/${taskId}/history`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({id: taskId}),
    });

    if (response.ok) {
        console.log("Successfully moved");
        await fullTableWithTasks();
        await closeModal();
    } else {
        const errorMessage = await response.text();
        alert(`Error moving task: ${errorMessage}`);
    }
}

async function editDataInActualTasks(taskId) {
    const response = await fetch(`/tasks/${taskId}`);
    if (!response.ok) {
        alert('Task not found, please reload the site.');
        return;
    }
    const task = await response.json();

    if (!task || !task.id) {
        alert('Task not found, please reload the site.');
        return;
    }

    const formWindow = window.open('editTaskForm.html', '_blank', 'width=400,height=300');

    formWindow.addEventListener('load', function () {
        formWindow.document.getElementById("taskName").value = task.name;
        formWindow.document.getElementById("taskDeadlineDateAndTime").value = task.deadline;
        formWindow.document.getElementById("taskId").value = taskId;
    });
}

async function compareDateAndTime() {
    let responseFromAPI = await fetch('/tasks');
    let tasks = await responseFromAPI.json();

    let now = new Date();

    tasks.forEach(task => {
        let row = document.getElementById("taskId_" + task.id);
        let deadline = new Date(task.deadline);

        if (!row) {
            console.warn('No row ', task.deadline);
            return;
        }

        let cells = row.querySelectorAll('td');
        if (deadline < now) {
            cells.forEach(cell => {
                cell.style.color = 'red';
            });
        } else {
            const timeDiff = deadline - now;
            const hoursLeft = timeDiff / (1000 * 60 * 60);

            if (hoursLeft <= 10) {
                cells.forEach(cell => {
                    cell.style.color = 'orange';
                });
            }
        }
    });
}

let currentSortOrder = {
    name: 'asc',
    deadline: 'asc'
};

async function taskSortieren() {
    let responseFromController = await fetch('/tasks');
    let taskOBJ = await responseFromController.json();

    let sortIndicator = document.querySelector('#taskSortieren .sort-indicator');

    if (currentSortOrder.name === 'asc') {
        taskOBJ.sort((a, b) => a.name.localeCompare(b.name));
        currentSortOrder.name = 'desc';
        sortIndicator.classList.add('flipped');
    } else {
        taskOBJ.sort((a, b) => b.name.localeCompare(a.name));
        currentSortOrder.name = 'asc';
        sortIndicator.classList.remove('flipped');
    }

    renderTasks(taskOBJ);
}

async function deadlineSortieren() {
    const responseFromController = await fetch('/tasks');
    const taskOBJ = await responseFromController.json();

    const sortIndicator = document.querySelector('#deadlineSortieren .sort-indicator');

    if (currentSortOrder.deadline === 'asc') {
        taskOBJ.sort((a, b) => a.deadline.localeCompare(b.deadline));
        currentSortOrder.deadline = 'desc';
        sortIndicator.classList.add('flipped');
    } else {
        taskOBJ.sort((a, b) => b.deadline.localeCompare(a.deadline));
        currentSortOrder.deadline = 'asc';
        sortIndicator.classList.remove('flipped');

    }

    renderTasks(taskOBJ);
}

function renderTasks(taskOBJ) {
    const taskList = document.getElementById('task-list');
    taskList.innerHTML = '';

    taskOBJ.forEach(taskObj => {
        const row = document.createElement('tr');
        row.id = "taskId_" + taskObj.id;
        row.dataset.deadline = taskObj.deadline;

        row.innerHTML = `
            <td><input type="button" id="buttonCheck" onclick="moveDataToHistory(${taskObj.id})" value="Move to History"></td>
            <td><input type="button" id="editButton" onclick="editDataInActualTasks(${taskObj.id})" value="Edit"></td>
            <td>${taskObj.name}</td>
            <td>${taskObj.deadline.replace('T', ' ')}</td> 
        `;
        taskList.appendChild(row);
    });

    compareDateAndTime();
}
