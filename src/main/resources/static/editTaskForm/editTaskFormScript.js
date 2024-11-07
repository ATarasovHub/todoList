document.getElementById("editTaskForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const taskName = document.getElementById("taskName").value;
    const taskDeadlineDateAndTime = document.getElementById("taskDeadlineDateAndTime").value;
    const taskId = document.getElementById("taskId").value;
    const now = new Date();
    const deadlineToCheck = new Date(taskDeadlineDateAndTime);

    if (!taskName || !taskDeadlineDateAndTime) {
        alert('Please fill in all fields.');
        return;
    }

    const responseFromAPI = await fetch(`/tasks/${taskId}`);
    if (!responseFromAPI.ok) {
        alert('Task not found, please reload the site.');
        return;
    }

    if (deadlineToCheck <= now) {
        alert('Please enter a future deadline.');
        return;
    }

    const response = await fetch(`/tasks/${taskId}/edit`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: taskId,
            name: taskName,
            deadline: taskDeadlineDateAndTime
        })
    });

    if (response.ok) {
        window.close();
        window.opener.fullTableWithTasks();
    } else {
        console.error('Error updating task.');
    }

    document.getElementById("taskName").value = '';
    document.getElementById("taskDeadlineDateAndTime").value = '';
});