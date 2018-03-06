var url = 'http://localhost:8080/bank-item/reactive'; 

var refreshButton = document.getElementById('fetch');

var refreshClickStream = Rx.Observable.fromEvent(refreshButton, 'click');

var eventSourceObserver = function (observer) {
    var eventSource = new EventSource(url);
    eventSource.onmessage = function(event) {
	observer.next(event.data);
    };
    eventSource.onerror = function() {
	console.log("EventSource failed: closing the connection");
	eventSource.close();
	observer.complete();
    };
};

refreshClickStream.flatMap(event => {
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
