'use strict'

const url = 'http://localhost:8080/api/users'

fillUserTable();

async function fillUserTable() {
    let response = await fetch(url);
    let users = await response.json();
    let contentHtml = ''
    let usersTable = document.getElementById('usersTableBody')

    users.forEach(eachUser => {
        let rolesStr = eachUser.roles.map(role => role.name).join(', ')
        contentHtml += `
            <tr class = "container">
              <td>${eachUser.id}</td>
              <td>${eachUser.firstName}</td>
              <td>${eachUser.lastName}</td>
              <td>${eachUser.age}</td>
              <td>${eachUser.email}</td>
              <td>${rolesStr}</td>
            </tr>`
    })
    usersTable.innerHTML = contentHtml
}