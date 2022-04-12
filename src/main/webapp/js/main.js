function showModal(id) {
	var modalDelete = document.getElementById(id);
	if (modalDelete) {
		var modal = new bootstrap.Modal(modalDelete, {});
		modal.show();
	}
}
function askForDelete(num) {
	var numero = document.getElementById("numero");
	if (numero) {
		numero.value = num;
		showModal("modalDelete");
	}
}
function deleteAll() {
	showModal("modalDeleteAll");	
}