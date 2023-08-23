'use strict'

const userApiUrl = 'http://localhost:8080/api/users'

async function submitNewUserRegisterForm() {
    const newUser = {
        'email': $('#emailInputRegisterFormModal').val(),
        'firstName': $('#firstNameInputRegisterFormModal').val(),
        'lastName': $('#lastNameInputRegisterFormModal').val(),
        'age': $('#ageInputRegisterFormModal').val(),
        'password': $('#passwordInputRegisterFormModal').val(),
        'rolesIds': [...document.getElementById('roleSelectionInputNewUserRegisterForm').options]
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

    let response = await fetch(`${userApiUrl}/new`, request);
    if (response.ok) {
        $('#registerNewUserFormModal').modal('hide');
        $('#userRegisterFormModal').reset();
    } else {
        $('.validationMessages').hide();
        let errors = await response.json();
        Object.keys(errors).forEach(function(key) {
            $('#' + key + 'InputRegisterFormValidation').text(errors[key]).show();
        })
    }
}