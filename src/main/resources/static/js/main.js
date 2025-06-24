document.addEventListener('DOMContentLoaded', () => {
    const url = '/reviews';

    request(url)
        .then(render)
        .catch(error => console.error('Error fetching reviews:', error));
});

function render(data) {
    const container = document.getElementById('random-reviews');

    data.forEach(content => {
        const htmlContent = `
            <div class="media-body">
                ${content.review}
            </div>
        `;

        const div = document.createElement('li');
        div.className = 'media my-4';
        div.innerHTML = htmlContent;

        container.appendChild(div);
    });
}

function request(endpoint) {
    return fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        if (response.status === 200) return response.json();
        throw new Error('Post failed');
    });
}

function effect(selector, colors, interval) {
    const element = document.querySelector(selector);
    if (!element) return;

    let index = 0;
    setInterval(() => {
        element.style.color = colors[index];
        index = (index + 1) % colors.length;
    }, interval);
}

effect('#random-reviews', ['#e74c3c', '#3498db', '#2ecc71', '#f1c40f', '#9b59b6'], 1000);
