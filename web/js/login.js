
$(document).ready(
        function () {

            $("#Logout").hide();
            $("#Upload").hide();
            $("#Search").hide();
            $("#list").hide();


            function login() {
                //dataStringSearch = $("#LoginForm").serialize();
                var userID = $("input#LoginuserID").val();
                var password = $("input#Loginpassword").val();
                dataString = {
                    userID: userID,
                    password: password
                }

                $.ajax({
                    type: "POST",
                    url: "LoginService",
                    dataType: 'json',
                    data: dataString,
                    cache: false,
                    success: function (data, textStatus, xhr) {
//						createLoginDataList(data);
                        console.log(data.msg);

                        if (data.msg === "success") {
                            $("#Login").hide();
                            $("#Home").show();
                            $("#Upload").show();
                            $("#Search").show();
                            $("#list").show();
                            $("#Logout").show();

                            $("#SearchDiv").show();
                            $("#UploadDiv").hide();
                            $("#HomeDiv").hide();
                            $("#AllRecordsDiv").hide();
                            $("#LoginDiv").hide();


                        } else {
                            alert("failed");
                            $("#Login").show();
                            $("#Home").show();
                            $("#Upload").hide();
                            $("#list").hide();
                            $("#Search").hide();
                            $("#Logout").hide();

                            $("#HomeDiv").hide();
                            $("#LoginDiv").show();
                            $("#SearchDiv").hide();
                            $("#UploadDiv").hide();
                             $("#AllRecordsDiv").hide();

                        }

                    },
                    error: function (data, textStatus, errorThrown) {
                        console.log(data)
                    }
                });

            }

            function logout() {
                alert("logging Out!");
                $("#Login").show();
                $("#Home").show();
                $("#Upload").hide();
                $("#Search").hide();
                $("#Logout").hide();
                $("#list").hide();

                $("#LoginDiv").hide();
                $("#HomeDiv").show();
                $("#UploadDiv").hide();
                $("#SearchDiv").hide();
                $("#AllRecordsDiv").hide();
            }




            $("#Login").click(function (e) {

                $("#LoginDiv").show();
                $("#HomeDiv").hide();
                $("#UploadDiv").hide();
                $("#SearchDiv").hide();
                $("#AllRecordsDiv").hide();

            });

            $("#LoginCancelBtn").click(function (e) {

                $("#LoginDiv").hide();
                $("#HomeDiv").show();
                $("#UploadDiv").hide();
                $("#SearchDiv").hide();
                $("#AllRecordsDiv").hide();
            });

            $("#LoginBtn").click(function (e) {
                login();
            });

            $("#Logout").click(function () {
                logout();
            });
        });
