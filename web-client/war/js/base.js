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
				addItem(resp.items[i]);
			}
		}
	});
};

google.endpoints.bmcaApi.add = function() {
	var addCategory = $('[id="addCategory"] :selected').val();
	var addTitle = $('[id="addTitle"]').val();
	var addDescription = $('[id="addDescription"]').val();
	var addAuthor = $('[id="addAuthor"]').val();
	
	gapi.client.bmca.item.add({'category': addCategory, 'title' : addTitle, 'description': addDescription, 'author' : addAuthor}).execute(function(resp) {
		if (resp) {
			addItem(resp);
			
			$('[id="addCategory"] option[value=\"'+ activities[0] +'\"]').attr('selected', 'selected');
			$('[id="addTitle"]').val("");
			$('[id="addDescription"]').val("");
			$('[id="addAuthor"]').val("");
			
			goToPage(resp.category);
		}
	});
};

google.endpoints.bmcaApi.update = function() {
	var updateId = $('[id="updateId"]').val();
	var updateCategory = $('[id="updateCategory"] :selected').val();
	var updateTitle = $('[id="updateTitle"]').val();
	var updateDescription = $('[id="updateDescription"]').val();
	var updateAuthor = $('[id="updateAuthor"]').val();
	
	gapi.client.bmca.item.update({'id': updateId, 'category': updateCategory, 'title' : updateTitle, 'description': updateDescription, 'author' : updateAuthor}).execute(function(resp) {
		if (resp) {
			$('[id=\"'+ resp.id +'\"]').remove();
			
			addItem(resp);
			
			goToPage(resp.category);
		}
	});
};

google.endpoints.bmcaApi.remove = function() {
	var id = $('[id="updateId"]').val();
	
	gapi.client.bmca.item.delete({'id': id}).execute(function(resp) {
		$('[id=\"'+ id +'\"]').remove();
		
		backPage();
	});
};

addItem = function(item){
	var canvasItem = $('<div id=\"'+ item.id +'\" class=\"canvasItem\" data-category=\"'+ item.category +'\"><div class=\"title\">'+ item.title +'</div><hr><div class=\"description\">'+ item.description +'</div><hr><div class=\"author\">'+ item.author +'</div></div>');
	canvasItem.click(function(){
		$(this).on('tap', showEditItemPage($(this)[0].id));
	});
	
	var content = $('[id="'+ item.category+ '"]').children('div[data-role="content"]');
	canvasItem.appendTo(content);
};

showEditItemPage = function (id){
	var category = $('[id=\"'+ id +'\"]').data("category");
	var title = $('[id=\"'+ id +'\"]').find('.title')[0].textContent;
	var description = $('[id=\"'+ id +'\"]').find('.description')[0].textContent;
	var author = $('[id=\"'+ id +'\"]').find('.author')[0].textContent;

	$('[id="updateCategory"] option[value=\"'+ category +'\"]').attr('selected', 'selected');
	
	$('[id="selectCategory"]').selectmenu();
	$('[id="selectCategory"]').selectmenu("refresh", true);
	
	$('[id="updateId"]').val(id);
	$('[id="updateCategory"]').val(category);
	$('[id="updateTitle"]').val(title);
	$('[id="updateDescription"]').val(description);
	$('[id="updateAuthor"]').val(author);
	
	$.mobile.changePage('#Update Item', {transition: "slidedown", changeHash:false});
};

showNewItemPage = function (){
	$('[id="addCategory"] option[value=\"'+ activities[index] +'\"]').attr('selected', 'selected');
	
	$.mobile.changePage('#New Item', {transition: "slidedown", changeHash:false});
};

refreshItems = function() {
	$('.canvasItem').remove();

	google.endpoints.bmcaApi.list();	
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

registerListener = function() {
	$('div[data-role="page"]').swiperight(function() {
		if(index > 0) {
			index--;
			$.mobile.changePage("#"+ activities[index], {transition: "slide", reverse: true, changeHash:false});
		}
	});

	$('div[data-role="page"]').swipeleft(function() {
		if(index < (activities.length - 1)) {
			index++;
			$.mobile.changePage("#"+ activities[index], {transition: "slide", changeHash:false});
		}
	});
};

goToPage = function(category) {
	var categoryIndex = activities.indexOf(category);
	index = categoryIndex;
	$.mobile.changePage("#"+ activities[index], {transition: "slideup", changeHash:false});
};

backPage = function() {
	$.mobile.changePage("#"+ activities[index], {transition: "slideup", changeHash:false});
};