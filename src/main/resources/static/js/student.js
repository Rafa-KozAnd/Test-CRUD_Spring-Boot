const apiBaseUrl = "/api/students";

document.addEventListener('DOMContentLoaded', function () {
    if (document.getElementById('student-table')) {
        loadStudents();
    }

    const createStudentForm = document.getElementById('create-student-form');
    if (createStudentForm) {
        createStudentForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const student = {
                name: document.getElementById('name').value,
                emailAddress: document.getElementById('email').value,
                phoneNumber: document.getElementById('phone').value,
                studentNumber: document.getElementById('studentNumber').value,
                photo: document.getElementById('photo').value
            };
            fetch(apiBaseUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(student),
            }).then(response => {
                if (response.ok) {
                    window.location.href = 'students.html';
                } else {
                    return response.text().then(text => {
                        alert('Failed to create student: ' + text);
                    });
                }
            });
        });
    }

    const updateStudentForm = document.getElementById('update-student-form');
    if (updateStudentForm) {
        const studentId = new URLSearchParams(window.location.search).get('id');
        if (studentId) {
                fetch(`${apiBaseUrl}/${studentId}`)
                .then(response => {
                    if (response.ok) {
                        return response.json()
                    } else {
                        throw new Error('Erro ao carregar os dados da pessoa');
                    }
                })
                .then(student => {
                    document.getElementById('student-id').value = student.id;
                    document.getElementById('name').value = student.name;
                    document.getElementById('email').value = student.emailAddress;
                    document.getElementById('phone').value = student.phoneNumber;
                    document.getElementById('studentNumber').value = student.studentNumber;
                    document.getElementById('photo').value = student.photo;
                })
                .catch(error => {
                    console.error(error);
                    alert('Erro ao carregar os dados do estudante. Verifique o console para mais detalhes.');
                });
        }

        updateStudentForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const id = document.getElementById('student-id').value;
            const student = {
                name: document.getElementById('name').value,
                emailAddress: document.getElementById('email').value,
                phoneNumber: document.getElementById('phone').value,
                studentNumber: document.getElementById('studentNumber').value,
                photo: document.getElementById('photo').value
            };
            fetch(`${apiBaseUrl}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(student),
            }).then(response => {
                if (response.ok) {
                    window.location.href = 'students.html';
                } else {
                    alert('Failed to update student.');
                }
            });
        });
    }
});

function loadStudents() {
    fetch(apiBaseUrl)
        .then(response => response.json())
        .then(data => {
            const studentTable = document.getElementById('student-table');
            studentTable.innerHTML = '';
            data.forEach(student => {
                const row = `
                    <tr>
                        <td>${student.id}</td>
                        <td>${student.name}</td>
                        <td>${student.emailAddress}</td>
                        <td>${student.phoneNumber}</td>
                        <td>${student.studentNumber}</td>
                        <td>${student.photo}</td>
                        <td>
                            <button onclick="editStudent(${student.id})">Edit</button>
                            <button onclick="deleteStudent(${student.id})">Delete</button>
                        </td>
                    </tr>
                `;
                studentTable.innerHTML += row;
            });
        });
}

function editStudent(id) {
    window.location.href = `update-student.html?id=${id}`;
}

function deleteStudent(id) {
    fetch(`${apiBaseUrl}/${id}`, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            loadStudents();
        } else {
            alert('Failed to delete student.');
        }
    });
}