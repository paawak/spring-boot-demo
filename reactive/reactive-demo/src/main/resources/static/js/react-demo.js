var url = 'http://localhost:8080/bank-item/reactive'; 

var fetchButton = document.getElementById('fetch');
var fetchClickStream = Rx.Observable.fromEvent(fetchButton, 'click');

var stopButton = document.getElementById('stop');
var stopClickStream = Rx.Observable.fromEvent(stopButton, 'click');

var eventSource = null;

stopClickStream.subscribe(event => {
    if (eventSource != null) {
	console.log("closing the EventSource connection");
	eventSource.close();
    }
});


var eventSourceObserver = function (observer) {
    eventSource = new EventSource(url);
    eventSource.onmessage = function(event) {
	observer.next(event.data);
    };
    eventSource.onerror = function() {
	console.log("EventSource failed: closing the connection");
	eventSource.close();
	observer.complete();
	eventSource = null;
    };
};

fetchClickStream.flatMap(event => {
    console.log("refresh clicked.");
    return Rx.Observable.create(eventSourceObserver);
}).map(dataAsText => {
    return JSON.parse(dataAsText);
}).subscribe(jsonData => {
    addRow(jsonData);
});


function addRow(newValueAsJson) {
    var tableDiv = document.getElementById("tableDiv");
    var newDivRow = '<div class="div-table-row">' 
	+ '<div class="div-table-col">' + newValueAsJson.id + '</div>'
	+ '<div class="div-table-col">' + newValueAsJson.age + '</div>'
	+ '<div class="div-table-col">' + newValueAsJson.job + '</div>'
	+ '</div>'; 
    tableDiv.innerHTML += newDivRow;
}
