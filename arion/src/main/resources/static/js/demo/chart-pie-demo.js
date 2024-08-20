// 기본 설정
Chart.defaults.global.defaultFontFamily = 'Nunito, -apple-system, system-ui, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// 차트 초기화
var ctx = document.getElementById("myPieChart").getContext('2d');
var myPieChart = new Chart(ctx, {
	type: 'doughnut',
	data: {
		labels: ["Direct", "Referral", "Social"],
		datasets: [{
			data: [50, 50, 50],  // 초기 데이터
			backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'],
			hoverBackgroundColor: ['#FF4D75', '#3399FF', '#FFBD4D', '#43B3B3', '#8856FF', '#FF8A33'],
			hoverBorderColor: "rgba(234, 236, 244, 1)",
		}],
	},
	options: {
		maintainAspectRatio: false,
		tooltips: {
			backgroundColor: "rgb(255,255,255)",
			bodyFontColor: "#858796",
			borderColor: '#dddfeb',
			borderWidth: 1,
			xPadding: 15,
			yPadding: 15,
			displayColors: false,
			caretPadding: 10,
		},
		legend: {
			display: false
		},
		cutoutPercentage: 80,
	},
});

// AJAX 호출로 데이터베이스에서 데이터 가져오기
function updateChartData() {
	$.ajax({
		url: '/adminPieChart', // 서버의 데이터를 가져오는 엔드포인트
		method: 'POST',
		dataType: 'json',
		success: function(response) {
			// 빈 배열 생성
			var chartData = [];
			var chartLabels = [];

			// response.resultValue 배열을 순회하며 데이터를 추출
			response.forEach(function(item) {
				chartLabels.push(item.label);  // 라벨 추가
				chartData.push(item.resultValue);    // 값 추가
			});

			// 차트 데이터 업데이트
			myPieChart.data.labels = chartLabels;
			myPieChart.data.datasets[0].data = chartData;
			myPieChart.update();
		},
		error: function(xhr, status, error) {
			console.error('데이터를 가져오는데 실패했습니다:', error);
		}
	});
}

// 페이지 로드 시 차트 데이터 업데이트
$(document).ready(function() {
	updateChartData();
});
