<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>HTTP请求</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/layui/2.5.6/css/layui.min.css" integrity="sha512-o+g1fl6FWuY42UXwVvA8Dk9C8OoU/DKSJjKTpjh2QWu2OXIYZlRNC3lu7iaIsCxa9oSv6KSLmk4R4QH8ujP0DA==" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/layer/3.1.1/theme/default/layer.css" integrity="sha512-Qr8+/ygZmNCIzgErmlkQ9ylRyRcVWVVyu5aPv8X6KxzdrO88poOhc060ERSzArak2ti3QyxYd7NWOggKJUeuBQ==" crossorigin="anonymous" />
</head>
<body style="width: 100%;">

<form class="layui-form layui-form-pane">
    <div class="layui-row" style="align-content: center;margin-top: 10px">
        <h1 align="center">Javaman</h1>
    </div>
    <div class="layui-row">
        <div class="layui-col-md9,layui-form-item" style="margin-top: 20px;width: 100%;display: flex;">
            <label style="min-width: 120px" class="layui-form-label">请求地址</label>
            <div class="layui-input-inline" style="width: 50%">
                <input id="httpHost" name="httpHost" lay-filter="httpHost" style="width: 100%" type="text" value="${hostUrl}" lay-verify="required|basecheck|urlcheck" class="layui-input">
            </div>
            <div id="HttpSuffixSelect" class="layui-input-inline" style="width: 35%">
                <select  id="HttpSuffixSelectValue" lay-filter="predicates" style="width: 100%">
                    <c:forEach var="predicate" items="${predicates}">
                        <option value="${predicate}">${predicate}</option>
                    </c:forEach>
                </select>
            </div>
            <div id="HttpSuffixInput" class="layui-input-inline" style="width: 35%">
                <input style="width: 100%" type="text" id ="HttpSuffixInputValue" name="HttpSuffixInputValue"  lay-filter="HttpSuffixInputValue" value="" lay-verify="required|basecheck" class="layui-input">
            </div>
        </div>
    </div>

    <div class="layui-form-item" style="width: 100%;display: flex;">
        <label style="min-width: 120px ;" class="layui-form-label">请求方式</label>
        <div class="layui-input-inline" style="width: 100%;">
            <select id="httpMethod" name="HttpMethod" lay-filter="HttpMethod">
                <option value="GET" selected="selected">GET</option>
                <option value="POST">POST</option>
            </select>
        </div>
    </div>

    <div class="layui-tab" >
        <ul class="layui-tab-title">
            <li class="layui-this">Params</li>
            <li>Headers</li>
            <li>Body</li>
        </ul>
        <div class="layui-tab-content" style="height: 400px">
            <div class="layui-tab-item layui-show">
                <div style="width: 100%;height: 400px">
                    <table id="paramTable" name="paramTable" lay-filter="paramTable"></table>
                </div>
            </div>
            <div class="layui-tab-item">
                <div style=" width: 100%;height: 400px">
                    <table id="headerTable" name="headerTable" lay-filter="headerTable"></table>
                </div>
            </div>
            <div class="layui-tab-item">
                <div carousel-item="layui-tab-fluid">
                    <div class="layui-tab layui-tab-brief" >
                        <ul class="layui-tab-title">
                            <li class="layui-this">none</li>
                            <li>x-www-form-urlencoded</li>
                            <li>raw</li>
                        </ul>
                        <div class="layui-tab-content">
                            <div class="layui-tab-item layui-show">none</div>
                            <div class="layui-tab-item ">
                                <div style="width: 100%;height: 400px">
                                    <table id="formDataTable" name="formDataTable" lay-filter="formDataTable"></table>
                                </div>
                            </div>
                            <div class="layui-tab-item">
                                <select name="rawType" lay-filter="rawType">
                                    <option>Text</option>
                                    <%--<option>javaScript</option>
                                    <option>HTML</option>--%>
                                    <option selected>JSON</option>
                                    <option>XML</option>
                                </select>
                                <div class="layui-inline" style="width: 100%;height: 300px">
                                        <textarea name="rawValue" lay-filter="rawValue" placeholder="请输入内容" class="layui-textarea" style="width: 100%;height: 100%"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-form-item " style="width: 100%;">
        <label style="min-width: 120px;width: 10%;margin-top: 10px" class="layui-form-label">响应结果</label>
        <div class="layui-input-inline" style="width: 89%;">
            <span style="float: right;"><i class="layui-icon">&#xe7ae;</i>&nbsp;&nbsp;&nbsp;&nbsp;Status:<span style="color: #57d957" id="RPStatus"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Time:<span style="color: #57d957" id="RPSTime"></span>ms</span>&nbsp;&nbsp;&nbsp;
            <br>
            <textarea name="httpResponse" lay-filter="httpResponse" id="httpResponse" class="layui-textarea" style="width: 100%;"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="send">发送请求</button>
        </div>
    </div>
