const apiBaseUrl = "/api/persons";

function loadPersons() {
    fetch(apiBaseUrl)
        .then(response => response.json())
        .then(data => {
            const personTable = document.getElementById('person-table');
            personTable.innerHTML = '';
            data.forEach(person => {
                const row = `
                    <tr>
                        <td>${person.id}</td>
                        <td>${person.name}</td>
                        <td>${person.emailAddress}</td>
                        <td>${person.phoneNumber}</td>
                        <td>
                            <button onclick="editPerson(${person.id})">Editar</button>
                            <button onclick="deletePerson(${person.id})">Deletar</button>
                        </td>
                    </tr>
                `;
                personTable.innerHTML += row;
            });
        });
}

document.getElementById('create-person-form').addEventListener('submit', function (e) {
    e.preventDefault();
    const person = {
        name: document.getElementById('name').value,
        emailAddress: document.getElementById('email').value,
        phoneNumber: document.getElementById('phone').value
    };
    fetch(apiBaseUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(person),
    }).then(response => {
        if (response.ok) {
            window.location.href = 'persons.html';
        }
    });
});

function editPerson(id) {
    window.location.href = `update-person.html?id=${id}`;
}

document.getElementById('update-person-form').addEventListener('submit', function (e) {
    e.preventDefault();
    const id = document.getElementById('person-id').value;
    const person = {
        name: document.getElementById('name').value,
        emailAddress: document.getElementById('email').value,
        phoneNumber: document.getElementById('phone').value
    };
    fetch(`${apiBaseUrl}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(person),
    }).then(response => {
        if (response.ok) {
            window.location.href = 'persons.html';
        }
    });
});

function deletePerson(id) {
    fetch(`${apiBaseUrl}/${id}`, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            loadPersons();
        }
    });
}

if (document.getElementById('person-table')) {
    loadPersons();
}
