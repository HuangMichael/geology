<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/14
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- release v4.4.2, copyright 2014 - 2017 Kartik Visweswaran -->
<!--suppress JSUnresolvedLibraryURL -->
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Krajee JQuery Plugins - &copy; Kartik</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/js/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <link href="/js/bootstrap-fileinput/themes/explorer/theme.css" media="all" rel="stylesheet" type="text/css" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="/js/bootstrap-fileinput/js/plugins/sortable.js" type="text/javascript"></script>
    <script src="/js/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
    <script src="/js/bootstrap-fileinput/js/locales/fr.js" type="text/javascript"></script>
    <script src="/js/bootstrap-fileinput/js/locales/es.js" type="text/javascript"></script>
    <script src="/js/bootstrap-fileinput/themes/explorer/theme.js" type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
</head>

<body>
<div class="container kv-main">
    <div class="page-header">
        <h1>Bootstrap File Input Example</h1>
    </div>
    <form enctype="multipart/form-data">
        <input id="kv-explorer" type="file"/>
        <button type="submit" class="btn btn-primary">Submit</button>
        <button type="reset" class="btn btn-default">Reset</button>
    </form>

</body>
<script>
    //  $('#file-fr').fileinput({
    //      language: 'fr',
    //      uploadUrl: '#',
    //      allowedFileExtensions: ['jpg', 'png', 'gif']
    //  });
    //  $('#file-es').fileinput({
    //      language: 'es',
    //      uploadUrl: '#',
    //      allowedFileExtensions: ['jpg', 'png', 'gif']
    //  });
    //  $("#file-0").fileinput({
    //      'allowedFileExtensions': ['jpg', 'png', 'gif']
    //  });
    //  $("#file-1").fileinput({
    //      uploadUrl: '#', // you must set a valid URL here else you will get an error
    //      allowedFileExtensions: ['jpg', 'png', 'gif'],
    //      overwriteInitial: false,
    //      maxFileSize: 1000,
    //      maxFilesNum: 10,
    //      //allowedFileTypes: ['image', 'video', 'flash'],
    //      slugCallback: function (filename) {
    //          return filename.replace('(', '_').replace(']', '_');
    //      }
    //  });
    //  /*
    //   $(".file").on('fileselect', function(event, n, l) {
    //   alert('File Selected. Name: ' + l + ', Num: ' + n);
    //   });
    //   */
    //  $("#file-3").fileinput({
    //      showUpload: false,
    //      showCaption: false,
    //      browseClass: "btn btn-primary btn-lg",
    //      fileType: "any",
    //      previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
    //      overwriteInitial: false,
    //      initialPreviewAsData: true,
    //      initialPreview: [
    //          "http://lorempixel.com/1920/1080/transport/1",
    //          "http://lorempixel.com/1920/1080/transport/2",
    //          "http://lorempixel.com/1920/1080/transport/3"
    //      ],
    //      initialPreviewConfig: [
    //          {caption: "transport-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
    //          {caption: "transport-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
    //          {caption: "transport-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3}
    //      ]
    //  });
    //  $("#file-4").fileinput({
    //      uploadExtraData: {kvId: '10'}
    //  });
    //  $(".btn-warning").on('click', function () {
    //      var $el = $("#file-4");
    //      if ($el.attr('disabled')) {
    //          $el.fileinput('enable');
    //      } else {
    //          $el.fileinput('disable');
    //      }
    //  });
    //  $(".btn-info").on('click', function () {
    //      $("#file-4").fileinput('refresh', {previewClass: 'bg-info'});
    //  });
    /*
     $('#file-4').on('fileselectnone', function() {
     alert('Huh! You selected no files.');
     });
     $('#file-4').on('filebrowse', function() {
     alert('File browse clicked for #file-4');
     });
     */
    $(document).ready(function() {
        $("#kv-explorer").fileinput({
            'theme': 'explorer',
            'uploadUrl': '/history/upload',

            language: 'zn',
                  allowedFileExtensions: ['jpg', 'png', 'gif']
        });
    });
</script>
</html>
