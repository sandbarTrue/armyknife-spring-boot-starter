try{
(function(){
    Vue.http.interceptors.push(function(request, next) {
        try{
            if(!request.hideLoading) {
                commonMsg.loading('正在加载中...');
                commonMsg.cover();
            }
            if(request.method.equalsIgnoreCase('POST') && request.body != null){
                var isStr = typeof request.body == 'string';
                var bj = isStr?JSON.parse(request.body):request.body;
                bj = stringUtil.copy(bj,true);
                request.body = isStr?JSON.stringify(bj):bj;
            }else if(request.method.equalsIgnoreCase('get') && request.url.indexOf("?") != -1){
                var u = request.url.substring(0,request.url.indexOf("?"));
                var map = stringUtil.getQuery(request.url);
                if(map != null){
                    var parmet = stringUtil.copy(map,true);
                    for(var i in parmet) {
                        u+=(u.indexOf("?")==-1?"?":"&")+i+"="+parmet[i]
                    }
                    request.url = u;
                }
            }
            request['hideLoading'] = true;
        }catch(e){
            console.log(e);
            commonMsg.closeCover();
            commonMsg.closeLoadin();
        }
        next(function(response) {
            commonMsg.closeCover();
            commonMsg.closeLoadin();
            try{
                response.data = typeof response.data == "string" ? response.data != "" ? JSON.parse(response.data) : '' : response.data;
            }catch(e){}
            if(response.status == 500){
                commonMsg.notice('出了点小问题',"系统繁忙，请稍后再试",2,"error");
            } else if(response.status == 550) {
                var msg = "系统繁忙，请稍后再试";
                try{
                    msg = response.data.errorMsg;//.split(":")[1].split('\r\n')[0]
                    commonMsg.notice(null,msg,10,"error");
                    return;
                }catch(e){}
                commonMsg.notice(msg);
            } else if(response.status != 200) {
                commonMsg.notice('出了点小问题',"您的网络连接失败",2,"error");
            }
        });
    });
})();
}catch (e){}

