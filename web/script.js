document.getElementById("loginForm")?.addEventListener("submit", function(event) {
    event.preventDefault();
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    if (username === "" || password === "") {
        alert("Please fill all fields!");
    } else {
        alert("Login Successful (Backend Integration Pending)");
        // Redirect to dashboard (temporary)
        window.location.href = "dashboard.html";
    }
});

document.getElementById("registerForm")?.addEventListener("submit", function(event) {
    event.preventDefault();
    let username = document.getElementById("regUsername").value;
    let email = document.getElementById("regEmail").value;
    let password = document.getElementById("regPassword").value;
    if (username === "" || email === "" || password === "") {
        alert("Please fill all fields!");
    } else {
        alert("Registration Successful (Backend Integration Pending)");
        window.location.href = "login.html";
    }
});
