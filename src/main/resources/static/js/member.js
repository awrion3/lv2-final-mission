document.addEventListener('DOMContentLoaded', function () {
    updateUIBasedOnLogin();
});

document.getElementById('logout-btn').addEventListener('click', function (event) {
    event.preventDefault();
    fetch('/logout', {
        method: 'POST',
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                console.error('Logout failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

function updateUIBasedOnLogin() {
    fetch('/login/check')
        .then(response => {
            if (!response.ok) {
                throw new Error('Not logged in or other error');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('profile-name').textContent = data.name;
            document.querySelector('.nav-item.dropdown').style.display = 'block';
            document.querySelector('.nav-item a[href="/login"]').parentElement.style.display = 'none';
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('profile-name').textContent = 'Profile';
            document.querySelector('.nav-item.dropdown').style.display = 'none';
            document.querySelector('.nav-item a[href="/login"]').parentElement.style.display = 'block';
        });
}

document.getElementById("navbarDropdown").addEventListener('click', function (e) {
    e.preventDefault();
    const dropdownMenu = e.target.closest('.nav-item.dropdown').querySelector('.dropdown-menu');
    dropdownMenu.classList.toggle('show');
});


function login() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!email || !password) {
        alert('Please fill in all fields.');
        return;
    }

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
        .then(response => {
            if (200 === !response.status) {
                alert('Login failed');
                throw new Error('Login failed');
            }
        })
        .then(() => {
            updateUIBasedOnLogin();
            window.location.href = '/';
        })
        .catch(error => {
            console.error('Error during login:', error);
        });
}

function signup() {
    window.location.href = '/signup';
}

function register(event) {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const name = document.getElementById('name').value;

    if (!email || !password || !name) {
        alert('Please fill in all fields.');
        return;
    }

    const formData = {
        email: email,
        password: password,
        name: name
    };

    fetch('/members', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                alert('Signup request failed');
                throw new Error('Signup request failed');
            }
            return response.json();
        })
        .then(data => {
            console.log('Signup successful:', data);
            window.location.href = '/login';
        })
        .catch(error => {
            console.error('Error during signup:', error);
        });

    event.preventDefault();
}
