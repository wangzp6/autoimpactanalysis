<template>
    <div class="aside_left">
        <div class="aside__left--top" style="flex: 1; font-size: 22px; height: 60px; line-height: 60px; font-family: 华文楷体; color: rgb(242, 253, 255);">
            <b v-show="logoTextShow">数据工作支持系统</b>
            <span :class="collapseBtnClass" style="cursor: pointer; left: 210px; top: 20px; position: absolute;" @click="collapse"></span>
        </div>
        <el-menu
            background-color="transparent"
            text-color="#eee"
            active-text-color="#D9D919"
            :default-active="currentPage"
            style="margin-top: 50px; min-height: calc(100vh - 50px); overflow-x: hidden"
            :collapse="isCollapse"
            :collapse-transition="false"
        >
            <DynamicMenu :menuList="sidebarMenu"></DynamicMenu>
        </el-menu>
    </div>
</template>

<script>
import DynamicMenu from "@/components/dynamic-menu";
import { mapState } from "vuex";
import router from "../../../router/index";

export default {
    data(){
        return{
            currentPage: router.currentRoute.name
        }
    },
    props: {
            isCollapse: Boolean,
            logoTextShow: Boolean,
            collapseBtnClass: String,
    },
    computed: {
        ...mapState("permission", ["sidebarMenu"]),
    },
    methods: {
        collapse(){
            this.$emit("asideCollapse")
        },
    },
    components: {
        DynamicMenu,
    },
    watch:{
        $route(){
            this.currentPage = router.currentRoute.name;
            console.log(this.currentPage);
        }
    }
};
</script>
<style>
.aside_left {
    border-radius: 0 20px 20px 0;
}
#find_input{
    height: 30px;
    line-height: 30px;
}
.aside__left--top {
        position: absolute;
        left: 10px;
        top: -1px;
        height: 50px;
        line-height: 50px;
        bottom: 0px;
    }
</style>
