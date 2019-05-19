Vue.config.devtools = true;
var router = new VueRouter({});
router.map({
    '/': {
        component: app.login,
    }


});
var App = Vue.extend({});
router.start(App, '#app');