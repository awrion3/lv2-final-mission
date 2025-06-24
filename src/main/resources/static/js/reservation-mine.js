const RESERVATION_MINE_API_ENDPOINT = '/reservations/mine';
const RESERVATION_API_ENDPOINT = '/reservations';

document.addEventListener('DOMContentLoaded', () => {
    fetch(RESERVATION_MINE_API_ENDPOINT)
        .then(response => {
            if (response.status === 200) return response.json();
            throw new Error('Read failed');
        })
        .then(render)
        .catch(error => console.error('Error fetching reservations:', error));
});

function render(data) {
    const tableBody = document.getElementById('table-body');
    tableBody.innerHTML = '';

    data.forEach(item => {
        const row = tableBody.insertRow();

        const date = item.date;
        const time = item.time;
        const reservationId = item.reservationId;

        row.insertCell(0).textContent = date;
        row.insertCell(1).textContent = time;

        const cancelCell = row.insertCell(2);
        const cancelButton = document.createElement('button');

        cancelButton.textContent = 'Cancel';
        cancelButton.className = 'btn btn-danger';
        cancelButton.onclick = function () {
            requestDeleteWaiting(reservationId)
                .then(() => window.location.reload());
        };
        cancelCell.appendChild(cancelButton);
    });
}

function requestDeleteWaiting(reservationId) {
    const requestOptions = {
        method: 'DELETE',
    };

    return fetch(`${RESERVATION_API_ENDPOINT}/${reservationId}`, requestOptions)
        .then(response => {
            if (response.status === 204) return;
            throw new Error('Delete failed');
        });
}
