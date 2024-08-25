document.addEventListener('DOMContentLoaded', function() {
		let editorInstance = null;
		let selectedApprovers = []; // 선택된 결재자 목록
		let selectedReferences = []; // 선택된 참조자 목록

		// 휴가신청서 양식 요소를 숨김 처리
		const leaveForm = document.querySelector('#leaveForm');
        leaveForm.style.display = 'none';
 
 		setInterval(function () {
        	if (leaveForm.style.display !== 'none' && !leaveForm.classList.contains('visible')) {
            	leaveForm.style.display = 'none';
            	leaveForm.style.overflow = 'hidden';
        	}
    	}, 0);


		// CKEditor 초기화
		$('#templateSelect').on('change', function() {
			let tempNo = $(this).val();
			let tempName = $(this).find('option:selected').text(); // 선택된 템플릿의 이름 가져오기
			
			if (tempNo === "") {
			// 템플릿을 선택하세요 옵션이 선택된 경우
				$('#docName').val(''); // docName 필드 초기화
				$('#editor').hide(); // CKEditor 숨기기
				$('#leaveForm').hide(); // 휴가 신청서 양식 숨기기
				$('#placeholder').show(); // 안내 메시지 표시
				if (editorInstance !== null) {
					editorInstance.destroy()
					.then(() => {
						editorInstance = null;
					})
					.catch(error => {
						console.error('error', error);
					});
				}
				return;
			}
			
			if (tempName === '휴가신청서') { 
				// 휴가 신청서 템플릿이 선택된 경우
				$('#docName').val('휴가신청서');
				$('#editor').hide(); // CKEditor 숨기기
				$('#placeholder').hide(); // 안내 메시지 숨기기
				leaveForm.style.display = 'block'; // 휴가 신청서 양식 보이기
				leaveForm.classList.add('visible');  // 요소를 표시할 때만 'visible' 클래스를 추가합니다.
				
				if (editorInstance !== null) {
					// 이미 CKEditor가 초기화된 경우, 에디터를 제거
					editorInstance.destroy()
						.then(() => {
							editorInstance = null;
						})
						.catch(error => {
							console.error(error);
						});
				}
			} else {
				// 다른 템플릿이 선택된 경우
				leaveForm.style.display = 'none'; // 휴가 신청서 양식 숨기기
				leaveForm.classList.remove('visible');  // 다른 템플릿이 선택되면 'visible' 클래스를 제거합니다.
				$('#docName').val(tempName); // docName 필드에 템플릿 이름 설정
				$('#placeholder').hide(); // 안내 메시지 숨기기
				$('#editor').show(); // 에디터 표시

				if (editorInstance === null) {
					// CKEditor가 아직 초기화되지 않은 경우에만 초기화
					ClassicEditor
						.create(document.querySelector('#editor'))
						.then(editor => {
							editorInstance = editor;
							editor.setData("템플릿을 불러오는 중..."); // 초기 메시지 설정
							// AJAX 요청으로 템플릿 내용을 불러오기
							$.ajax({
								url: '/group/doc/templateContent',
								type: 'GET',
								data: { tempNo: tempNo },
							})
								.done(function(data) {
									editor.setData(data); // 불러온 데이터로 CKEditor 내용 설정
								})
									.fail(function(error) {
									console.log(error);
								});
						})
						.catch(error => {
							console.error(error);
						});
				} else {
					// 이미 CKEditor가 초기화된 경우, 텍스트만 갱신
					$.ajax({
						url: '/group/doc/templateContent',
						type: 'GET',
						data: { tempNo: tempNo },
					})
						.done(function(data) {
							editorInstance.setData(data); // 불러온 데이터로 CKEditor 내용 설정
						})
						.fail(function(error) {
							console.log('Error:', error);
						});
				}
			}
		});

		// 결재자 모달에서 부서 목록 로드
		$('#approverModal').on('show.bs.modal', function() {
			$.ajax({
				url: '/group/doc/department',
				type: 'GET',
				success: function(departments) {
					let departmentHtml = '';
					departments.forEach(department => {
						departmentHtml += `<div class="department-item" data-department="${department}">${department}</div>`;
					});
					$('#departmentList').html(departmentHtml);
				},
				error: function(error) {
					console.log('Error:', error);
				}
			});
		});

		// 참조자 모달에서 부서 목록 로드
		$('#referenceModal').on('show.bs.modal', function() {
			$.ajax({
				url: '/group/doc/department',
				type: 'GET',
				success: function(departments) {
					let departmentHtml = '';
					departments.forEach(department => {
						departmentHtml += `<div class="department-item" data-department="${department}">${department}</div>`;
					});
					$('#referenceDepartmentList').html(departmentHtml);
				},
				error: function(error) {
					console.log('Error:', error);
				}
			});
		});

		// 부서 클릭 시 사원 목록 로드 (결재자)
		$(document).on('click', '#departmentList .department-item', function() {
			const departmentName = $(this).data('department');
			$.ajax({
				url: '/group/doc/employee',
				type: 'GET',
				data: { departmentName: departmentName },
				success: function(employees) {
					let employeeHtml = '';
					employees.forEach(employee => {
						employeeHtml += `
									<div class="employee-item" data-empno="${employee.employeeNo}" data-name="${employee.employeeName}" data-rank="${employee.rankName}" data-department="${employee.departmentName}" data-rankranking="${employee.rankRanking}">
										<label>
											${employee.employeeName} (${employee.rankName})
										</label>
									</div>`;
					});
					$('#employeeList').html(employeeHtml);
				},
				error: function(error) {
					console.log('Error:', error);
				}
			});
		});

		// 부서 클릭 시 사원 목록 로드 (참조자)
		$(document).on('click', '#referenceDepartmentList .department-item', function() {
			const departmentName = $(this).data('department');
			$.ajax({
				url: '/group/doc/employee',
				type: 'GET',
				data: { departmentName: departmentName },
				success: function(employees) {
					let employeeHtml = '';
					employees.forEach(employee => {
						employeeHtml += `
									<div class="employee-item" data-empno="${employee.employeeNo}" data-name="${employee.employeeName}" data-rank="${employee.rankName}" data-department="${employee.departmentName}">
										<label>
											${employee.employeeName} (${employee.rankName})
										</label>
									</div>`;
					});
					$('#referenceEmployeeList').html(employeeHtml);
				},
				error: function(error) {
					console.log('Error:', error);
				}
			});
		});

		// 사원 클릭 시 결재자 목록에 추가 (모달 내)
		$(document).on('click', '#employeeList .employee-item', function() {
			const employeeNo = $(this).data('empno');
			const employeeName = $(this).data('name');
			const rankName = $(this).data('rank');
			const departmentName = $(this).data('department');
			const rankRanking = $(this).data('rankranking');

			// 중복 체크
			if (selectedApprovers.some(approver => approver.employeeNo === employeeNo)) {
				return;
			}

			// 선택한 결재자를 목록에 추가
			selectedApprovers.push({
				employeeNo: employeeNo,
				employeeName: employeeName,
				rankName: rankName,
				departmentName: departmentName,
				rankRanking: rankRanking
			});

			updateApproverList();
		});

		// 사원 클릭 시 참조자 목록에 추가 (모달 내)
		$(document).on('click', '#referenceEmployeeList .employee-item', function() {
			const employeeNo = $(this).data('empno');
			const employeeName = $(this).data('name');
			const rankName = $(this).data('rank');
			const departmentName = $(this).data('department');

			// 중복 체크
			if (selectedReferences.some(reference => reference.employeeNo === employeeNo)) {
				return;
			}

			// 선택한 참조자를 목록에 추가
			selectedReferences.push({
				employeeNo: employeeNo,
				employeeName: employeeName,
				rankName: rankName,
				departmentName: departmentName
			});

			updateReferenceList();
		});

		// 결재자 목록 업데이트 (모달 내)
		function updateApproverList() {
			// 높은 순번부터 정렬
			selectedApprovers.sort((a, b) => b.rankRanking - a.rankRanking);

			let approverHtml = '';
			selectedApprovers.forEach(approver => {
				approverHtml += `
							<li data-empno="${approver.employeeNo}">
								${approver.employeeName} (${approver.rankName}) - ${approver.departmentName}
								<button class="remove-approver">X</button>
							</li>`;
			});
			$('#selectedApproversListModal').html(approverHtml);
		}

		// 참조자 목록 업데이트 (모달 내)
		function updateReferenceList() {
			let referenceHtml = '';
			selectedReferences.forEach(reference => {
				referenceHtml += `
							<li data-empno="${reference.employeeNo}">
								${reference.employeeName} (${reference.rankName}) - ${reference.departmentName}
								<button class="remove-reference">X</button>
							</li>`;
			});
			$('#selectedReferencesListModal').html(referenceHtml);
		}

		// 결재자 삭제 (모달 내)
		$(document).on('click', '.remove-approver', function() {
			const employeeNo = $(this).closest('li').data('empno');
			selectedApprovers = selectedApprovers.filter(approver => approver.employeeNo !== employeeNo);
			updateApproverList();
		});

		// 참조자 삭제 (모달 내)
		$(document).on('click', '.remove-reference', function() {
			const employeeNo = $(this).closest('li').data('empno');
			selectedReferences = selectedReferences.filter(reference => reference.employeeNo !== employeeNo);
			updateReferenceList();
		});

		// 결재자 선택 완료
		$('#approverSelectDone').on('click', function() {
			let approverHtml = '';
			selectedApprovers.forEach(approver => {
				approverHtml += `
							<li>
								${approver.employeeName} (${approver.rankName}) - ${approver.departmentName}
							</li>`;
			});
			$('#selectedApproversList').html(approverHtml);
			$('#approverModal').modal('hide');
		});

		// 참조자 선택 완료
		$('#referenceSelectDone').on('click', function() {
			let referenceHtml = '';
			selectedReferences.forEach(reference => {
				referenceHtml += `
							<li>
								${reference.employeeName} (${reference.rankName}) - ${reference.departmentName}
							</li>`;
			});
			$('#selectedReferencesList').html(referenceHtml);
			$('#referenceModal').modal('hide');
		});

		// 다중 파일 업로드 처리
		$('#files').on('change', function() {
			let files = $(this)[0].files;
			let fileList = $('#fileList');
			fileList.empty();

			for (let i = 0; i < files.length; i++) {
				let fileName = files[i].name;
				let listItem = $('<li>').text(fileName);
				let removeButton = $('<button>').text('삭제').on('click', function() {
					$(this).parent().remove();
				});
				listItem.append(removeButton);
				fileList.append(listItem);
			}
		});

		$('#submitDocument').on('click', function(event) {
			event.preventDefault();

			// 필수 항목 확인
			const docTitle = $('input[name="docTitle"]').val();
			const templateSelected = $('#templateSelect').val();
			const templateName = $('#templateSelect option:selected').text(); // 선택된 템플릿의 이름 가져오기

			if (selectedApprovers.length === 0) {
				Swal.fire({
					icon: "warning",
					text: "결재자를 선택해주세요."
				});
				return;
			}
			if (!docTitle) {
				Swal.fire({
					icon: "warning",
					text: "제목을 입력해주세요."
				});
				return;
			}
			if (!templateSelected) {
				Swal.fire({
					icon: "warning",
					text: "템플릿을 선택해주세요."
				});
				return;
			}

			if (templateName === '휴가신청서') {
				// 휴가 신청서의 경우
				const startDate = $('input[name="startDate"]').val();
				const startDateTime = $('select[name="startDateTime"]').val() || '00:00';
				const endDate = $('input[name="endDate"]').val();
				const endDateTime = $('select[name="endDateTime"]').val() || '18:00';

				if (startDate === '') {
					Swal.fire({
						icon: 'warning',
						text: '시작일을 선택해주세요.'
					});
					return;
				}

				if (endDate === '') {
					Swal.fire({
						icon: 'warning',
						text: '종료일을 선택해주세요.'
					});
					return;
				}

				const fullStartDate = startDate + ' ' + startDateTime;
				const fullEndDate = endDate + ' ' + endDateTime;

				const formData = new FormData($('#writeDoc')[0]);

				// 기존에 있던 startDate와 endDate를 삭제
				formData.delete('startDate');
				formData.delete('endDate');

				// 병합된 날짜와 시간을 다시 추가
				formData.append('startDate', fullStartDate);
				formData.append('endDate', fullEndDate);

				// 결재자 및 참조자 목록을 FormData에 추가
				selectedApprovers.forEach((approver, index) => {
					formData.append(`approverIds`, approver.employeeNo);
				});

				selectedReferences.forEach((reference, index) => {
					formData.append(`referenceIds`, reference.employeeNo);
				});

				// AJAX 요청 보내기
				$.ajax({
					url: '/group/doc/writeDoc', // 휴가 신청서에 대한 별도의 URL
					type: 'POST',
					data: formData,
					contentType: false,
					processData: false,
					success: function(response) {
						Swal.fire({
							icon: "success",
							text: "휴가 신청서가 상신되었습니다.",
							willClose: () => {
								window.location.href = '/group/doc/apprProgress';
							}
						});
					},
					error: function(error) {
						console.log('Error:', error);
						Swal.fire({
							icon: "error",
							text: "상신 중 오류가 발생했습니다."
						});
					}
				});

			} else {
				// 일반 템플릿의 경우
				const formData = new FormData($('#writeDoc')[0]);

				// 결재자 및 참조자 목록을 FormData에 추가
				selectedApprovers.forEach((approver, index) => {
					formData.append(`approverIds`, approver.employeeNo);
				});

				selectedReferences.forEach((reference, index) => {
					formData.append(`referenceIds`, reference.employeeNo);
				});

				// CKEditor의 내용을 formData에 추가
				if (editorInstance) {
					const content = editorInstance.getData(); // CKEditor 내용 가져오기
					formData.append('docContent', content);
				}

				// AJAX 요청 보내기
				$.ajax({
					url: '/group/doc/writeDoc', // 일반 문서 처리 URL
					type: 'POST',
					data: formData,
					contentType: false,
					processData: false,
					success: function(response) {
						Swal.fire({
							icon: "success",
							text: "문서가 상신되었습니다.",
							willClose: () => {
								window.location.href = '/group/doc/apprProgress';
							}
						});
					},
					error: function(error) {
						console.log('Error:', error);
						Swal.fire({
							icon: "error",
							text: "상신 중 오류가 발생했습니다."
						});
					}
				});
			}
		});
	});