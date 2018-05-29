$(document).ready(
         
           
		function() {
			$("#myAjaxRequestForm").submit(function(e) {
				e.preventDefault();
			});
                        
                        function uploadDiv() {
                            $("#Login").hide();
                            $("#Home").show();
                            $("#Upload").show();
                            $("#Search").show();
                            $("#Logout").show();

                            $("#LoginDiv").hide();
                            $("#HomeDiv").hide();
                            $("#UploadDiv").show();
                            $("#SearchDiv").hide();
                            $("#AllRecordsDiv").hide();
                        }


                        
                         $("#Upload").click(function (e) {
                            uploadDiv();
                        });

//			var birdArray = new Array();
//                        function Bird(collor_id, student_name, state, site, gender, comment, rorf, file){
//                                this.collor_id = collor_id;
//                                this.student_name = student_name;
//                                this.state = state;
//                                this.site = site;
//                                this.gender = gender;
//                                this.comment = comment;
//                                this.rorf = rorf;
//                                this.file = file;
//                        }
			
			function uploadData() {
				dataString = $("#UploadRequestForm").serialize();

				var collor_id = $("input#upload_collor_id").val();
				var student_name = $("input#upload_student_name").val();
				var state = $("select#upload_state").val();
				var site = $("select#upload_site").val();
				var gender = $("select#upload_gender").val();
                                var age = $("select#upload_age").val();
				var comments = $("select#upload_comments").val();
                                var other_comments = $("input#upload_other_comments").val();
                                var rorf = $("select#upload_rorf").val();
				var file = $("input#upload_file").val();

				dataString = {
					collor_id : collor_id,
					student_name : student_name,
					state : state,
					site : site,
					gender : gender,
                                        age: age,
					comments : comments,
                                        other_comments : other_comments,
                                        rorf : rorf,
					file : file
				};
                            console.log("request sent");
				$.ajax({
					type : "POST",
					url : "UploadService",
					dataType : 'json',
					data : dataString,
					cache : false,
					success : function(data, textStatus, xhr) {
						console.log(data);
						alert("Insertion complete.");
					},
					error : function(data, textStatus, errorThrown) {
						console.log(textStatus)
					}
				});
			}

			
			$("#uploadBtn").click(function(e) {
				uploadData();

			});

		});
