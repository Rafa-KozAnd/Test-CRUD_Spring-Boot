const apiBaseUrl = "/api/professors";

document.addEventListener('DOMContentLoaded', function () {
    if (document.getElementById('professor-table')) {
        loadProfessors();
    }

    const createProfessorForm = document.getElementById('create-professor-form');
    if (createProfessorForm) {
        createProfessorForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const professor = {
                name: document.getElementById('name').value,
                emailAddress: document.getElementById('email').value,
                phoneNumber: document.getElementById('phone').value,
                salary: document.getElementById('salary').value
            };
            fetch(apiBaseUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(professor),
            }).then(response => {
                if (response.ok) {
                    window.location.href = 'professors.html';
                } else {
                    alert('Error creating professor.');
                }
            });
        });
    }

    const updateProfessorForm = document.getElementById('update-professor-form');
    if (updateProfessorForm) {
        const professorId = new URLSearchParams(window.location.search).get('id');
        if (professorId) {
            fetch(`${apiBaseUrl}/${professorId}`)
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Error loading professor data');
                    }
                })
                .then(professor => {
                    document.getElementById('professor-id').value = professor.id;
                    document.getElementById('name').value = professor.name;
                    document.getElementById('email').value = professor.emailAddress;
                    document.getElementById('phone').value = professor.phoneNumber;
                    document.getElementById('salary').value = professor.salary;
                })
                .catch(error => {
                    console.error(error);
                    alert('Error loading professor data. Check the console for details.');
                });
        }

        updateProfessorForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const id = document.getElementById('professor-id').value;
            const professor = {
                name: document.getElementById('name').value,
                emailAddress: document.getElementById('email').value,
                phoneNumber: document.getElementById('phone').value,
                salary: document.getElementById('salary').value
            };
            fetch(`${apiBaseUrl}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(professor),
            }).then(response => {
                if (response.ok) {
                    window.location.href = 'professors.html';
                } else {
                    alert('Error updating professor.');
                }
            });
        });
    }
});

function loadProfessors() {
    fetch(apiBaseUrl)
        .then(response => response.json())
        .then(data => {
            const professorTable = document.getElementById('professor-table');
            professorTable.innerHTML = '';
            data.forEach(professor => {
                const row = `
                    <tr>
                        <td>${professor.id}</td>
                        <td>${professor.name}</td>
                        <td>${professor.emailAddress}</td>
                        <td>${professor.phoneNumber}</td>
                        <td>${professor.salary}</td>
                        <td>
                            <button onclick="editProfessor(${professor.id})">Edit</button>
                            <button onclick="deleteProfessor(${professor.id})">Delete</button>
                        </td>
                    </tr>
                `;
                professorTable.innerHTML += row;
            });
        });
}



function editProfessor(id) {
    window.location.href = `update-professor.html?id=${id}`;
}

function deleteProfessor(id) {
    fetch(`${apiBaseUrl}/${id}`, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            loadProfessors();
        } else {
            alert('Error deleting professor.');
        }
    });
}
