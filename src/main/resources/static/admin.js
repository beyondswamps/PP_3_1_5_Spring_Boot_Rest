'use strict'

const url = 'http://localhost:8080/api/users'

init();

function init() {
    refreshUserTable();
}

async function refreshUserTable() {
    const users = await (await fetch(url)).json();

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
    $('#usersTableBody').html(contentHtml);
}

async function toggleUserEditModal(id) {
    const user = await (await fetch(`${url}/${id}`)).json();

    $('#idInput').val(id);
    $('#firstNameInput').val(user.firstName);
    $('#lastNameInput').val(user.lastName);
    $('#ageInput').val(user.age);
    $('#emailInput').val(user.email);
    $('#passwordInput').val(user.password);
    [...document.getElementById('roles').options]
        .filter(option => user.rolesIds.includes(parseInt(option.value)))
        .forEach(option => option.selected = true);

    $('#userEditModal').show();
}

async function submitUserEdit() {
    let request = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: $('#idInput').val(),
            firstName: $('#firstNameInput').val(),
            lastName: $('#lastNameInput').val(),
            age: $('#ageInput').val(),
            email: $('#emailInput').val(),
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

    $('#idInputDeleteModal').val(id);
    $('#firstNameInputDeleteModal').val(user.firstName);
    $('#lastNameInputDeleteModal').val(user.lastName);
    $('#ageInputDeleteModal').val(user.age);
    $('#emailInputDeleteModal').val(user.email);
    // document.getElementById('').value = user.password;
    [...document.getElementById('rolesSelectDeleteModal').options]
        .filter(option => user.rolesIds.includes(parseInt(option.value)))
        .forEach(option => option.selected = true);

    $('#userDeleteModal').show();
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
        'email': $('#emailInputNewUserForm').val(),
        'firstName': $('#firstNameInputNewUserForm').val(),
        'lastName': $('#lastNameInputNewUserForm').val(),
        'age': $('#ageInputNewUserForm').val(),
        'password': $('#passwordInputNewUserForm').val(),
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
