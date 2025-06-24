let isEditing = false;

const RESERVATION_ADMIN_API_ENDPOINT = '/admin/reservations';
const RESERVATION_API_ENDPOINT = '/reservations';
const TIME_API_ENDPOINT = '/times';
const MEMBER_API_ENDPOINT = '/members';

const timesOptions = [];
const membersOptions = [];

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('add-button').addEventListener('click', addInputRow);
    document.getElementById('filter-form').addEventListener('submit', applyFilter);

    requestRead(RESERVATION_API_ENDPOINT)
        .then(render)
        .catch(error => console.error('Error fetching reservations:', error));

    fetchTimes();
    fetchMembers();
});

function render(data) {
    const tableBody = document.getElementById('table-body');
    tableBody.innerHTML = '';

    data.forEach(item => {
        const row = tableBody.insertRow();

        row.insertCell(0).textContent = item.id;
        row.insertCell(1).textContent = item.member.name;
        row.insertCell(2).textContent = item.date;
        row.insertCell(3).textContent = item.time.startAt;

        const actionCell = row.insertCell(row.cells.length);
        actionCell.appendChild(createActionButton('Delete', 'btn-danger', deleteRow));
    });
}

function fetchTimes() {
    requestRead(TIME_API_ENDPOINT)
        .then(data => {
            timesOptions.push(...data);
        })
        .catch(error => console.error('Error fetching time:', error));
}

function fetchMembers() {
    requestRead(MEMBER_API_ENDPOINT)
        .then(data => {
            membersOptions.push(...data);
            populateSelect('member', membersOptions, 'name');
        })
        .catch(error => console.error('Error fetching member:', error));
}

function populateSelect(selectId, options, textProperty) {
    const select = document.getElementById(selectId);
    options.forEach(optionData => {
        const option = document.createElement('option');
        option.value = optionData.id;
        option.textContent = optionData[textProperty];
        select.appendChild(option);
    });
}

function createSelect(options, defaultText, selectId, textProperty) {
    const select = document.createElement('select');
    select.className = 'form-control';
    select.id = selectId;

    const defaultOption = document.createElement('option');
    defaultOption.textContent = defaultText;
    select.appendChild(defaultOption);

    options.forEach(optionData => {
        const option = document.createElement('option');
        option.value = optionData.id;
        option.textContent = optionData[textProperty];
        select.appendChild(option);
    });

    return select;
}

function addInputRow() {
    if (isEditing) return;

    const tableBody = document.getElementById('table-body');
    const row = tableBody.insertRow();
    isEditing = true;

    const dateInput = createInput('date');
    const timeDropdown = createSelect(timesOptions, "Choose Time", 'time-select', 'startAt');
    const memberDropdown = createSelect(membersOptions, "Choose Member", 'member-select', 'name');

    const cellFieldsToCreate = ['', memberDropdown, dateInput, timeDropdown];

    cellFieldsToCreate.forEach((field, index) => {
        const cell = row.insertCell(index);
        if (typeof field === 'string') {
            cell.textContent = field;
        } else {
            cell.appendChild(field);
        }
    });

    const actionCell = row.insertCell(row.cells.length);
    actionCell.appendChild(createActionButton('Save', 'btn-custom', saveRow));
    actionCell.appendChild(createActionButton('Cancel', 'btn-secondary', () => {
        row.remove();
        isEditing = false;
    }));
}

function createInput(type) {
    const input = document.createElement('input');
    input.type = type;
    input.className = 'form-control';
    return input;
}

function createActionButton(label, className, eventListener) {
    const button = document.createElement('button');
    button.textContent = label;
    button.classList.add('btn', className, 'mr-2');
    button.addEventListener('click', eventListener);
    return button;
}

function saveRow(event) {
    event.stopPropagation();

    const row = event.target.parentNode.parentNode;
    const dateInput = row.querySelector('input[type="date"]');
    const memberSelect = row.querySelector('#member-select');
    const timeSelect = row.querySelector('#time-select');

    const reservation = {
        date: dateInput.value,
        timeId: timeSelect.value,
        memberId: memberSelect.value,
    };

    requestCreate(reservation)
        .then(() => {
            location.reload();
        })
        .catch(error => console.error('Error:', error));

    isEditing = false;
}

function deleteRow(event) {
    const row = event.target.closest('tr');
    const reservationId = row.cells[0].textContent;

    requestDelete(reservationId)
        .then(() => row.remove())
        .catch(error => console.error('Error:', error));
}

function applyFilter(event) {
    event.preventDefault();

    const memberId = document.getElementById('member').value;
    const dateFrom = document.getElementById('date-from').value;
    const dateTo = document.getElementById('date-to').value;

    const params = new URLSearchParams();

    if (memberId) params.append('memberId', memberId);
    if (dateFrom) params.append('from', dateFrom);
    if (dateTo) params.append('to', dateTo);

    fetch(`${RESERVATION_API_ENDPOINT}?${params.toString()}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    }).then(response => {
        if (response.status === 200) return response.json();
        throw new Error('Read failed');
    }).then(render)
        .catch(error => console.error("Error fetching available times:", error));
}

function requestCreate(reservation) {
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(reservation)
    };

    return fetch(`${RESERVATION_ADMIN_API_ENDPOINT}`, requestOptions)
        .then(response => {
            if (response.status === 201) return response.json();
            throw new Error('Create failed');
        });
}

function requestDelete(id) {
    const requestOptions = {
        method: 'DELETE',
    };

    return fetch(`${RESERVATION_API_ENDPOINT}/${id}`, requestOptions)
        .then(response => {
            if (response.status !== 204) throw new Error('Delete failed');
        });
}

function requestRead(endpoint) {
    return fetch(endpoint)
        .then(response => {
            if (response.status === 200) return response.json();
            throw new Error('Read failed');
        });
}