</form>

<script type="text/html" id="commonBarRight">
    <a class=" layui-btn layui-btn-primary layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="commonBarTop">
    <a class=" layui-btn layui-btn-xs" lay-event="addRow">新增</a>
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/layui/2.5.6/layui.js" integrity="sha512-PvrrVb4lPJw4/lKqjDr9cr83kdvu6kRTz47JuEP//SnBDCSbeS1rIYV27FEuJ1PlKBVB1cXpwCLs9NKFVqC1PQ==" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/layer/3.1.1/layer.js" integrity="sha512-+eChqsll8P6yHFipVChRfsE5NwvLbQLNyGJsaa9krPx2UIxYle085/5PxgUf4CHMzRHuANGWEkeBLimjzcrFCQ==" crossorigin="anonymous"></script>
<script src="https://libs.cdnjs.net/jquery/3.5.1/jquery.js"></script>
<script>
    var width;
    var height;

    $(function(){
        width=$(window).width();
        height=$(window).height();
    })

    //数据check
    function datacheck(){
        //主机地址
        let httpHost = $("#httpHost").val();
        //请求url
        let HttpSuffixInputValue = $("#HttpSuffixInputValue").val();
        //请求方法
        let httpMethod = $("#httpMethod").val();
        //请求json
        let httpJson = $("#httpJson").val();

        if(urlCheck(httpHost)){
            layer.msg('URL输入格式错误！',{icon: 5});
            return true;
        }
    }

    function urlCheck(value){
        if(value){
            if(!new RegExp("http(s)?").test(value)){
                return true;
            }
        }
    }

    layui.use(['form','table','element'], function(){

        var form = layui.form;
        var table = layui.table;
        var element = layui.element;

        $("#httpBody").hide();
        $("#HttpSuffixInput").hide();
        $("#HttpSuffixInputValue").val($("#HttpSuffixSelectValue").val());
        $("#HttpSuffixSelect").find("dl").css('width','50%');

        //param表格
        var paramTableIns = table.render({
            elem: '#paramTable',
            toolbar: '#commonBarTop',
            width:width*0.98,
            height:380,
            data:[
                {
                    "paramKey":"",
                    "paramValue":""
                }
            ],//可以是当前展示的，或者后台反的单独拿出来展示，用的时候注释url和where
            cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            cols: [[ //表头
                {field: 'paramKey', title: 'paramKey',  edit: 'text'},
                {field: 'paramValue', title: 'paramValue', edit: 'text',},
                {field: 'wealth', title: '操作', width:100, toolbar: '#commonBarRight' }
            ]],
            done: function (res) {
                //表格完成后
            },
        });

        //header表格
        var headerTableIns = table.render({
            elem: '#headerTable',
            toolbar: '#commonBarTop',
            width:width*0.98,
            height:380,
            data:[
                {
                    "headerKey":"",
                    "headerValue":""
                }
            ],//可以是当前展示的，或者后台反的单独拿出来展示，用的时候注释url和where
            cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            cols: [[ //表头
                {field: 'headerKey', title: 'headerKey',  edit: 'text'},
                {field: 'headerValue', title: 'headerValue', edit: 'text',},
                {field: 'wealth', title: '操作', width:100, toolbar: '#commonBarRight' }
            ]],
            done: function (res) {
                //表格完成后
            },
        });

        //formData表格
        var formDataTableIns = table.render({
            elem: '#formDataTable',
            toolbar: '#commonBarTop',
            width:width*0.98,
            height:300,
            data:[
                {
                    "Key":"",
                    "Value":""
                }
            ],//可以是当前展示的，或者后台反的单独拿出来展示，用的时候注释url和where
            cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            cols: [[ //表头
                {field: 'Key', title: 'Key',  edit: 'text'},
                {field: 'Value', title: 'Value', edit: 'text',},
                {field: 'wealth', title: '操作', width:100, toolbar: '#commonBarRight' }
            ]],
            done: function (res) {
                //表格完成后
            },
        });

        //formData
        //监听添加一行和删除一行操作
        table.on('tool(formDataTable)', function (obj) {
            if (obj.event === 'del') {
                var Data = table.cache["formDataTable"];
                if (obj.tr.data('index') != 0) {
                    Data.splice(obj.tr.data('index'), 1)//根据索引删除当前行
                    formDataTableIns.reload({
                        data: Data
                    });
                }
            }
        });

        //Header
        //监听添加一行和删除一行操作
        table.on('tool(headerTable)', function (obj) {
            if (obj.event === 'del') {
                var Data = table.cache["headerTable"];
                if (obj.tr.data('index') != 0) {
                    Data.splice(obj.tr.data('index'), 1)//根据索引删除当前行
                    headerTableIns.reload({
                        data: Data
                    });
                }
            }
        });

        //param
        //监听添加一行和删除一行操作
        table.on('tool(paramTable)', function (obj) {
            if (obj.event === 'del') {
                var Data = table.cache["paramTable"];
                if (obj.tr.data('index') != 0) {
                    Data.splice(obj.tr.data('index'), 1)//根据索引删除当前行
                    paramTableIns.reload({
                        data: Data
                    });
                }
            }
        });

        //监听头部工具栏事件formDataTable
        table.on('toolbar(formDataTable)',function (obj) {
            if (obj.event === 'addRow') {
                var Data = table.cache["formDataTable"];
                var datas = {
                    "Key": ""
                    ,"Value": ""
                }
                Data.push(datas);
                formDataTableIns.reload({
                    data: Data
                });
            }
        });

        //监听头部工具栏事件headerTable
        table.on('toolbar(headerTable)',function (obj) {
            if (obj.event === 'addRow') {
                var Data = table.cache["headerTable"];
                var datas = {
                    "headerKey": ""
                    ,"headerValue": ""
                }
                Data.push(datas);
                headerTableIns.reload({
                    data: Data
                });
            }
        });

        //监听头部工具栏事件paramTable
        table.on('toolbar(paramTable)',function (obj) {
            if (obj.event === 'addRow') {
                var Data = table.cache["paramTable"];
                var datas = {
                    "paramKey": ""
                    , "paramValue": ""
                }
                Data.push(datas);
                paramTableIns.reload({
                    data: Data
                });
            }
        });

        //监听单元格编辑
        table.on('edit(paramTable)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键值
                ,field = obj.field; //得到字段
        });

        //监控下拉框
        form.on('select(HttpMethod)', function(data){
            if(data.value === "GET"){
                $("#httpBody").hide();
            }else if(data.value === "POST"){
                $("#httpBody").show();
            }else{
                $("#httpBody").hide();
            }
        });

        form.on('select(predicates)',function(data){
            $("#HttpSuffixInputValue").val(data.value);
            $("#HttpSuffixSelect").hide();
            $("#HttpSuffixInput").show();
        });

        //输入框的值改变时触发
        $("#HttpSuffixInputValue").on("input",function(e){
            //获取input输入的值
            if(e.delegateTarget.value.length < 1){
                $("#HttpSuffixSelect").show();
                $("#HttpSuffixInput").hide();
                $("#HttpSuffixInputValue").val($("#HttpSuffixSelectValue").val());
            }
        });

        //发送ajax请求
        form.on('submit(send)', function(data){

            var params = {};
            var headers = {};
            var formData = {};
            var HttpRequestModel = {};

            let fields = data.field;

            let HttpMethod = fields.HttpMethod.trim();
            let HttpSuffixInputValue = fields.HttpSuffixInputValue.trim();
            let httpHost = fields.httpHost.trim();
            let rawType = fields.rawType.trim();
            let rawValue = fields.rawValue.trim();

            var paramTable = table.cache["paramTable"];
            var headerTable = table.cache["headerTable"];
            var formDataTable = table.cache["formDataTable"];

            paramTable.forEach(function (value) {
                let key = value.paramKey;
                params[key] = value.paramValue;
            })
            headerTable.forEach(function (value) {
                let key = value.headerKey;
                headers[key] = value.headerValue;
            })
            formDataTable.forEach(function (value) {
                let key = value.Key;
                formData[key] = value.Value;
            })

            HttpRequestModel.type = HttpMethod;
            HttpRequestModel.url = httpHost + HttpSuffixInputValue;
            HttpRequestModel.rawType = rawType;
            HttpRequestModel.rawValue = rawValue;
            HttpRequestModel.params = params;
            HttpRequestModel.headers = headers;
            HttpRequestModel.formData = formData;

            //if(datacheck()){return false};

            $.ajax({
                url:"/send",
                type:'POST',
                data:JSON.stringify(HttpRequestModel),
                contentType : "application/json",
                beforeSend:function () {
                    this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
                },
                success:function(result){
                    $("#RPStatus").html(result.obj.code);
                    $("#RPSTime").html(result.obj.duration);
                    $("#httpResponse").val(result.obj.content);
                },
                complete: function () {
                    layer.close(this.layerIndex);
                }
            });
            return false;
        });

    });
</script>
</body>
</html>