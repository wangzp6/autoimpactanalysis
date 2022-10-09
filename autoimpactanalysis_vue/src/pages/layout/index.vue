<template>
    <el-container style="min-height: 100vh;">
        <el-aside>
            <sidebarNav @asideCollapse="collapse" :collapseBtnClass="collapseBtnClass" :isCollapse="isCollapse" :logoTextShow="logoTextShow" class="sidebar" :style="sidebar"/>
        </el-aside>
        <el-header>
             <TopAside :style="topaside" />
        </el-header>
    <el-main style="padding:0px">
        <router-view :style="mainCon" class="content"></router-view>
    </el-main>
    </el-container>

</template>

<script>
import sidebarNav from "./component/sidebar-nav";
import TopAside from './component/top-aside'

export default {
    data() {
        return {
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            logoTextShow: true,
            sidebar:{
                height : '100vh',
                width: '272px',
                overflowY: '',
            },
            topaside:{
                left : '272px',
                width: 'calc(100vw - 272px)',
            },
            mainCon:{
                paddingLeft:'272px'
            },
            
        }
    },
    components: {
        sidebarNav,
        TopAside,
    },
    methods: {
        collapse() {//点击收缩按钮触发
            this.isCollapse = !this.isCollapse;
            if (this.isCollapse) { //收缩
                this.collapseBtnClass = 'el-icon-s-unfold';
                // this.logoTextShow = false;
                this.sidebar.height = '90px';
                this.mainCon.paddingLeft = '0px';
                this.sidebar.overflowY = 'hidden';
            } else { //展开
                this.collapseBtnClass = 'el-icon-s-fold';
                // this.logoTextShow = true;
                this.sidebar.height = '100vh';
                this.mainCon.paddingLeft = '272px';
                this.sidebar.overflowY = 'auto';
            }
        },

    }
};
</script>

<style lang="scss" scoped>
.content{
    padding-top:31px;
}
</style>
