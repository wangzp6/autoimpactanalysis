<template>
    <aside class="aside__top" style="display: flex">
        <div class="aside__top--left">
            <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
                <el-breadcrumb-item :to="'/home'">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{currentName}}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="aside__top--right">
            <div class="user-msg">
                <span class="user-name">{{user.nickname}}</span>
                <el-dropdown trigger="click" placement="top">
                    <span class="el-dropdown-link">
                        <i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>个人中心</el-dropdown-item>
                        <el-dropdown-item>修改密码</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
            <div class="quit-system" @click="loginOut">退出登录</div>
        </div>
        <div class="aside__top--left_tabs">
            <el-tabs type="card"
                     v-model="currentName"
            @tab-click="handTableClick" 
            :closable="$store.state.permission.openPages.length > 1"
            @tab-remove="handleTabRemove">
                <el-tab-pane
                    :key="item.path"
                    v-for="item in $store.state.permission.openPages"
                    :label="item.name"
                    :name="item.path"
                >
                </el-tab-pane>
            </el-tabs>
        </div>
    </aside>
</template>

<script>

import router from "../../../router/index";
export default {
    data(){
        return{
            user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{},
            currentName: router.currentRoute.path
        }
    },
    props: {
            collapseBtnClass: String,
    },
/*     computed: {
        currentPathName(){
                return this.$store.state.currentPathName; //需要监听的数据
            }
    }, */
    methods: {
/*        toggleNavCollapse() {
            this.$store.commit("toggleNavCollapse");
        },*/
        loginOut() {
            this.$store.commit("LOGIN_OUT");
            /* 防止切换角色时addRoutes重复添加路由导致出现警告 */
            window.location.reload();
        },
        handTableClick({ _props: { name } }) {
            console.log(name);
            this.$router.push(name);
        },
        handleTabRemove(path){
            this.$store.commit("REMOVE_PAGE",path);
        },
        collapse(){
            this.$emit("asideCollapse")
        },
    },
    watch:{
        $route(){
            this.currentName=router.currentRoute.path;
            console.log(router);
        }
    }
};
</script>

<style lang="scss" scoped>
.aside__top {
    border-bottom: 1px solid #e5e5e5;
    height: 90px;
    line-height: 90px;
    position: fixed;
    /* left: 272px; */
    /* width: calc(100vw - 272px); */
    top: 0;
    right: 0;
    background: #fff;
    z-index: 1000;
    transition: left 0.25s;
/*    .toggleNavCollapse {
        display: inline-block;
        margin-left: 8px;
        padding: 0 10px;
        font-size: 26px;
        vertical-align: middle;
        color: #333;
        cursor: pointer;
        transition: all 0.5s;
        &.active {
            transform: rotate(90deg);
        }
    }*/
    .aside__top--left {
        position: absolute;
        left: 10px;
        top: -1px;
        height: 50px;
        line-height: 50px;
        bottom: 0px;
        > div {
            position: relative;
            display: inline-block;
            text-align: left;
            vertical-align: middle;
            margin-left: 10px;
            padding: 0 15px;
            cursor: pointer;
        }
    }
    .aside__top--right {
        position: absolute;
        right: 10px;
        top: -1px;
        height: 50px;
        line-height: 50px;
        bottom: 0px;
        > div {
            position: relative;
            display: inline-block;
            text-align: center;
            vertical-align: middle;
            margin-left: 10px;
            padding: 0 15px;
            cursor: pointer;
            &:hover::after {
                transform-origin: 0 0;
                transform: scaleX(1);
            }
            &:first-child:before {
                border: none;
            }
            &::after {
                content: "";
                position: absolute;
                left: 0;
                bottom: 0;
                width: 100%;
                height: 2px;
                background: #ef4747;
                transform: scaleX(0);
                transform-origin: right 0;
                transition: transform 0.5s;
            }
            &::before {
                content: "";
                position: absolute;
                height: 20px;
                top: 50%;
                left: -8px;
                margin-top: -10px;
                border-left: 1px solid #ccc;
            }
            &.user-msg {
                .user-img {
                    width: 34px;
                    height: 34px;
                    border-radius: 50%;
                    vertical-align: middle;
                }
                .user-name {
                    color: #758eb5;
                    padding: 0 4px;
                }
            }
        }
    }
    .aside__top--left_tabs {
    margin-top: 50px;
    height: 41px;
    line-height: 41px;
    font-size: 5px;
}
}


.breadcrumb-enter,
.breadcrumb-leave-active {
    opacity: 0;
    transform: translateX(20px);
}

.breadcrumb-enter-active,
.breadcrumb-leave-active {
    transition: all 0.6s;
}

.breadcrumb-leave-active {
    position: absolute;
}
</style>