var stringUtil = {
    index:0,
    setFocus:function (_this,name,tagName,time) {
        setTimeout(function(){
            if(tagName == 'input'){
                _this.$els[name.toLocaleLowerCase()].querySelector(tagName).focus();
            }else{
                _this.$els[name.toLocaleLowerCase()].querySelector(tagName).click();
            }
        },time==null?100:time);
    },
    isNotBlank :function(cs){
        return !this.isBlank(cs);
    },
    isBlank :function(cs){
        return cs == null || cs == '';
    },
    parseParam: function(param) {//json转url get参数  json对象，参数加的前缀
        if(param == null || param == '' || typeof param == 'number' || typeof param == 'string' || typeof param == 'boolean'){
            return param;
        }
        var data = JSON.parse(JSON.stringify(param));
        var paramStr = "";
        for(var i in param){
            var val = param[i];
            if (val != null && val != '' && (typeof val == 'number' || typeof val == 'string' || typeof val == 'boolean')) {
                paramStr += "&" + i + "=" + encodeURIComponent(val);
            }else if(val != null && typeof val == 'object' && typeof val.getTime == 'function'){
                paramStr += "&" + i + "=" + val;
            }
        }
        return paramStr.substr(1);
    },
    isEmptyObject:function(e) {
        var t;
        for (t in e)
            return !1;
        return !0
    },
    addIndex:function(arr,isCopy){
        for(var i in arr){
            arr[i].index = stringUtil.index++;
        }
        return isCopy ? stringUtil.copy(arr) : arr;
    },
    copy:function (json,trim) {
        if (typeof json == 'number' || typeof json == 'string' || typeof json == 'boolean') {
            return trim?stringUtil.trim(json):json;
        } else if (typeof json == 'object') {
            if (json instanceof Array) {
                var newArr = [],
                    i, len = json.length;
                for (i = 0; i < len; i++) {
                    newArr[i] = arguments.callee(trim?stringUtil.trim(json[i]):json[i]);
                }
                return newArr;
            } else {
                var newObj = {};
                for (var name in json) {
                    if(json[name] != null && typeof json[name] == 'object' && typeof json[name].getTime == 'function'){
                        newObj[name] = new Date(json[name].getTime());
                    }else{
                        newObj[name] = arguments.callee(json[name] == 0 ? json[name] : ((trim?stringUtil.trim(json[name]):json[name]) || ""));
                    }
                }
                return newObj;
            }
        }
    },
    trim : function(str) {
        if(typeof str != 'string'){
            return str;
        }
        return str.replace(/(^\s*)|(\s*$)/g, '');
    },
    getBlen:function(str) {
        if (str == null) return 0;
        if (typeof str != "string"){
            str += "";
        }
        return str.replace(/[^\x00-\xff]/g,"01").length;
    },
    getQuery:function(url){
        if(typeof url!=='string'){
            return '';
        }
        var query=url.match(/[^\?]+\?([^#]*)/,'$1');
        if(!query || !query[1]){
            return null;
        }
        var kv=query[1].split('&');
        var map={};
        for(var i=0,len=kv.length;i<len;i++){
            var result=kv[i].split('=');
            var key=result[0],value=result[1];
            //将"c="认为key为c，value为null，将"e"认为key为e，value为true
            map[key]=value || (typeof value=='string'?null:true);
        }
        return map;
    },
    getQueryString:function(name,url) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = url.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    },
    dateFormat: function (date,format) {
        if(date == null || date == ''){return "";}

        var _this = new Date(date);
        var o = {
            "M+": _this.getMonth() + 1, //month
            "d+": _this.getDate(), //day
            "h+": _this.getHours(), //hour
            "m+": _this.getMinutes(), //minute
            "s+": _this.getSeconds(), //second
            "q+": Math.floor((_this.getMonth() + 3) / 3), //quarter
            "S": _this.getMilliseconds() //millisecond
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (_this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }
};
String.prototype.equalsIgnoreCase = function(str) {//不区分大小写
    if (this.toLowerCase() == str.toLowerCase()) {
        return true; // 正确
    } else {
        return false; // 错误
    }
};

function remoteMethod($this, query,url,remoteLoading,remoteOptions) {
    if (query !== '') {
        if(!$this[remoteLoading]){
            $this[remoteLoading] = true;
            Vue.http.get(url+query).then(function(item){
                $this[remoteLoading] = false;
                $this[remoteOptions] = item.data;
            });
        }
    } else {
        $this[remoteOptions] = [];
    }
}
try{
var commonMsg = {
    key : "pmsMessage_publicArea",
    notice:function(title,content,duration,level,onClose){//消息提示框
        var options = {
            title: title ? title : '',
            desc: content ? content : '',
            duration:duration || duration==0 ? duration  : undefined,
            onClose:onClose ? onClose : undefined,
            level:level ? level : 'open'
        };
        commonMsg.open(options,1);
    },
    closeNotice:function(){
        new Vue({
            el:"#"+commonMsg.key,
            methods:{
                close:function(){
                    this.$Notice.destroy();
                }
            }
        }).close();
    },
    message:function(content,level,onClose,duration){//小型提示框 level :info,success,warning,error
        var options = {
            content: content ? content : '',
            duration:duration || duration==0 ? duration  : undefined,
            onClose:onClose ? onClose : undefined,
            level: level ? level : 'info'
        };
        commonMsg.open(options,2);
    },
    loading:function(content,onClose){
        commonMsg.message(content,'loading',onClose,0 );
    },
    cover:function(){
        var div = document.getElementById("opencoverdiv");
        if(div == null){
            div = document.createElement("div");
            div.setAttribute("id","opencoverdiv");
            div.setAttribute("style","cursor:not-allowed;opacity: 0;position: absolute;top: 0;bottom: 0;left: 0;right: 0;z-index: 800000;display: table;width: 100%;height: 100%;");
            document.body.appendChild(div);
        }
        div.style.display="";

    },
    closeCover:function(){
        var div = document.getElementById("opencoverdiv");
        if(div != null){
            div.style.display="none";
        }
    },
    closeLoadin:function(){
        new Vue({
            el:"#"+commonMsg.key,
            methods:{
                close:function(){
                    this.$Message.destroy();
                }
            }
        }).close();
    },
    alert:function(title,content,closeFun,level){//强调确定框 level :info,success,warning,error
        var options = {
            title:title ? title  : "提示",
            content: content ? content : '',
            level: level ? level : 'info',
            onOk: closeFun ? closeFun : undefined,
            onCancel: closeFun ? closeFun : undefined
        };
        commonMsg.open(options,3);
    },
    confirm:function(title,content,onOk,onCancel,okText,cancelText,width,loading){// 选择对话框
        var options = {
            title:title ? title  : "提示",
            content: content ? content : '',
            onOk : onOk ? onOk : undefined,
            onCancel : onCancel ? onCancel : undefined,
            okText: okText ? okText : '确定',
            cancelText: cancelText ? cancelText : '取消',
            width: width ? width : undefined,
            loading: loading ? loading : undefined
        };
        commonMsg.open(options,4);
    },
    open:function(options,n) {
        if (document.getElementById(commonMsg.key) == null) {
            var div = document.createElement("div");
            div.setAttribute("id", commonMsg.key);
            document.body.appendChild(div);
        }
        new Vue({
            el:"#"+commonMsg.key,
            methods:{
                open:function () {
                    switch(n){
                        case 1:
                            this.$Notice[options.level](options);
                            break;
                        case 2:
                            this.$Message[options.level](options.content,options.duration,options.onClose);
                            break;
                        case 3:
                            this.$Modal[options.level](options);
                            break;
                        case 4:
                            this.$Modal.confirm(options);
                            break;
                        default:
                            console.log(options);
                    }
                }
            }
        }).open();
    }
}
}catch (e){}

function isObj(object) {
    return object && typeof (object) == 'object' && Object.prototype.toString.call(object).toLowerCase() == "[object object]";
}
function isArray(object) {
    return object && typeof (object) == 'object' && object.constructor == Array;
}
function getLength(object) {
    var count = 0;
    for (var i in object) count++;
    return count;
}
//两个json对比
function Compare(objA, objB) {
    if (!isObj(objA) || !isObj(objB)) return false; //判断类型是否正确
    if (getLength(objA) != getLength(objB)) return false; //判断长度是否一致
    return CompareObj(objA, objB, true);//默认为true
}
//对比两个对象
function CompareObj(objA, objB, flag) {
    for (var key in objA) {
        if (!flag) //跳出整个循环
            break;
        if (!objB.hasOwnProperty(key)) { flag = false; break; }
        if (!isArray(objA[key])) { //子级不是数组时,比较属性值
            if (!CompareAttribute(objB[key], objA[key])) { flag = false; break; }
        } else {
            if (!isArray(objB[key])) { flag = false; break; }
            var oA = objA[key], oB = objB[key];
            if (oA.length != oB.length) { flag = false; break; }
            for (var k in oA) {
                if (!flag) //这里跳出循环是为了不让递归继续
                    break;
                if (!CompareAttribute(oA[k] ,oB[k])) { flag = false; break; }
            }
        }
    }
    return flag;
}
//对比两个属性
function CompareAttribute(A,B) {
    if(A == null && B == ""){
        return true;
    }
    if(A == "" && B == null){
        return true;
    }
    if(A == B){
        return true;
    }
    return false;
}