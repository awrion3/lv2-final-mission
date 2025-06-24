const RESERVATION_API_ENDPOINT = '/reservations';

document.addEventListener('DOMContentLoaded', () => {

    flatpickr("#datepicker", {
        inline: true,
        onChange: function (selectedDates, dateStr) {
            if (dateStr === '') return;
            checkDate();
        }
    });

    document.getElementById('time-slots').addEventListener('click', event => {
        if (event.target.classList.contains('time-slot') && !event.target.classList.contains('disabled')) {
            document.querySelectorAll('.time-slot').forEach(slot => slot.classList.remove('active'));
            event.target.classList.add('active');
            checkDateAndTime();
        }
    });

    document.getElementById('reserve-button').addEventListener('click', onReservationButtonClick);
});

function createSlot(type, text, id, booked) {
    const div = document.createElement('div');
    div.className = type + '-slot cursor-pointer bg-light border rounded p-3 mb-2';
    div.textContent = text;
    div.setAttribute('data-' + type + '-id', id);
    if (type === 'time') {
        div.setAttribute('data-time-booked', booked);
        if (booked) {
            div.classList.add('disabled');
        }
    }
    return div;
}

function checkDate() {
    const selectedDate = document.getElementById("datepicker").value;
    if (selectedDate) {
        const timeSlots = document.getElementById('time-slots');
        timeSlots.innerHTML = '';
        fetchAvailableTimes(selectedDate);
    }
}

function fetchAvailableTimes(date) {
    const query = new URLSearchParams({date}).toString();
    const url = `/times?${query}`;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        if (response.status === 200) return response.json();
        throw new Error('Read failed');
    }).then(renderAvailableTimes)
        .catch(error => console.error("Error fetching available times:", error));
}

function renderAvailableTimes(times) {
    const timeSection = document.getElementById("time-section");
    if (timeSection.classList.contains("disabled")) {
        timeSection.classList.remove("disabled");
    }

    const timeSlots = document.getElementById('time-slots');
    timeSlots.innerHTML = '';
    if (times.length === 0) {
        timeSlots.innerHTML = '<div class="no-times">No Currently Available Times</div>';
        return;
    }
    times.forEach(time => {
        const startAt = time.startAt;
        const timeId = time.id;
        const alreadyBooked = time.alreadyBooked;

        const div = createSlot('time', startAt, timeId, alreadyBooked);
        timeSlots.appendChild(div);
    });
}

function checkDateAndTime() {
    const selectedDate = document.getElementById("datepicker").value;
    const selectedTimeElement = document.querySelector('.time-slot.active');
    const reserveButton = document.getElementById("reserve-button");

    if (selectedDate && selectedTimeElement) {
        if (selectedTimeElement.getAttribute('data-time-booked') === 'true') {
            reserveButton.classList.add("disabled");
        } else {
            reserveButton.classList.remove("disabled");
        }
    } else {
        reserveButton.classList.add("disabled");
    }
}

function onReservationButtonClick() {
    const selectedDate = document.getElementById("datepicker").value;
    const selectedTimeId = document.querySelector('.time-slot.active')?.getAttribute('data-time-id');

    if (selectedDate && selectedTimeId) {
        const reservationData = {
            date: selectedDate,
            timeId: selectedTimeId
        };

        fetch(`${RESERVATION_API_ENDPOINT}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(reservationData)
        })
            .then(response => {
                if (!response.ok) throw new Error('Reservation failed');
                return response.json();
            })
            .then(data => {
                alert("Reservation successful!");
                location.reload();
            })
            .catch(error => {
                alert("An error occurred while making the reservation.");
                console.error(error);
            });
    } else {
        alert("Please select a date, and time before making a reservation.");
    }
}
