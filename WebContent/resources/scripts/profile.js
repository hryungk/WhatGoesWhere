addTableHead();
function addTableHead() {
	let tbody = document.getElementById('tbody');
	if (tbody.firstElementChild != null) {
	    let table = document.getElementById('find-result-table');
        let thead = document.createElement('thead');
        let tr = document.createElement('tr');
        let thArray = ["Name", "Condition", "Best Option", "Special Instruction","Notes","Added On"];
        for (let head of thArray){
            let th = document.createElement('th');
            th.innerHTML = head;
            tr.appendChild(th);
        }
        thead.appendChild(tr);
        table.appendChild(thead);
	}
}
