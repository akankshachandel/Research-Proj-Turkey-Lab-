
var birdArray = new Array();
function Bird(collor_id, student_name, state, site, gender, age, comments, addcomments, rorf, file) {
    this.collor_id = collor_id;
    this.student_name = student_name;
    this.state = state;
    this.site = site;
    this.gender = gender;
    this.comments = comments;
    this.age=age;
    this.addcomments = addcomments;
    this.rorf = rorf;
    this.file = file;
}


function loadSearchData() {
    var name = $("input#name").val();
    dataStringSearch = "name=" + name;
    $.ajax({
        type: "POST",
        url: "SearchService",
        dataType: 'json',
        data: dataStringSearch,
        cache: false,
        success: function (data, textStatus, xhr) {
            createBirdDataList(data);
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
                var age=$("<td>" + bird.age + "</td>");
                var comments = $("<td>" + bird.comments + "</td>");
                var addcomm=$("<td>" + bird.addcomments + "</td>");
                var rorf = $("<td>" + bird.rorf + "</td>");
                var file = $("<td>" + bird.file + "</td>");
                collor_id.appendTo(row);
                student_name.appendTo(row);
                state.appendTo(row);
                site.appendTo(row);
                gender.appendTo(row);
                age.appendTo(row);
                comments.appendTo(row);
                addcomm.append(row);
                rorf.appendTo(row);
                file.appendTo(row);
                row.appendTo(listTableBody);
            }
        },
        error: function (data, textStatus, errorThrown) {
            console.log(textStatus)
        }
    });
}


function createBirdDataList(JsonObject) {
    birdArray.length = 0;
    $.each(JsonObject, function (key, object) {
        var bird = new Bird(object.collor_id, object.student_name, object.state, object.site, object.gender, object.age, object.comment, object.rorf, object.file);
        birdArray.push(bird);
    });
}


$("#Search").click(function (e) {

    $("#Login").hide();
    $("#Home").show();
    $("#Upload").show();
    $("#Search").show();
    $("#Logout").show();
    $("#list").show();

    $("#LoginDiv").hide();
    $("#HomeDiv").hide();
    $("#UploadDiv").hide();
   $("#SearchDiv").show();
    $("#AllRecordsDiv").hide();
});
$("#SearchBtn").click(function (e) {
    loadSearchData();
    $("#Login").hide();
    $("#Home").show();
    $("#Upload").show();
    $("#Search").show();
    $("#Logout").show();
    $("#list").show();

    $("#LoginDiv").hide();
    $("#HomeDiv").hide();
    $("#UploadDiv").hide();
    $("#SearchDiv").show();
    $("#AllRecordsDiv").hide();
    $("#ajaxResponse").html("");
});


