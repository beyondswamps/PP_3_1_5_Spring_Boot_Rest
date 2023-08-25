'use strict'

const urlUserApi = 'http://localhost:8080'

init();

function init() {
    if ($('#adminTab').val() === undefined) {
        $('#userTab').addClass('active');
        $('#userTabContent').addClass('show active');
    }
}

async function updatePassword() {

    $('.passwordValidation').hide();
    const currentPassword = $('#currentPasswordInputChangePasswordModal').val();
    const newPassword = $('#newPasswordInputChangePasswordModal').val();

    if (currentPassword === '' || newPassword === '') {
        $('#passwordValidation').text("Passwords cannot be blank").show();
        return;
    }
    if (currentPassword === newPassword) {
        $('#passwordValidation').text("Passwords match. Nothing to change").show();
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
        resetUpdatePasswordModal();
    } else {
        let error = await response.json();
        $('#passwordValidation').text(error.message);
    }
}

function resetUpdatePasswordModal() {
    $('#changePasswordModalForm')[0].reset()
    $('.validationMessages').hide().empty();
}