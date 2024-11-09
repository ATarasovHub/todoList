document.getElementById("loginForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (!username || !password) {
        alert('Please enter both username and password.');
        return;
    }

    // Тестирование (проверка всех пользователей)
    let responseFromAPI = await fetch('/Users');
    let checkUsers = await responseFromAPI.json();
    console.log(checkUsers);
    console.log("Checking input Users:" + username);
    console.log("Checking input Users passwords:" + password);
    // Конец теста

    const response = await fetch("/Users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        const data = await response.json();
        alert("Login successful!");

        document.getElementById("username").value = '';
        document.getElementById("password").value = '';

        window.location.href = "Index/mainSeite.html";
    } else {
        alert("Invalid username or password. Please try again.");

        document.getElementById("username").value = '';
        document.getElementById("password").value = '';

        location.reload();
    }
});
