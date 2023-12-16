$(document).ready(function () {
    updateTotalTime(); // Вызов функции при загрузке страницы
});

function updateTotalTime() {
    var totalTime = 0;

    // Найти все элементы input с id, содержащим "workingTime_"
    $('[id^="workingTime_"]:visible').each(function () {
        var timeValue = $(this).val();

        // Проверить, что значение времени не пустое
        if (timeValue.trim() !== "") {
            // Разбить строку времени на часы, минуты и секунды
            var timeParts = timeValue.split(':');
            var hours = parseInt(timeParts[0], 10);
            var minutes = parseInt(timeParts[1], 10);
            var seconds = parseInt(timeParts[2], 10);

            // Преобразовать все в секунды и добавить к общей сумме
            totalTime += hours * 3600 + minutes * 60 + seconds;
        }
    });

    // Преобразовать общую сумму обратно в формат hh:mm:ss
    var formattedTotalTime = secondsToHMS(totalTime);

    // Отобразить обновленную сумму времени
    $('#totalTime').text(formattedTotalTime);
}

// Функция для преобразования секунд в формат hh:mm:ss
function secondsToHMS(seconds) {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    seconds = seconds % 60;

    return pad(hours) + ':' + pad(minutes) + ':' + pad(seconds);
}

// Функция для добавления ведущего нуля к числам < 10
function pad(num) {
    return (num < 10 ? '0' : '') + num;
}

// Вызвать функцию обновления при изменении значений в полях
$('[id^="workingTime_"]').on('input', function () {
    updateTotalTime();
});

function addStep() {
    // Находим первый элемент с классом 'step'
    const firstStep = $('.step').first();

    // Клонируем первый элемент
    var newStep = firstStep.clone();
    newStep.find('input, select').val('');
    newStep.find('[id]').each(function () {
        var oldId = $(this).attr('id');
        var newId = oldId.replace(/_\d+$/, '_' + $('.step').length);
        $(this).attr('id', newId);
    });

    newStep.find('[label]').each(function () {
        var oldId = $(this).attr('label');
        var newId = oldId.replace(/_\d+$/, '_' + $('.step').length);
        $(this).attr('id', newId);
    });

    // Изменяем айди всех элементов внутри нового шага
    newStep.find('[name]').each(function () {
        var oldName = $(this).attr('name');
        var newName = oldName.replace(/_\d+/, '_' + $('.step').length);
        $(this).attr('name', newName);
    });
    newStep.find('[name]').each(function () {
        var oldName = $(this).attr('name');
        var newName = oldName.replace(/\[\d+\]/, '[' + $('.step').length + ']');
        $(this).attr('name', newName);
    });

    newStep.find('button').attr('onclick', 'removeStep(\'' + $('.step').length + '\')');
    newStep.show();
    updateTotalTime();
    $('.step').last().after(newStep);
}

function removeStep(index) {
    document.getElementById('id_' + index).value = "-1";
    document.getElementById('workingTime_' + index).value = "00:00:00";
    $('.step:eq(' + index + ')').hide();
    updateTotalTime();
}