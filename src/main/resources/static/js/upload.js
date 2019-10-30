var upload = function () {

    var file = $(":input[type='file']").val();
    if (!/\.(mp4|rmvb|wmv|avi|mov|flv)$/.test(file)) {
        alert("请选择以下后缀名的视频文件 .mp4 .rmvb .wmv .avi .mov .flv");
        return false;
    }

    var formData = new FormData();
    formData.append("file", document.getElementById('file').files[0]);


    $("#submit-button").attr('disabled',true);
    $('#submit-button').attr('value', "已提交");
    $('.upload-status').text('上传中...');

    $.ajax({
        url: "/upload",
        type: "POST",
        data: formData,
        processData: false, // 不要对data参数进行序列化处理，默认为true
        contentType: false, // 不要设置Content-Type请求头，因为文件数据是以 multipart/form-data 来编码
        xhr: function () {
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener('progress', function (e) {
                    if (e.lengthComputable) {
                        var percent = Math.floor(e.loaded / e.total * 100);
                        if (percent <= 100) {
                            $('#progress-bar').attr('value', percent);
                        }
                        if (percent >= 100) {
                            $('.upload-status').text('后台处理中...');
                        }

                    }
                }, false);
            }
            return myXhr;
        },
        success: function (data) {
            $('.upload-status').text(data.msg);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('.upload-status').text('文件上传失败');
            alert(XMLHttpRequest + ' ' + textStatus + ' ' + errorThrown);
        }
    });

}