var refreshButton = document.querySelector('.refresh');


var refreshClickStream = Rx.Observable.fromEvent(refreshButton, 'click');

refreshClickStream.subscribe(event => {
	var responseStream = Rx.Observable.fromPromise($.getJSON('https://api.github.com/users?since=124'));
	responseStream.subscribe(jsonData => {responseSubscriber(jsonData);});
});

var responseSubscriber = function(jsonData) {
	var responseArrayStream = Rx.Observable.fromArray(jsonData);
	responseArrayStream.subscribe(row => {responseArraySubscriber(row);});
};

var responseArraySubscriber = function(row) {
	console.log(row.login);
};


