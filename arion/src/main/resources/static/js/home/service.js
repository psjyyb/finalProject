$(document).ready(function() {
  function calculateAmounts() {
        let accountNumber = parseInt($('#accountNumber').val());
        let period = parseInt($('#subscriptionPeriod').val());
        let checkedModules = $('.module-checkbox:checked');
        let payDate = parseInt($('#regularPaymentDate').val());

        let monthlyAccountAmount = accountNumber * 1200;
        let monthlyModuleAmount = 0;

        checkedModules.each(function() {
            monthlyModuleAmount += parseInt($(this).data('price'));
        });
      
      
        let monthlyAmount = monthlyAccountAmount + monthlyModuleAmount;
        let totalAmount = monthlyAmount * (period / 30);

        calculateFirstMonthAmount(accountNumber, checkedModules, payDate, function(firstMonthAmount) {
            let remainingAmount = totalAmount - firstMonthAmount;
            let remainingMonths = Math.floor((period - 30) / 30);
            let monthlyAmountExcludingFirstMonth = remainingMonths > 0 ? remainingAmount / remainingMonths : monthlyAmount;
   
         $('input[name=accountAmount]').val(Math.trunc(monthlyAccountAmount * (period / 30)));
            $('input[name=moduleAmount]').val(Math.trunc(monthlyModuleAmount * (period / 30)));
            $('input[name=totalAmount]').val(totalAmount);
            $('input[name=firstMonthAmount]').val(Math.trunc(firstMonthAmount));
            $('input[name=monthlyAmount]').val(Math.trunc(monthlyAmountExcludingFirstMonth));
                     
            $('#accountAmount').text((monthlyAccountAmount * (period / 30)).toLocaleString().split(".")[0] + '원');
            $('#moduleAmount').text((monthlyModuleAmount * (period / 30)).toLocaleString().split(".")[0] + '원');
            $('#totalAmount').text(totalAmount.toLocaleString().split(".")[0] + '원');
            $('#firstMonthAmount').text(firstMonthAmount.toLocaleString().split(".")[0] + '원');
            $('#monthlyAmount').text(monthlyAmountExcludingFirstMonth.toLocaleString().split(".")[0] + '원');
        });
    }

    function calculateFirstMonthAmount(accountNumber, checkedModules, payDate, callback) {
        let today = new Date();

        if (isNaN(payDate) || payDate < 1 || payDate > 30) {
            $('#firstMonthAmount').text('N/A');
            callback(0);
            return;
        }
        let currentDay = today.getDate();
        let currentMonth = today.getMonth();
        let currentYear = today.getFullYear();

        let nextPayDate;
        if (currentDay <= payDate) {
            nextPayDate = new Date(currentYear, currentMonth, payDate);
        } else {
            nextPayDate = new Date(currentYear, currentMonth + 1, payDate);
        }

        let diffTime = nextPayDate - today;
        let diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

        let dailyAccountAmount = (accountNumber * 1200) / 30;
        let firstMonthAccountAmount = dailyAccountAmount * diffDays;

        let dailyModuleAmount = 0;
        checkedModules.each(function() {
            dailyModuleAmount += parseInt($(this).data('price')) / 30;
        });
        let firstMonthModuleAmount = dailyModuleAmount * diffDays;

        let firstMonthAmount = firstMonthAccountAmount + firstMonthModuleAmount;

        callback(firstMonthAmount);
    }

    $('#accountNumber').change(function() {
        if ($(this).val() > 100) {
            Swal.fire({
                icon: "error",
                text: "100인 초과는 상담직원에게 문의하세요."
            });
            $(this).val(100);
        }
        calculateAmounts();
    });

    $('#subscriptionPeriod').change(function() {
        if ($(this).val() > 30) {
            $('#regularPaymentDate').prop('disabled', false).val(10);
        } else {
            $('#regularPaymentDate').prop('disabled', true).val('');
        }

        if ($(this).val() > 1825) {
            Swal.fire({
                icon: "error",
                text: "5년 초과는 상담직원에게 문의하세요."
            });
            $(this).val(1825);
        }
        calculateAmounts();
    });

    $('#regularPaymentDate').on('input', function() {
        let value = parseInt($(this).val());
        if (value < 1 || value > 30) {
            Swal.fire({
                icon: "error",
                text: "결제일은 1~30 사이만 기입이 가능합니다."
            })
            $(this).val('');
        }
        calculateAmounts();
    });

    $('.module-checkbox').change(calculateAmounts);

    calculateAmounts();
});