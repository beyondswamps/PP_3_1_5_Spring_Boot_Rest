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
                        class="btn btn-info"
                        data-toggle="modal"
                        data-target="#userDeleteModal"
                        onclick="toggleUserDeleteModal(${eachUser.id})">
                  Edit
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
        .forEach(option => option.selected=true);

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
        .forEach(option => option.selected=false);
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

    let response = await fetch(url + '/edit', request);
    document.getElementById('closeModalXButton').click();
    refreshUserTable();
}

async function submitUserDelete() {

}