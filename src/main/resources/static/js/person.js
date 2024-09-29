const apiBaseUrl = "/api/persons";

document.addEventListener('DOMContentLoaded', function () {
    if (document.getElementById('person-table')) {
        loadPersons();
    }

    const createPersonForm = document.getElementById('create-person-form');
    if (createPersonForm) {
        createPersonForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const personType = document.getElementById('personType').value;
            const person = {
                name: document.getElementById('name').value,
                emailAddress: document.getElementById('email').value,
                phoneNumber: document.getElementById('phone').value,
                address: {
                    street: document.getElementById('street').value,
                    city: document.getElementById('city').value,
                    state: document.getElementById('state').value,
                    zipCode: document.getElementById('zipCode').value,
                    country: document.getElementById('country').value
                }
            };

            if (personType === 'student') {
                person.studentNumber = document.getElementById('studentNumber').value;
                person.photo = document.getElementById('photo').value;
            } else if (personType === 'professor') {
                person.salary = document.getElementById('salary').value;
            }

            fetch(apiBaseUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(person),
            }).then(response => {
                if (response.ok) {
                    window.location.href = 'persons.html';
                } else {
                    return response.text().then(text => {
                        alert('Failed to create person: ' + text);
                    });
                }
            });
        });
    }

    const updatePersonForm = document.getElementById('update-person-form');
    if (updatePersonForm) {
        const personId = new URLSearchParams(window.location.search).get('id');
        if (personId) {
            fetch(`${apiBaseUrl}/${personId}`)
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Erro ao carregar os dados da pessoa');
                    }
                })
                .then(person => {
                    // Preenche os dados básicos da pessoa
                    document.getElementById('person-id').value = person.id;
                    document.getElementById('name').value = person.name;
                    document.getElementById('email').value = person.emailAddress;
                    document.getElementById('phone').value = person.phoneNumber;
    
                    // Preenche o endereço
                    if (person.address) {
                        document.getElementById('street').value = person.address.street || '';
                        document.getElementById('city').value = person.address.city || '';
                        document.getElementById('state').value = person.address.state || '';
                        document.getElementById('zipCode').value = person.address.zipCode || '';
                        document.getElementById('country').value = person.address.country || '';
                    }
    
                    // Verifica se a pessoa é Estudante ou Professor e mostra os campos correspondentes
                    if (person.studentNumber !== undefined) {
                        document.getElementById('personType').value = 'student';
                        document.getElementById('studentNumber').value = person.studentNumber || '';
                        document.getElementById('photo').value = person.photo || '';
                        document.getElementById('studentFields').style.display = 'block';
                        document.getElementById('professorFields').style.display = 'none';
                    } else if (person.salary !== undefined) {
                        document.getElementById('personType').value = 'professor';
                        document.getElementById('salary').value = person.salary || '';
                        document.getElementById('studentFields').style.display = 'none';
                        document.getElementById('professorFields').style.display = 'block';
                    } else {
                        document.getElementById('personType').value = 'person';
                        document.getElementById('studentFields').style.display = 'none';
                        document.getElementById('professorFields').style.display = 'none';
                    }
                })
                .catch(error => {
                    console.error(error);
                    alert('Erro ao carregar os dados da pessoa. Verifique o console para mais detalhes.');
                });
        }
    
        // Função de submit para atualizar a pessoa
        updatePersonForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const id = document.getElementById('person-id').value;
            const personType = document.getElementById('personType').value;
    
            // Dados básicos de uma pessoa
            const person = {
                name: document.getElementById('name').value,
                emailAddress: document.getElementById('email').value,
                phoneNumber: document.getElementById('phone').value,
                address: {
                    street: document.getElementById('street').value,
                    city: document.getElementById('city').value,
                    state: document.getElementById('state').value,
                    zipCode: document.getElementById('zipCode').value,
                    country: document.getElementById('country').value
                }
            };
    
            // Adiciona os campos extras de acordo com o tipo
            if (personType === 'student') {
                person.studentNumber = document.getElementById('studentNumber').value;
                person.photo = document.getElementById('photo').value;
            } else if (personType === 'professor') {
                person.salary = document.getElementById('salary').value;
            }
    
            // Faz o PUT para atualizar os dados
            fetch(`${apiBaseUrl}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(person),
            }).then(response => {
                if (response.ok) {
                    window.location.href = 'persons.html';
                } else {
                    alert('Erro ao atualizar pessoa.');
                }
            });
        });
    }
    
});

function loadPersons() {
    fetch(apiBaseUrl)
        .then(response => response.json())
        .then(data => {
            const personTable = document.getElementById('person-table');
            personTable.innerHTML = '';
            data.forEach(person => {
                let personType = '';
                let studentNumber = '';
                let photo = '';
                let salary = '';

                if (person.studentNumber !== undefined) {
                    personType = 'Student';
                    studentNumber = person.studentNumber;
                    photo = person.photo || '';
                } else if (person.salary !== undefined) {
                    personType = 'Professor';
                    salary = person.salary;
                } else {
                    personType = 'Person';
                }

                const row = `
                    <tr>
                        <td>${person.id}</td>
                        <td>${person.name}</td>
                        <td>${person.emailAddress}</td>
                        <td>${person.phoneNumber}</td>
                        <td>${person.address ? person.address.street : ''}</td>
                        <td>${person.address ? person.address.city : ''}</td>
                        <td>${person.address ? person.address.state : ''}</td>
                        <td>${person.address ? person.address.zipCode : ''}</td>
                        <td>${person.address ? person.address.country : ''}</td>
                        <td>${personType}</td>
                        <td>${studentNumber}</td>
                        <td>${photo}</td>
                        <td>${salary}</td>
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

function editPerson(id) {
    window.location.href = `update-person.html?id=${id}`;
}

function deletePerson(id) {
    fetch(`${apiBaseUrl}/${id}`, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            loadPersons();
        }
    });
}

document.getElementById('personType').addEventListener('change', function () {
    const personType = this.value;
    const studentFields = document.getElementById('studentFields');
    const professorFields = document.getElementById('professorFields');
    
    if (personType === 'student') {
        studentFields.style.display = 'block';
        professorFields.style.display = 'none';
    } else if (personType === 'professor') {
        studentFields.style.display = 'none';
        professorFields.style.display = 'block';
    } else {
        studentFields.style.display = 'none';
        professorFields.style.display = 'none';
    }
});
