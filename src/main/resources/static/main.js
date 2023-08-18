'use strict'

const url = 'http://localhost:8080/api/users'

refreshUserTable();


async function refreshUserTable() {
    const users = await (await fetch(url)).json();
    const usersTable = document.getElementById('usersTableBody');

    let contentHtml = '';
    users.forEach(
        eachUser => {
            const rolesStr = eachUser
                .roles
                .map(role => role.name)
                .join(', ')
            contentHtml += `
            <tr class = "container">
              <td>${eachUser.id}</td>
              <td>${eachUser.firstName}</td>
              <td>${eachUser.lastName}</td>
              <td>${eachUser.age}</td>
              <td>${eachUser.email}</td>
              <td>${rolesStr}</td>
              <td>
                <button id="toggleEditUserModalButton"
                        class="btn btn-info"
                        data-toggle="modal"
                        data-target="#userEditModal"
                        onclick="toggleUserEditModal(${eachUser.id})">
                  Edit
                </button>
              </td>
              <td>
                <button id="toggleDeleteUserModalButton"
                        class="btn btn-danger"
                        data-toggle="modal"
                        data-target="#userDeleteModal"
                        onclick="toggleUserDeleteModal(${eachUser.id})">
                  Delete
                </button>
              </td>
            </tr>`
        }
    )
    usersTable.innerHTML = contentHtml;

}

async function toggleUserEditModal(id) {
    const user = await (await fetch(url + `/${id}`)).json();

    document.getElementById('idInput').value = id;
    document.getElementById('firstNameInput').value = user.firstName;
    document.getElementById('lastNameInput').value = user.lastName;
    document.getElementById('ageInput').value = user.age;
    document.getElementById('emailInput').value = user.email;
    document.getElementById('passwordInput').value = user.password;
    [...document.getElementById('roles').options]
        .filter(option => user.rolesIds.includes(parseInt(option.value)))
        .forEach(option => option.selected = true);

    document.getElementById('userEditModal').style.display = 'block';
}

function clearUserEditModal() {
    document.getElementById('idInput').value = '';
    document.getElementById('firstNameInput').value = '';
    document.getElementById('lastNameInput').value = '';
    document.getElementById('ageInput').value = '';
    document.getElementById('emailInput').value = '';
    document.getElementById('passwordInput').value = '';
    [...document.getElementById('roles').options]
        .forEach(option => option.selected = false);
}

async function submitUserEdit() {
    let request = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('idInput').value,
            firstName: document.getElementById('firstNameInput').value,
            lastName: document.getElementById('lastNameInput').value,
            age: document.getElementById('ageInput').value,
            email: document.getElementById('emailInput').value,
            rolesIds: [...document.getElementById('roles').options]
                .filter(option => option.selected === true)
                .map(option => parseInt(option.value))
        })
    }

    let response = await fetch(`${url}/edit`, request);
    if (response.ok) {
        document.getElementById('closeEditModalXButton').click();
        refreshUserTable()
    }
}

async function toggleUserDeleteModal(id) {
    const user = await (await fetch(url + `/${id}`)).json();

    document.getElementById('idInputDeleteModal').value = id;
    document.getElementById('firstNameInputDeleteModal').value = user.firstName;
    document.getElementById('lastNameInputDeleteModal').value = user.lastName;
    document.getElementById('ageInputDeleteModal').value = user.age;
    document.getElementById('emailInputDeleteModal').value = user.email;
    // document.getElementById('').value = user.password;
    [...document.getElementById('rolesSelectDeleteModal').options]
        .filter(option => user.rolesIds.includes(parseInt(option.value)))
        .forEach(option => option.selected = true);

    document.getElementById('userDeleteModal').style.display = 'block';
}

async function submitUserDelete() {
    let request = {
        method: 'POST'
    };

    let id = document.getElementById('idInputDeleteModal').value;

    await fetch(`${url}/delete?id=${id}`, request);

    document.getElementById('closeDeleteModalXButton').click();

    refreshUserTable();
}

async function submitNewUserForm() {
    const newUser = {
        'email': document.getElementById('emailInputNewUserForm').value,
        'firstName': document.getElementById('firstNameInputNewUserForm').value,
        'lastName': document.getElementById('lastNameInputNewUserForm').value,
        'age': document.getElementById('ageInputNewUserForm').value,
        'password': document.getElementById('passwordInputNewUserForm').value,
        'rolesIds': [...document.getElementById('roleSelectionInputNewUserForm').options]
            .filter(option => option.selected === true)
            .map(option => parseInt(option.value))
    }

    const request = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    }

    let response = await fetch(`${url}/new`, request);
    if (response.ok) {
        refreshUserTable();
        document.getElementById('usersTableTab').click();
        clearNewUserForm();
    }
}

function clearNewUserForm() {
    document.getElementById('firstNameInputNewUserForm').value = '';
    document.getElementById('lastNameInputNewUserForm').value = '';
    document.getElementById('ageInputNewUserForm').value = '';
    document.getElementById('emailInputNewUserForm').value = '';
    document.getElementById('passwordInputNewUserForm').value = '';
    [...document.getElementById('roleSelectionInputNewUserForm').options]
        .forEach(option => option.selected = false);
}