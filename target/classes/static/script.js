document.addEventListener("DOMContentLoaded", function () {
    hideModalLoading()
});

function showModalLoading() {
    document.getElementById("modal-loading").style.visibility = "visible";
}

function hideModalLoading() {
    document.getElementById("modal-loading").style.visibility = "hidden";
}