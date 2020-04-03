//функция корректирует расположение элементов при загрузке страницы
function bodyLoad(){
    $('#mainDiv').css({"margin-left":($(window).width()-$('#mainDiv').width())/2});
    $('#mainDiv').css({"margin-top":($(window).height()-$('#mainDiv').height() - $('#MessageDiv').height())/2});
    $('input[type="button"]').css({"margin-left":($('input[type="button"]').parent().width()-$('input[type="button"]').width())/2});
    $('.TableDiv').outerHeight($('.ResultsDiv').outerHeight() - $('.ResultsDiv').find('label').outerHeight() - $('.FooterDiv').outerHeight()-15);
    $('#loadWaitDiv').css({"margin-left":($(window).width()-$('#loadWaitDiv').width())/2});
    $('#loadWaitDiv').css({"margin-top":($(window).height()-$('#loadWaitDiv').height())/2});
    $('.Spinner').css({"margin-top":($('#loadWaitDiv').height()-$('.Spinner').height())/2});
    $('#loadWaitDiv').hide();
    $('.InputClass').focus();
}
//функция показывает "WaitingBox"
function startWaitAjax(text){
    $('#loadWaitDiv').show();
    $('#waitLabel').text(text);
}
//функция скрывает "WaitingBox"
function stopWaitAjax(){
    $('#loadWaitDiv').hide();
}
//функция показывает сообщения
var messageTimeout;
function showMessage(msgType, message){
    var className = '';
    $('#MessageDiv').removeClass('positiveMessage');
    $('#MessageDiv').removeClass('warningMessage');
    $('#MessageDiv').removeClass('errorMessage');
    if(msgType=='positive'){
        className = 'positiveMessage';
        $('#MessageDiv').addClass('positiveMessage');
    }
    if(msgType=='warning'){
        className = 'warningMessage';
        $('#MessageDiv').addClass('warningMessage');
    }
    if(msgType=='error'){
        className = 'errorMessage';
        $('#MessageDiv').addClass('errorMessage');
    }
    $('#MessageDiv div').text(message);
    $('#MessageDiv').addClass('activeMessage');
    clearTimeout(messageTimeout);
    var msgTime = 2500;
    if(message.length>40){
        msgTime = 62*message.length
    }
    messageTimeout = setTimeout(function(){
        $('#MessageDiv').removeClass('activeMessage');
        setTimeout(function(){
            $('#MessageDiv').removeClass(className);
            $('#MessageDiv').find('div').empty();
        },500);
    },msgTime);
}
//функция подставляет выбранную ссылку в поле поиска
function toFindInput(link){
    $('.InputClass').val($(link).text());
}
//функция валидирует введенный URL, отпраляет асинхронный запрос на сервер, обрабатывает ответ
function analyze(){
    var expression = "^(https?://)(www\\.)?([-a-z0-9]{1,63}\\.)*?[a-z0-9][-a-z0-9]{0,61}[a-z0-9]\\.[a-z]{2,6}(/[-\\w@\\+\\.~#\\?&/=%]*)?$";
    var regex = new RegExp(expression);
    if(!regex.test($('.InputClass').val())){
        showMessage('error','Не корректный URL!');
        return;
    }
    startWaitAjax('Идет обработка...');
    clearResults();
    $.ajax({
        url : '/analyze',
        type: 'POST',
        dataType:'json',
        data:{
            url:$('.InputClass').val(),
        },
        success:function (data, status) {
            stopWaitAjax();
            if(Object.keys(data).length == 0) {
                showMessage('warning', 'Не найдено ссылок по адресу ' + $('.InputClass').val());
            }
            var i = 1;
            for(var key in data){
                $('#table').find('tbody').append(
                    '<tr><td width="5%">'+i+'</td><td width="40%">'+data[key]+'</td><td width="55%" ><a href="#" onclick="toFindInput(this)">'+key+'</a></td></tr>'
                );
                i++;
            }
            $('#table').find('*').css({"font-size":"18px", "font-weight": "normal"});
        },
        error:function (data, status) {
            stopWaitAjax();
            showMessage('error','Ошибка на сервере((');
        }
    });
}
//функция очищает результаты поиска
function clearResults(){
    $('#table').find('tbody').empty();
}
