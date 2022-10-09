import router from "./index"
import store from "../store/index"


router.beforeEach((to, from, next) => {
    //判断是否有Token（是否登录）
    if(!store.state.UserToken){
        // 未登录  页面是否需要获取权限
        if (to.matched.length > 0 && !to.matched.some(record => record.meta.requiresAuth)) {
            // 此页面无需权限可直接访问
            next();
        } else {
            // 访问此页面需要权限，跳转至登录页面
            next({
                path:"/login"
            })
        }
    }else{
        // 用户已经登录  路由的访问权限
        if (!store.state.permission.permissionList) {
            //当store权限列表为空时获取权限列表
            store.dispatch("permission/FETCH_PERMISSION").then(() => {
                next({
                    path: to.path
                })
            })
        }else{
            // store存在权限
            if (to.path !== "/login") {
                //登录状态下页面跳转
                // localStorage.setItem("currentPathName", to.meta.name);//设置当前的路由名称,为了在header组件中去使用
                // store.commit("SET_PATH");//触发store的数据更新

                store.commit('ADD_PAGE', {
                    name: to.meta.name,
                    path: to.path
                });
                next();
            } else {
                //登录状态下访问login页面跳转到当前路由
                next(from.fullPath)
            }
        
        }
    }
})