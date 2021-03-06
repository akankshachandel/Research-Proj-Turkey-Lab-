$(document).ready(
        function () {

            var birdArray = new Array();
            function Bird(collor_ID, student_name, state, site, gender, age, comments, addcomm, rorf, file) {
                this.collor_ID = collor_ID;
                this.student_name = student_name;
                this.state = state;
                this.site = site;
                this.gender = gender;
                this.age = age;
                this.comments = comments;
                this.addcomm = addcomm;
                this.rorf = rorf;
                this.file = file;
            }

            function loadInitData() {
                dataString = null;
                $.ajax({
                    type: "POST",
                    url: "ListAllService",
                    dataType: 'json',
                    data: dataString,
                    cache: false,
                    success: function (data, textStatus, xhr) {
                        console.log(data);
                        createDataList(data);


                    },
                    error: function (data, textStatus, errorThrown) {
                        console.log(textStatus);
                    }
                });
            }

            function createDataList(JsonObject) {
                birdArray.length = 0;
                $.each(JsonObject, function (key, object) {
                    var bird = new Bird(object.collor_ID, object.student_name, object.state, object.site, object.gender, object.age, object.comments, object.addcomm, object.rorf, object.file);
                    birdArray.push(bird);
                });

            }

            loadInitData();

            $("#List").click(function (e) {

                $("#Login").hide();
                $("#Home").show();
                $("#Upload").show();
                $("#Search").show();
                $("#List").show();
                $("#Logout").show();

                $("#LoginDiv").hide();
                $("#HomeDiv").hide();
                $("#UploadDiv").hide();
                $("#SearchDiv").hide();
                $("#AllRecordsDiv").show();

                $("#userAjaxResponse").html("");
                var listTableBody = $("#userAjaxResponse");
                listTableBody.empty();
                for (i = 0; i < birdArray.length; i++) {
                    bird = birdArray[i];
                    var row = $("<tr></tr>");
                    var collor_ID = $("<td>" + bird.collor_ID + "</td>");
                    var student_name = $("<td>" + bird.student_name + "</td>");
                    var state = $("<td>" + bird.state + "</td>");
                    var site = $("<td>" + bird.site + "</td>");
                    var gender = $("<td>" + bird.gender + "</td>");
                    var age = $("<td>" + bird.age + "</td>");
                    var comments = $("<td>" + bird.comments + "</td>");
                    var addcomm = $("<td>" + bird.addcomm + "</td>");
                    //var rorf = $("<td>" + bird.rorf + "</td>");
                    var temp = bird.file;

                    var file = "";
                    if (temp !== "undefined") {
                        var finalized = "";
                        var raw = "";
                        var gdf = "";
                        temp = temp.replace("\\", "");
                        var x = temp.length;

                        if (temp.indexOf("*#gdf#*") !== -1)
                        {
                            gdf = temp.substring(temp.indexOf("*#gdf#*") + 7);
                            x = temp.indexOf("*#gdf#*");
                        }

                        if (temp.indexOf("*#finalized:*#") !== -1)
                        {
                            if (temp.indexOf("*#raw#*") !== -1)
                                finalized = temp.substring(temp.indexOf("*#finalized:*#") + 14, temp.indexOf("*#raw#*"));
                            else if (temp.indexOf("*#gdf#*") !== -1)
                                finalized = temp.substring(temp.indexOf("*#finalized:*#") + 14, temp.indexOf("*#gdf#*"));
                            else
                                finalized = temp.substring(temp.indexOf("*#finalized:*#") + 14);
                        }

                        if (temp.indexOf("*#raw#*") !== -1)
                        {
                            raw = temp.substring(temp.indexOf("*#raw#*") + 7, x);
                        }


                        var p = "", q = "", r = "";

                        if (finalized !== "")
                            p = "<a href =\"Saved/" + finalized + "\" download>" + "Finalized File" + "</a><br>";

                        if (raw !== "")
                            q = "<a href =\"Saved/" + raw + "\" download>" + "Raw File" + "</a><br>";

                        if (gdf !== "")
                            r = "<a href =\"Saved/" + gdf + "\" download>" + "GDF File" + "</a>";


                        file = $("<td>" + p + q + r + "</td>");
                    } else {
                        console.log("error in path");
                    }

                    collor_ID.appendTo(row);
                    student_name.appendTo(row);
                    state.appendTo(row);
                    site.appendTo(row);
                    gender.appendTo(row);
                    age.appendTo(row);
                    comments.appendTo(row);
                    addcomm.appendTo(row);
                    file.appendTo(row);
                    row.appendTo(listTableBody);

                }

            });

        });
