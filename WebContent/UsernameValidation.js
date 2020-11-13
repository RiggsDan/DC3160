var request;

function validateUsername(newUsername) {
	if (newUsername.length < 7) {
		document.getElementById("addUserSubmitButton").disabled = true;
		document.getElementById("addUserWarning").innerHTML = "The password is too short";
		return;
	}

	$.ajax({
		type: "POST",
		url: "do/checkName",
		data: { "newUsername": newUsername },
		dataType: "text",
		success: function(rawResponse) {
			 var response = JSON.parse(rawResponse);
			 
			 if (!response.isUsernameValid) {
			 	$("#addUserWarning").text("That username is taken");
			 }
			 
			 $("#addUserSubmitButton").attr("disabled", !response.isUsernameValid);
		}
	});
	
	// Clear the warning
	document.getElementById("addUserWarning").innerHTML = "";
}