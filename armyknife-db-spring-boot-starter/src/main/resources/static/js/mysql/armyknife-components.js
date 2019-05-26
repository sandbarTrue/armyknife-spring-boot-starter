Date.prototype.convert = function (format) {
    var args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var i in args) {
        var n = args[i];
        if (new RegExp("(" + i + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
    }
    return format;
};
isJSON = function (str) {
    if (typeof str == 'string') {
        try {
            var obj = JSON.parse(str);
        } catch (e) {
            new Vue().$Modal.error({
                title: "提示",
                content: e,
            });

            return false;
        }
    }
    return true;
}
var list = {
    template: "#list",
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
    data: function () {
        return {
            self: this,
            mysqlDataSources: [],
            mysqlDataSource: null,
            sql: null
        }
    },
    methods: {
        loadData: function () {
            armyknifeMysqlApi.load(function (data) {
                    if (data) {
                        var i = 0;
                        for (var key in data) {

                            var map = new Object();
                            map.label = key;
                            map.value = data[key];
                            this.mysqlDataSources.push(map);
                            i++;
                        }


                        console.log(this.colony);
                    }
                    else {
                        this.mysqlDataSources = [];
                    }

                }.bind(this)
            );
        },

        doSQl: function () {

            armyknifeMysqlApi.doSql(this.mysqlDataSource, this.sql, function (response) {

                if (response == true) {
                    this.$Notice.success({

                        title: '',
                        desc: '执行成功'
                    });
                } else {
                    this.$Notice.error({

                        title: '',
                        desc: '执行失败'
                    });
                }


            }.bind(this), function (error) {

            }.bind(this))

        }


    },
    ready: function () {
        this.loadData();
    }
};


