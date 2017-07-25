$(document).ready(function () {
    $('#twitter-form').submit(function (event) {
        event.preventDefault();

        twitter_ajax_request(this);
    })
})

function twitter_ajax_request(form) {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: $(form).attr('action'),
        data: $(form).serialize(),
        success: function (data) {
            console.log('success');
            console.log(data);
        },
        error: function (e) {
            console.log('error');
            console.log(e)

        }
    });

}