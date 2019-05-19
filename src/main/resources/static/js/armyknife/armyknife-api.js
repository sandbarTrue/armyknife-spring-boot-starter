(function(root, $http) {
    var api = {};
    api.getAgreementInfoUri = "/api/common/agreement";
    api.commonUri="/api/common"

    api.deleteAgreementApplyById = function(query, func1, func2) {
        var paramStr = "/deleteAgreementApplyById/?id=" + query;
        $http.get(api.getAgreementInfoUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.deleteAgreementApplyByAgreementId = function(query, func1, func2) {
        var paramStr = "/deleteAgreementApplyByAgreementId?id=" + query;
        $http.get(api.getAgreementInfoUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.deleteAgreementById = function(query, func1, func2) {
        var paramStr = "/deleteAgreementById?id=" + query;
        $http.get(api.getAgreementInfoUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.loadMenuData = function(query, func1, func2) {
        $http.get("/api/armyknife/tools/loadMenuData").then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.deleteBillById = function(query, func1, func2) {
        var paramStr = "/deleteBillById?id=" + query;
        $http.get(api.getAgreementInfoUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.deleteBillByUuid = function(query, func1, func2) {
        var paramStr = "/deleteBillByUuid?uuid=" + query;
        $http.get(api.getAgreementInfoUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.login = function(query, func1, func2) {
        var paramStr = "/login?counterSign=" + query.counterSign+"&userName="+query.userName;
        $http.get(api.commonUri+paramStr).then(function(response) {

            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.rage = function(query, func1, func2) {
        var paramStr = "/rage?counterSign=" + query;
        $http.get(api.commonUri+paramStr).then(function(response) {

            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.getAuthErps = function(query,func1, func2) {
        var paramStr = "/auth/getAuthErps";
        $http.get(api.commonUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.authTiangou = function(query, func1, func2) {
        var paramStr = "/auth/authTiangou";
        debugger;
        $http.post(api.commonUri+paramStr,query).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.denyTiangou = function(query, func1, func2) {
        var paramStr = "/auth/denyTiangou";
        $http.post(api.commonUri+paramStr,query).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
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
    api.testmq = function(query, func1, func2) {
        var paramStr = "";
        for(var i in query) {
            query[i] ? paramStr += "&" + i + "=" + encodeURIComponent(query[i]) : null;
        }
        paramStr = "/?" + paramStr.substr(1);
        var url = api.commonUri+"/testmq";
        $http.get(url+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.sendmq = function(query, func1, func2) {
        var paramStr = "";
        for(var i in query) {
            query[i] ? paramStr += "&" + i + "=" + encodeURIComponent(query[i]) : null;
        }
        paramStr = "/?" + paramStr.substr(1);
        var url = api.commonUri+"/sendmq";
        $http.get(url+paramStr).then(function(response) {
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
    api.getMqTopics = function(query,func1, func2) {
        var paramStr = "/getMqTopics";
        $http.get(api.commonUri+paramStr).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.deleteMqTopic = function(query, func1, func2) {
        var paramStr = "/deleteMqTopic";
        $http.post(api.commonUri+paramStr,query).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    api.getMqClassName = function(query, func1, func2) {
        var url = api.commonUri+"/getMqClassName?topic="+query;
        $http.get(url).then(function(response) {
            func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    root.armyknifeMqApi = api;
})(window, Vue.http);