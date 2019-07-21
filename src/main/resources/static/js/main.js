function doAjax(url, method, data, successFx, errorFx, extendOpts) {
    let ajaxOptions = {
        url: url,
        type: method,
        contentType: "application/json",
        success: function (responseData) {
            if (successFx) {
                successFx.call(successFx, responseData);
            }
        },
        error: function (errData) {
            try {
                errText = JSON.parse(errData.responseText);
                console.error(errText);
                showError(errText.message);
                if (errorFx) {
                    errorFx.call(errorFx, errData);
                }
            } catch (error) {
                let msg = "";
                switch (errData.status) {
                    case 404:
                        msg = "Not Found";
                        break;
                    case 401:
                        msg = "Authentication Error";
                        break;
                    case 403:
                        msg = "Access Denied";
                        break;
                    default:
                        msg = "Unknown Error";
                }
                showError(msg);
                return msg;
            }
        }
    };
    if (data) {
        ajaxOptions['data'] = JSON.stringify(data);
    }
    let updatedOpts = {
        ...{},
        ...ajaxOptions,
        ...extendOpts
    }
    $.ajax(updatedOpts);
}
