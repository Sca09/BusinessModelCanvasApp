var google = google || {};

google.endpoints = google.endpoints || {};

google.endpoints.bmcaApi = google.endpoints.bmcaApi || {};

google.endpoints.bmcaApi.init = function(apiRoot) {
	var callback = function() {
//		alert("bmca API uploaded");
		
		google.endpoints.bmcaApi.list();
	}

	gapi.client.load('bmca', 'v1', callback, apiRoot);
};

google.endpoints.bmcaApi.list = function() {
	gapi.client.bmca.item.list().execute(function(resp) {
		if (resp.items) {
			for (var i = 0; i < resp.items.length; i++) {
				var canvasItem = document.createElement('div');
				canvasItem.id = resp.items[i].id;
				canvasItem.className = "canvasItem";
				
				canvasItem.innerHTML = "<div class=\"title\">"+ resp.items[i].title +"</div><hr><div class=\"description\">"+ resp.items[i].description +"</div><hr><div class=\"author\">"+ resp.items[i].author +"</div>";
				
				document.getElementById(resp.items[i].category).appendChild(canvasItem);
			}
		}
	});
};

google.endpoints.bmcaApi.added = function(message) {
	var id = message.id;

	gapi.client.bmca.item.get({'id' : id[0]}).execute(function(resp) {
		if (resp) {
			
			var canvasItem = document.createElement('div');
			canvasItem.id = resp.id;
			canvasItem.className = "canvasItem";
			
			canvasItem.innerHTML = "<div class=\"title\">"+ resp.title +"</div><hr><div class=\"description\">"+ resp.description +"</div><hr><div class=\"author\">"+ resp.author +"</div>";
			
			document.getElementById(resp.category).appendChild(canvasItem);
			
		}
	});
};

google.endpoints.bmcaApi.updated = function(message) {
	var id = message.id;

	gapi.client.bmca.item.get({'id' : id[0]}).execute(function(resp) {
		if (resp) {
			document.getElementById(resp.id).remove();
			
			var canvasItem = document.createElement('div');
			canvasItem.id = resp.id;
			canvasItem.className = "canvasItem";
			
			canvasItem.innerHTML = "<div class=\"title\">"+ resp.title +"</div><hr><div class=\"description\">"+ resp.description +"</div><hr><div class=\"author\">"+ resp.author +"</div>";
			
			document.getElementById(resp.category).appendChild(canvasItem);
		}
	});
};

google.endpoints.bmcaApi.deleted = function(message) {
	document.getElementById(message.id).remove();
}

// Channel API functions - Log Tool
onOpened = function() {
	console.log("channel connected");
//	google.endpoints.bmcaApi.list();
};

onError = function() {
	console.log("channel error");
};

onClose = function() {
	console.log("channel closed");
};

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
};