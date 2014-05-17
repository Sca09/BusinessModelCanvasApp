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
				var canvasItem = $('<div id=\"'+ resp.items[i].id +'\"class=\"canvasItem\"><div class=\"title\">'+ resp.items[i].title +'</div><hr><div class=\"description\">'+ resp.items[i].description +'</div><hr><div class=\"author\">'+ resp.items[i].author +'</div></div>');
				var content = $('[id="'+ resp.items[i].category+ '"]').children('div[data-role="content"]');
				
				canvasItem.appendTo(content);
			}
		}
	});
};

var index = 0;

var activities = [
                  "Key Partners",
                  "Key Activities",
                  "Key Resources",
                  "Value Propositions",
                  "Customer Relationships",
                  "Channels",
                  "Customer Segments",
                  "Cost Structure",
                  "Revenue Streams"
                  ];

changePage = function() {
	$('div[data-role="page"]').swiperight(function() {
		if(index > 0) {
			index--;
			$.mobile.changePage("#"+ activities[index], { transition: "slide", reverse: true, changeHash:false});
		}
	});

	$('div[data-role="page"]').swipeleft(function() {
		if(index < (activities.length - 1)) {
			index++;
			$.mobile.changePage("#"+ activities[index], {transition: "slide", changeHash:false});
		}
	});
}