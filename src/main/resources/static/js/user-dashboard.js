
$(document).ready(function () {
    let chamda = window.location.origin + "/";

    $("#devices").on("click", function(){
        let $this = $(this);
        let url = chamda + "api/user/user-details",
            method = "GET",
            successFx = function(result){
                console.log(result);
            };
        doAjax(url,method,undefined,successFx);
        return false;
    });

});