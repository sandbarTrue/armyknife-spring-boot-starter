(function(root, $http) {
    var api = {};
    api.armyknifeInterfaceUri="/api/armyknife/interface"

    api.operatProduct = function(query, func1, func2) {
        var paramStr = "/operatProduct";
        $http.post(api.commonUri+paramStr,query).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.operatApply = function(query, func1, func2) {
        var paramStr = "/operatApply";
        $http.post(api.commonUri+paramStr,query).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.workfolwmq = function(query, func1, func2) {
        var paramStr = "/workfolwmq";
        $http.post(api.commonUri+paramStr,query).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.invoke = function(query, func1, func2) {
        var url = api.armyknifeInterfaceUri+"/invoke";
        $http.put(url,query).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response.data);
        });
    };
    api.sendmq = function(query, func1, func2) {
        var url = api.armyknifeMqUri+"/sendmq";
        $http.put(url,query).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.addMqTopic = function(query, func1, func2) {
        var paramStr = "";
        for(var i in query) {
            query[i] ? paramStr += "&" + i + "=" + encodeURIComponent(query[i]) : null;
        }
        paramStr = "/?" + paramStr.substr(1);
        var url = api.commonUri+"/addMqTopic";
        $http.get(url+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };

    api.getInterfaces = function(query,func1, func2) {
        var paramStr = "/getInterfaces";
        $http.get(api.armyknifeInterfaceUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.getProducers = function(query,func1, func2) {
        var paramStr = "/getProducers";
        $http.get(api.armyknifeMqUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    root.armyknifeInterfaceApi = api;
})(window, Vue.http);