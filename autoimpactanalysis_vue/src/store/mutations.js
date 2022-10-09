import router from '../router/index'
export default {
    LOGIN_IN(state,token){
        state.UserToken = token;
    },
    LOGIN_OUT(state){
        state.UserToken = ""
    },
    ADD_PAGE(state, page) {
        if (!state.permission.openPages.some(r => r.path === page.path)) {
            state.permission.openPages.push(page);
        } 
       // state.permission.openPages.push(page)
    },
    REMOVE_PAGE(state, path) {
        let idx = state.permission.openPages.findIndex(r => r.path === path);
        let currPath = router.currentRoute.path;
        //自行控制回到上一个路由
        if (state.permission.openPages.length > 1 && state.permission.openPages[idx].path === currPath) { 
            /* if (idx === 0) {
                router.push(state.permission.openPages[idx + 1].path);
            } else { 
                router.push(state.permission.openPages[idx - 1].path);
            } */
            router.push(state.permission.openPages[idx + (!idx?1:-1)].path);

        }
        state.permission.openPages.splice(idx, 1)
    },
    CLEAR_PAGE(state) {
        state.permission.openPages = []
    },
    /*SET_PATH(state) {  //state，驱动应用的数据源
        state.currentPathName = localStorage.getItem("currentPathName")
    },*/
    SET_USER(state) {  //state，驱动应用的数据源
        state.user = localStorage.getItem("user")
    },
    SET_CURRENTMENU(state,routes){
        console.log("routes");
        state.currentMenu = routes;
        console.log(state.currentMenu);
    },
}