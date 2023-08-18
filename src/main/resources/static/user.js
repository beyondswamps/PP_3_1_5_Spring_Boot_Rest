'use strict'

init();

function init() {
    if (document.getElementById('adminTab') === null) {
        $('#userTab').addClass('active');
        $('#userTabContent').addClass('show active');
    }
}