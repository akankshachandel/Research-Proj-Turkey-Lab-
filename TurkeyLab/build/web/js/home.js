/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
	
	$("#Home").click(function(e) {
		$("#Login").show();
                $("#Home").show();
                $("#Upload").hide();
                $("#Search").hide();
                $("#Logout").hide();
                $("#List").hide();
                $("#LoginDiv").hide();
                $("#HomeDiv").show();
                $("#UploadDiv").hide();
                $("#SearchDiv").hide();
                $("#AllRecordsDiv").hide();
	});
	

});
