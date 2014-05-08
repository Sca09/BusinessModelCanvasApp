var google = google || {};

google.endpoints = google.endpoints || {};	

google.endpoints.bmcaApi = google.endpoints.bmcaApi || {};

google.endpoints.bmcaApi.init = function(apiRoot) {
	var callback = function() {
		alert("bmca API uploaded");
	}
	
	gapi.client.load('bmca', 'v1', callback, apiRoot);
};


google.endpoints.bmcaApi.added = function(id) {
	console.log(id[0]);
	gapi.client.bmca.item.get({'id': id[0]}).execute(function(resp) {
		if (resp) {
			document.getElementById("action").innerHTML = "add";
			document.getElementById("id").innerHTML = resp.id;
			document.getElementById("title").innerHTML = resp.title;
			document.getElementById("description").innerHTML = resp.description;
			document.getElementById("category").innerHTML = resp.category;
			document.getElementById("author").innerHTML = resp.author;
		}
	
	});
}

google.endpoints.bmcaApi.updated = function(id) {
	console.log(id[0]);
	gapi.client.bmca.item.get({'id': id[0]}).execute(function(resp) {
		if (resp) {
			document.getElementById("action").innerHTML = "update";
			document.getElementById("id").innerHTML = resp.id;
			document.getElementById("title").innerHTML = resp.title;
			document.getElementById("description").innerHTML = resp.description;
			document.getElementById("category").innerHTML = resp.category;
			document.getElementById("author").innerHTML = resp.author;
		}
	
	});
}

google.endpoints.bmcaApi.deleted = function(id) {
	console.log(id[0]);
	
	document.getElementById("action").innerHTML = "deleted";
	document.getElementById("id").innerHTML = id;
	document.getElementById("title").innerHTML = "";
	document.getElementById("description").innerHTML = "";
	document.getElementById("category").innerHTML = "";
	document.getElementById("author").innerHTML = "";
}

// Channel API functions
onOpened = function() {
	console.log("channel connected");
	var output = 'opened';
	document.getElementById("action").innerHTML = output;
};

onError = function() {
	var output = 'error';
	document.getElementById("action").innerHTML = output;
};

onClose = function() {
	var output = 'closed';
	document.getElementById("action").innerHTML = output;
}

onMessage = function(m) {
	message = JSON.parse(m.data);
	action = message.action;
	id = message.id;
	
	if(action == "add") {
		google.endpoints.bmcaApi.added(id);
	} else if(action == "update") {
		google.endpoints.bmcaApi.updated(id);
	} else if(action == "delete") {
		google.endpoints.bmcaApi.deleted(id);
	}
}