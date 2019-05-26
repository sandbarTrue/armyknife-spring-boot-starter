Date.prototype.convert = function(format) {
    var args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds()
    };
    if(/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for(var i in args) {
        var n = args[i];
        if(new RegExp("(" + i + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
    }
    return format;
};
isJSON= function (str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
        } catch(e) {
                new Vue().$Modal.error({
                title: "提示",
                content: e,
            });

            return false;
        }
    }
    return true;
}
var formatJson = function (json, options) {
             var reg = null,
                         formatted = '',
                        pad = 0,
                        PADDING = '    ';
             options = options || {};
             options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
             options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
             if (typeof json !== 'string') {
                     json = JSON.stringify(json);
                 } else {
                     json = JSON.parse(json);
                     json = JSON.stringify(json);
                 }
            reg = /([\{\}])/g;
            json = json.replace(reg, '\r\n$1\r\n');
             reg = /([\[\]])/g;
             json = json.replace(reg, '\r\n$1\r\n');
             reg = /(\,)/g;
             json = json.replace(reg, '$1\r\n');
             reg = /(\r\n\r\n)/g;
             json = json.replace(reg, '\r\n');
             reg = /\r\n\,/g;
            json = json.replace(reg, ',');
             if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
                     reg = /\:\r\n\{/g;
                     json = json.replace(reg, ':{');
                     reg = /\:\r\n\[/g;
                     json = json.replace(reg, ':[');
                 }
             if (options.spaceAfterColon) {
                     reg = /\:/g;
                     json = json.replace(reg, ':');
                 }
             (json.split('\r\n')).forEach(function (node, index) {
                             var i = 0,
                                         indent = 0,
                                         padding = '';

                             if (node.match(/\{$/) || node.match(/\[$/)) {
                                     indent = 1;
                                 } else if (node.match(/\}/) || node.match(/\]/)) {
                                     if (pad !== 0) {
                                             pad -= 1;
                                         }
                                 } else {
                                     indent = 0;
                                 }

                             for (i = 0; i < pad; i++) {
                                     padding += PADDING;
                                 }

                             formatted += padding + node + '\r\n';
                             pad += indent;
                         }
             );
             return formatted;
         };
var formatString=function (testStr) {
    var resultStr = testStr.replace(/\ +/g, ""); //去掉空格
    resultStr = testStr.replace(/[ ]/g, "");    //去掉空格
    resultStr = testStr.replace(/[\r\n]/g, ""); //去掉回车换行
    return resultStr;
};
var app = {};
app.interfaceinvoke={
    template: "#interfaceinvoke",
    watch: {
        '$route': function () {
            this.loadData();
        }
    },
    created () {
        // 组件创建完后获取数据，
        // 此时 data 已经被 observed 了
        this.loadData();
    },
    data:function(){
        return {
            self:this,
            isButtonDisable:false,
            invokeModel:{
                beanName:"",
                methodName:"",
                arguType:[],
                arguValue:[],
                arguName:[]
            },
            responseStr:'',
            interfaces:[],
            SelectedMethodList:[],
            SelectedArgumentList:[]
        }
    },
    methods: {
        loadData:function(){
            armyknifeInterfaceApi.getInterfaces(null,function (data) {
                    if(data){
                        this.interfaces=data;

                    }
                    else
                        this.interfaces=[];
                }.bind(this)
            );
        },
        initMethod:function(){
            for(var i=0;i<this.interfaces.length;i++){
                if(this.interfaces[i].beanName==this.invokeModel.beanName){
                    this.SelectedMethodList=this.interfaces[i].methodModelList;
                    this.SelectedArgumentList=[]
                    this.invokeModel.methodName=null;
                    this.invokeModel.arguName=null;
                }
            }
        },
        initArgument:function () {
            for(var i=0;i<this.SelectedMethodList.length;i++){
                if(this.SelectedMethodList[i].methodName==this.invokeModel.methodName){
                    this.SelectedArgumentList=this.SelectedMethodList[i].arumentModelList;
                    this.invokeModel.arguName=null;
                    this.invokeModel.arguValue=[]
                    this.invokeModel.arguName=[]
                    this.invokeModel.arguType=[]
                    this.buildInVokeMode();
                }
            }
        },
       buildInVokeMode:function(){
            for(var i=0;i<this.SelectedArgumentList.length;i++){
                this.invokeModel.arguName[i]=this.SelectedArgumentList[i].arguName;
                this.invokeModel.arguType[i]=this.SelectedArgumentList[i].arguType;
                var initialValue=this.SelectedArgumentList[i].initialValue;
                this.invokeModel.arguValue[i]=formatJson(initialValue);
            }
        },
        submit: function () {
            for(var i=0;i<this.invokeModel.arguValue.length;i++){
                this.invokeModel.arguValue[i]=formatString(this.invokeModel.arguValue[i])
                if(this.invokeModel.arguValue[i]==null || this.invokeModel.arguValue[i]==''){
                    this.invokeModel.arguValue[i]==null
                    continue;
                }
                var  result=isJSON(this.invokeModel.arguValue[i])
                if(!result){
                    return ;
                }
            }
            this.isButtonDisable=true
            armyknifeInterfaceApi.invoke(this.invokeModel, function (data) {
                this.isButtonDisable=false;
                if (!data) {
                    window.location.href = "/common/error"
                    return;
                }
                if(data.code==200){
                    this.responseStr=formatJson(data.data.data);
                }
                else{
                    this.$Modal.error({
                        title: "提示",
                        content: "执行失败," + data.msg,
                    });

                }
            }.bind(this),function (data) {

            }.bind(this));
        },
    }
};


