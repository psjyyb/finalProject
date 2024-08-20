// 기본 설정
Chart.defaults.global.defaultFontFamily = 'Nunito, -apple-system, system-ui, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

function number_format(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(',', '').replace(' ', '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function(n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec) + ' ₩';  // 원 표시 추가
}

// 차트 초기화
var ctx = document.getElementById("myAreaChart").getContext('2d');
var myLineChart = new Chart(ctx, {
	type: 'line',
	data: {
		labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],  // 초기 레이블 (빈 값)
		datasets: [{
			label: "Earnings",
			lineTension: 0.3,
			backgroundColor: "rgba(78, 115, 223, 0.05)",
			borderColor: "rgba(78, 115, 223, 1)",
			pointRadius: 3,
			pointBackgroundColor: "rgba(78, 115, 223, 1)",
			pointBorderColor: "rgba(78, 115, 223, 1)",
			pointHoverRadius: 3,
			pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			pointHitRadius: 10,
			pointBorderWidth: 2,
			data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],  // 초기 데이터 (빈 값)
		}],
	},
	options: {
		maintainAspectRatio: false,
		layout: {
			padding: {
				left: 10,
				right: 25,
				top: 25,
				bottom: 0
			}
		},
		scales: {
			xAxes: [{
				time: {
					unit: 'date'
				},
				gridLines: {
					display: false,
					drawBorder: false
				},
				ticks: {
					maxTicksLimit: 7
				}
			}],
			yAxes: [{
				ticks: {
					maxTicksLimit: 5,
					padding: 10,
					callback: function(value, index, values) {
						return '$' + number_format(value);
					}
				},
				gridLines: {
					color: "rgb(234, 236, 244)",
					zeroLineColor: "rgb(234, 236, 244)",
					drawBorder: false,
					borderDash: [2],
					zeroLineBorderDash: [2]
				}
			}],
		},
		legend: {
			display: false
		},
		tooltips: {
			backgroundColor: "rgb(255,255,255)",
			bodyFontColor: "#858796",
			titleMarginBottom: 10,
			titleFontColor: '#6e707e',
			titleFontSize: 14,
			borderColor: '#dddfeb',
			borderWidth: 1,
			xPadding: 15,
			yPadding: 15,
			displayColors: false,
			intersect: false,
			mode: 'index',
			caretPadding: 10,
			callbacks: {
				label: function(tooltipItem, chart) {
					var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
					return datasetLabel + ': $' + number_format(tooltipItem.yLabel);
				}
			}
		}
	}
});

// AJAX 호출로 데이터베이스에서 데이터 가져오기
function updateChartDatas() {
	console.log('여긴옴??');
	$.ajax({
		url: '/adminAreaChart', // 서버에서 데이터를 가져오는 엔드포인트
		method: 'POST',
		dataType: 'json',
		success: function(response) {
			var chartData = [];
			var chartLabels = [];
			console.log(response); // 데이터 확인
			response.forEach(function(item) {
				chartLabels.push(item.label);  // 라벨 추가
				chartData.push(item.resultValue);    // 값 추가
			});
			console.log(chartLabels); // 라벨 확인
			console.log(chartData);   // 데이터 확인
			// 가져온 데이터로 차트 업데이트
			myLineChart.data.labels = chartLabels;
			myLineChart.data.datasets[0].data = chartData;
			myLineChart.update();
		},
		error: function(xhr, status, error) {
			console.error('데이터를 가져오는데 실패했습니다:', error);
		}
	});
}

// 페이지 로드 시 차트 데이터 업데이트
$(document).ready(function() {
	updateChartDatas();
});