'use strict'

const urlUserApi = 'http://localhost:8080/api/users'

init();

function init() {
    if ($('#adminTab').val() === undefined) {
        $('#userTab').addClass('active');
        $('#userTabContent').addClass('show active');
    }
}

async function updatePassword() {

    const currentPassword = $('#currentPasswordInputChangePasswordModal').val();
    const newPassword = $('#newPasswordInputChangePasswordModal').val();

    if (currentPassword === '' || newPassword === '') {
        $('#passwordValidation').text("Passwords cannot be blank");
        return;
    }

    let request = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({'currentPassword': currentPassword, 'newPassword': newPassword})
    }
    let response = await fetch(`${urlUserApi}/updatepass`, request);
    if (response.ok) {
        $('#changePasswordModal').modal('hide');
    } else {
        let error = await response.json();
        $('#passwordValidation').text(error.message);
    }
}