(function (root, $http) {
    var api = {};
    api.armyknifeMySQLUri = "/api/armyknife/db"

    api.load = function (func1, func2) {
        var paramStr = "/mysql/load";
        $http.get(api.armyknifeMySQLUri + paramStr).then(function (response) {
            func1(response.data);
        }, function (response) {
            if (func2) func2(response);
        });
    };

    api.doSql=function (datasource,sql,func1,func2) {
        var paramStr="/mysql/dosql?dataSource="+datasource+"&sql="+sql;
        $http.get(api.armyknifeMySQLUri + paramStr).then(function (response) {
            func1(response.data);
        }, function (response) {
            if (func2) func2(response);
        });
    }
    root.armyknifeMysqlApi = api;
})(window, Vue.http);