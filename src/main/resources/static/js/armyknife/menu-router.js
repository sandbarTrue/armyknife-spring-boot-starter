Vue.config.devtools = true;
var router = new VueRouter({});
/*router.redirect({
    // 重定向 /a 到 /b
    '/': '/key1'
})*/
router.map({
    '/': {
        component: app.menu,
    },
});
var App = Vue.extend({});
router.start(App, '#app');