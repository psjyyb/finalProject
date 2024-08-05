$(document).ready(function() {
    const $allCheck = $('#allcheck');
    const $checkNeed1 = $('#checkNeed1');
    const $checkNeed2 = $('#checkNeed2');
    const $checkChoice = $('#checkChoice');
    const $signupButton = $('#signupButton');

    function updateSignupButtonState() {
        $signupButton.prop('disabled', !($checkNeed1.is(':checked') && $checkNeed2.is(':checked')));
    }

    $allCheck.change(function() {
        const allChecked = $allCheck.is(':checked');
        $checkNeed1.prop('checked', allChecked);
        $checkNeed2.prop('checked', allChecked);
        $checkChoice.prop('checked', allChecked);
        updateSignupButtonState();
    });

    $checkNeed1.change(updateSignupButtonState);
    $checkNeed2.change(updateSignupButtonState);

});