var google = google || {};

google.endpoints = google.endpoints || {};

google.endpoints.bmcaApi = google.endpoints.bmcaApi || {};

google.endpoints.bmcaApi.init = function(apiRoot) {
	var callback = function() {
		alert("bmca API uploaded");
	}

	gapi.client.load('bmca', 'v1', callback, apiRoot);
};

google.endpoints.bmcaApi.added = function(message) {
	var id = message.id;
	var action = message.action;

	gapi.client.bmca.item.get({'id' : id[0]}).execute(function(resp) {
		if (resp) {
			google.endpoints.bmcaApi.log(action, resp, message.user_agent);
		}
	});
}

google.endpoints.bmcaApi.updated = function(message) {
	var id = message.id;
	var action = message.action;

	gapi.client.bmca.item.get({'id' : id[0]}).execute(function(resp) {
		if (resp) {
			google.endpoints.bmcaApi.log(action, resp, message.user_agent);
		}
	});
}

google.endpoints.bmcaApi.deleted = function(message) {
	google.endpoints.bmcaApi.log(message.action, message, message.user_agent);
}

google.endpoints.bmcaApi.log = function(action, resp, user_agent) {
	var canvasItem = document.createElement('tr');
	canvasItem.innerHTML = "<td>" + action + "</td><td>" + user_agent+ "</td><td>" + resp.id + "</td><td>" + resp.category + "</td><td>"+ resp.title + "</td><td>" + resp.author + "</td>";
	document.getElementById("log_table").appendChild(canvasItem);
}

// Channel API functions
onOpened = function() {
	console.log("channel connected");
};

onError = function() {
	console.log("channel error");
};

onClose = function() {
	console.log("channel closed");
}

onMessage = function(m) {
	message = JSON.parse(m.data);
	action = message.action;

	if (action == "Added") {
		google.endpoints.bmcaApi.added(message);
	} else if (action == "Updated") {
		google.endpoints.bmcaApi.updated(message);
	} else if (action == "Deleted") {
		google.endpoints.bmcaApi.deleted(message);
	}
}