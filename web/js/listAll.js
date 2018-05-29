/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(
        function () {

            var birdArray = new Array();

            function Bird(collor_ID, student_name, state, site, gender, age, comments, addcomments, rorf, file) {
                this.collor_ID = collor_ID;
                this.student_name = student_name;
                this.state = state;
                this.site = site;
                this.gender = gender;
                this.age=age;
                this.comments = comments;
                this.addcomments = addcomments;
                this.rorf = rorf;
                this.file = file;
            }

            function loadInitData() {
                dataString = null;
                $.ajax({
                    type: "POST",
                    url: "listAllService",
                    dataType: 'json',
                    data: dataString,
                    cache: false,
                    success: function (data, textStatus, xhr) {
                        createDataList(data);

                    },
                    error: function (data, textStatus, errorThrown) {
                        console.log(textStatus)
                    }
                });
            }

            function createDataList(JsonObject) {
                birdArray.length = 0;
                $.each(JsonObject, function (key, object) {
                    var bird = new Bird(object.collor_ID, object.student_name, object.state, object.site, object.gender, object.age, object.comments, object.addcomments, object.rorf, object.file)
                    birdArray.push(bird);
                });

            }

            loadInitData();

            $("#list").click(function (e) {

                $("#Login").hide();
                $("#Home").show();
                $("#Upload").show();
                $("#Search").show();
                $("#list").show();
                $("#Logout").show();
                $("#SearchDiv").hide();
                $("#UploadDiv").hide();
                $("#HomeDiv").hide();
                $("#LoginDiv").hide();
                $("#AllRecordsDiv").show();
                
                $("#userAjaxResponse").html("");
                var listTableBody = $("#userAjaxResponse");
                listTableBody.empty();
                for (i = 0; i < birdArray.length; i++) {
                var bird = birdArray[i];
                var row = $("<tr></tr>");
                var collor_id = $("<td>" + bird.collor_id + "</td>");
                var student_name = $("<td>" + bird.student_name + "</td>");
                var state = $("<td>" + bird.state + "</td>");
                var site = $("<td>" + bird.site + "</td>");
                var gender = $("<td>" + bird.gender + "</td>");
                var age = $("<td>" + bird.age + "</td>");
                var comment = $("<td>" + bird.comments + "</td>");
                var addcomm=$("<td>" + bird.addcomments + "</td>");
                var rorf = $("<td>" + bird.rorf + "</td>");
                var file = $("<td>" + bird.file + "</td>");
                collor_id.appendTo(row);
                student_name.appendTo(row);
                state.appendTo(row);
                site.appendTo(row);
                gender.appendTo(row);
                age.appendTo(row);
                comment.appendTo(row);
                addcomm.append(row);
                rorf.appendTo(row);
                file.appendTo(row);
                row.appendTo(listTableBody);

                }

            });

        });



