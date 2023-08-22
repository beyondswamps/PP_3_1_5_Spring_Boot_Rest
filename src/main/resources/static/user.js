'use strict'


init();

function init() {
    if ($('#adminTab').val() === undefined) {
        $('#userTab').addClass('active');
        $('#userTabContent').addClass('show active');
    }
}