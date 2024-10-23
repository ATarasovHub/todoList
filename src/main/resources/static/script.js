async function fetchTasks() {
    const responseFromAPI = await fetch('/tasks/allTasks');
    const tasks = await responseFromAPI.json();

    const taskList = document.getElementById("task-list");
    taskList.innerHTML = '';

    tasks.forEach(task => {
        const row = document.createElement('tr');

        row.innerHTML = `
           <td><input type="button" id="buttonCheck" onclick="moveDataToHistory(${task.id})" value="Move to History"></td>
           <td><input type = "button" id ="editButton" onclick ="editDataInActualTasks(${task.id})" value ="Edit"</td>
            <td>${task.name}</td>
            <td>${task.deadline}</td>
        `;
        taskList.appendChild(row);
    });
}
document.addEventListener('DOMContentLoaded',fetchTasks);

document.getElementById("taskForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const taskName = document.getElementById("taskName").value;
    const taskDeadLineDateAndTime = document.getElementById("taskDeadlineDateAndTime").value;

    if (!taskName || !taskDeadLineDateAndTime) {
        alert('Please fill in all fields.');
        return;
    }

    const response = await fetch('/tasks', {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: taskName,
            deadline: taskDeadLineDateAndTime
        })
    });

    fetchTasks();

        const cleanTaskName = document.getElementById("taskName").value = '';
        const cleanDeadLine = document.getElementById("taskDeadlineDateAndTime").value = '';

});

async function moveDataToHistory(taskId) {
    console.log("Moving task with ID:", taskId);
    const response = await fetch('/transfer/move', {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id: taskId }),
    });

    if (response.ok) {
        console.log("Successfully moved");
        window.location.reload();
    } else {
        console.error('Error moving task to history.');
    }
}

async function editDataInActualTasks(taskId) {

    const response = await fetch(`/tasks/${taskId}`);
    const task = await response.json();

    document.getElementById("taskName").value = task.name;
    document.getElementById("taskDeadlineDateAndTime").value = task.deadline;


    const formWindow = window.open('editTaskForm.html', '_blank', 'width=400,height=300');

    formWindow.addEventListener('DOMContentLoaded', function() {

        formWindow.document.getElementById("taskName").value = task.name;
        formWindow.document.getElementById("taskDeadlineDateAndTime").value = task.deadline;
        formWindow.document.getElementById("taskId").value = taskId;
    });
}





// async function editDataInActualTasks(taskId) {
//     const response = await fetch(`/tasks/${taskId}`);
//     const task = await response.json();
//
//     const formWindow = window.open('', '_blank', 'width=400,height=300');
//
//     formWindow.document.write(`
//         <!DOCTYPE html>
//         <html>
//         <head>
//           <title>Edit Task</title>
//         </head>
//         <body>
//         <h2>Edit Task</h2>
//         <form id="editTaskForm">
//           <label for="taskName">Task Name:</label>
//           <input type="text" id="taskName" required value="${task.name}"><br>
//
//           <label for="taskDeadlineDateAndTime">Task Deadline:</label>
//           <input type="datetime-local" id="taskDeadlineDateAndTime" required value="${task.deadline}"><br>
//
//           <input type="hidden" id="taskId" value="${taskId}">
//
//           <button type="submit">Save</button>
//         </form>
//
//         <script>
//           document.getElementById("editTaskForm").addEventListener("submit", async function(event) {
//             event.preventDefault();
//
//             const taskId = document.getElementById("taskId").value;
//             const taskName = document.getElementById("taskName").value;
//             const taskDeadlineDateAndTime = document.getElementById("taskDeadlineDateAndTime").value;
//
//             if (!taskName || !taskDeadlineDateAndTime) {
//               alert('Please fill in all fields.');
//               return;
//             }
//
//             const response = await fetch('/tasks/edit', {
//               method: "POST",
//               headers: {
//                 'Content-Type': 'application/json',
//               },
//               body: JSON.stringify({
//                 id: taskId,
//                 name: taskName,
//                 deadline: taskDeadlineDateAndTime
//               })
//             });
//
//             if (response.ok) {
//               window.close();
//               window.opener.fetchTasks();
//             } else {
//               console.error('Error updating task.');
//             }
//           });
//         <\/script>
//         </body>
//         </html>
//     `);
// }
