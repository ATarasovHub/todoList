async function fullTableWithTasks() {
    const responseFromAPI = await fetch('/tasks/allTasks');
    const tasks = await responseFromAPI.json();

    const taskList = document.getElementById("task-list");
    taskList.innerHTML = '';

    tasks.forEach(task => {
        const formattedDeadline = task.deadline.replace('T', ' ');

        const row = document.createElement('tr');
        row.dataset.deadline = task.deadline;

        row.innerHTML = `
            <td><input type="button" id="buttonCheck" onclick="moveDataToHistory(${task.id})" value="Move to History"></td>
            <td><input type="button" id="editButton" onclick="editDataInActualTasks(${task.id})" value="Edit"></td>
            <td>${task.name}</td>
            <td>${formattedDeadline}</td> 
        `;
        taskList.appendChild(row);
    });

    await compareDateAndTime();
}

document.addEventListener('DOMContentLoaded', async function () {
    await fullTableWithTasks();
});

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
    } else {
        alert('Please enter a future deadline.');
    }
});

async function moveDataToHistory(taskId) {
    try {
        const response = await fetch('/transfer/move', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ id: taskId }),
        });

        if (response.ok) {
            console.log("Successfully moved");
            await fullTableWithTasks();
        } else {
            const errorMessage = await response.text();
            alert(`Error moving task: ${errorMessage}`);
        }
    } catch (error) {
        console.error('Error moving task:', error);
        alert('There was an error moving the task. Please try again later.');
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
    const responseFromAPI = await fetch('/tasks/allTasks');
    const tasks = await responseFromAPI.json();

    const now = new Date();

    tasks.forEach(task => {
        const row = document.querySelector(`tr[data-deadline="${task.deadline}"]`);
        const deadline = new Date(task.deadline);

        if (!row) {
            console.warn('No row ', task.deadline);
            return;
        }

        const cells = row.querySelectorAll('td');
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

document.getElementById("taskSortieren").addEventListener('click', taskSortieren);
document.getElementById("deadlineSortieren").addEventListener('click', deadlineSortieren);

async function taskSortieren() {
    const responseFromController = await fetch('/tasks/allTasks');
    const taskOBJ = await responseFromController.json();

    taskOBJ.sort((a, b) => a.name.localeCompare(b.name));

    const taskList = document.getElementById('task-list');
    taskList.innerHTML = '';

    taskOBJ.forEach(taskObj => {
        const row = document.createElement('tr');
        row.dataset.deadline = taskObj.deadline;

        row.innerHTML = `
            <td><input type="button" id="buttonCheck" onclick="moveDataToHistory(${taskObj.id})" value="Move to History"></td>
            <td><input type="button" id="editButton" onclick="editDataInActualTasks(${taskObj.id})" value="Edit"></td>
            <td>${taskObj.name}</td>
            <td>${taskObj.deadline.replace('T', ' ')}</td> 
        `;

        taskList.appendChild(row);
    });

    await compareDateAndTime();
}

async function deadlineSortieren() {
    const responseFromController = await fetch('/tasks/allTasks');
    const taskOBJ = await responseFromController.json();

    taskOBJ.sort((a, b) => a.deadline.localeCompare(b.deadline));

    const taskList = document.getElementById('task-list');
    taskList.innerHTML = '';

    taskOBJ.forEach(taskObj => {
        const row = document.createElement('tr');
        row.dataset.deadline = taskObj.deadline;

        row.innerHTML = `
            <td><input type="button" id="buttonCheck" onclick="moveDataToHistory(${taskObj.id})" value="Move to History"></td>
            <td><input type="button" id="editButton" onclick="editDataInActualTasks(${taskObj.id})" value="Edit"></td>
            <td>${taskObj.name}</td>
            <td>${taskObj.deadline.replace('T', ' ')}</td> 
        `;
        taskList.appendChild(row);
    });

    await compareDateAndTime();
}
