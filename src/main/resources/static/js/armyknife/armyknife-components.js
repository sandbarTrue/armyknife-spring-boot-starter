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
validSQL = function(sql) {

   if(sql.indexOf("where")==-1)
    return false;
   return true;
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
};
var app = {};
app.menu={
    template: "#menu",
    created () {
        // 组件创建完后获取数据，
        // 此时 data 已经被 observed 了
        this.loadData();
    },
    data:function(){
        return {
            counterSign:'',
            menuKey:'/key1',
            key1:"",
            menuModelList:[],
            menuLink:"/armyknife/index"

        }
    },
    methods: {
        loadData:function () {
            armyknifeMqApi.loadMenuData(null,function(data) {
                this.menuModelList=data;
            }.bind(this));
        },
        submit: function() {
            armyknifeMqApi.login(this.counterSign);
        },
        select:function(link){
            this.menuLink=link;
        }
    }
};

