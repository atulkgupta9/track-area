
$(document).ready(function () {
    init();
    let chamda = window.location.origin + "/";
    function init() {
        $("#signin-button").css("border-bottom", "3px solid black");
        $("#signin-form").css("display", "block");
    }

    $("#signin-button").on("click", function() {
        $(this).css("border-bottom", "3px solid black");
        $("#signup-button").css("border-bottom", "3px solid white");

        $("#signup-form").css("display", "none")
        $("#signin-form").slideDown(400, function() {
            $(this).css("display", "block");
        });
    });

    $("#signup-button").on("click", function() {
        $(this).css("border-bottom", "3px solid black");
        $("#signin-button").css("border-bottom", "3px solid white");

        $("#signin-form").css("display", "none");
        $("#signup-form").slideDown(400, function() {
            $(this).css("display", "block");
        });

    });

    $("#admin-signin-form").on("submit", function(){
        let $this = $(this);
        let url = chamda + "api/auth/signin",
            method = "POST",
            body = serializeForm($this),
            successFx = function(token){
                console.log(token);
                localStorage.setItem("token", token);
                window.location.href = chamda + "ui/dashboard";
            };
        doAjax(url,method,body,successFx);
        return false;
    });

    $("#user-signin-form").on("submit", function(){
        let $this = $(this);
        let url = chamda + "api/auth/signin",
            method = "POST",
            body = serializeForm($this),
            successFx = function(token){
                console.log(token);
                localStorage.setItem("token", token);
                window.location.href = chamda + "ui/dashboard";
            };
        doAjax(url,method,body,successFx);
        return false;
    });

    $("#signup-form").on("submit", function(){
        let $this = $(this);
        let url = chamda + "api/auth/signup",
            method = "POST",
            body = serializeForm($this),
            successFx = function(data){
                alert("Successfully Registered !!");
            };
        doAjax(url,method,body,successFx);
        return false;
    });
});