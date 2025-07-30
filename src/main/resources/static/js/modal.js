$(window).on('load', function(){
    const params = new URLSearchParams(window.location.search);
    const restriction = params.get('restriction');
    if (restriction != null) {
        const modal = new bootstrap.Modal(document.getElementById('restrictionModal'));
        modal.show();
    }
});