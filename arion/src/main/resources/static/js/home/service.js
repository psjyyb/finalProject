$(document).ready(function() {
	function calculateAmounts() {
		let accountNumber = parseInt($('#accountNumber').val());
		let period = parseInt($('#subscriptionPeriod').val());
		let checkedModules = $('.module-checkbox:checked');
		let accountAmount = accountNumber * 1200 * (period/30);
		let payDate = ($('#regularPaymentDate').val());
		let moduleAmount = 0 ;


		checkedModules.each(function() {
			moduleAmount += parseInt($(this).data('price') * (period / 30));
		});
	
		let monthlyAmount = accountAmount + moduleAmount;
		let firstmonthAmount;
		let totalAmount = monthlyAmount * (period / 30);
		
		$('input[name=accountAmount]').val(accountAmount);
		$('input[name=monthlyAmount]').val(monthlyAmount);
		$('input[name=moduleAmount]').val(moduleAmount);
		$('input[name=totalAmount]').val(totalAmount);
		
		$('#accountAmount').text(accountAmount.toLocaleString().split(".")[0] + '원');
		$('#monthlyAmount').text(monthlyAmount.toLocaleString().split(".")[0] + '원');
		$('#totalAmount').text(totalAmount.toLocaleString().split(".")[0] + '원');
		$('#moduleAmount').text(moduleAmount.toLocaleString().split(".")[0] + '원');
		
	}
		
	$('#accountNumber').change(function() {
		if($(this).val() > 100){
			Swal.fire({
				icon: "error",
				text: "100인 초과는 상담직원에게 문의하세요."
			});
			$(this).val() == 100;
		}
		calculateAmounts();
   	});

	$('#subscriptionPeriod').change(function() {
		if($(this).val() > 30) {
			$('#regularPaymentDate').prop('disabled', false).val(10);
		}else{
			$('#regularPaymentDate').prop('disabled', true).val('');
		}
		
		if($(this).val() > 1825) {
			Swal.fire({
				icon: "error",
				text: "5년 초과는 상담직원에게 문의하세요."
			});
			$(this).val() == 1825;
		}
		calculateAmounts();
	});
	
	$('.module-checkbox').change(calculateAmounts);
	
	calculateAmounts();
})