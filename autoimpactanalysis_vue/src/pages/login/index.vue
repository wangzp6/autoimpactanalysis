<template>
    <div class="clearfix" id="login_wrap">
        <div>
            <div>
                <h2 class="title">登 录</h2>
            </div>
            <div class="login">
                <el-form :model="user" :rules="rules" ref="userForm">
                    <el-form-item prop="username">
                        <el-input size="mediun" style="margin: 10px 0" prefix-icon="el-icon-user"
                                  v-model="user.username" placeholder="请输入账号"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock"
                                  v-model="user.password" show-password></el-input>
                    </el-form-item>
                    <el-form-item style="margin: 10px 0; text-align:right">
                        <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
                        <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">
                            注册
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    import {login} from "../../api";

    export default {
        data() {
            return {
                user: {},
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                },
                account: "",
            };
        },
        methods: {
            async login() {
                let data = await login(this.user);
                let token = data.token;
                console.log(token);
                // 本地  vuex
                this.$store.commit("LOGIN_IN", token);
                localStorage.setItem("user", JSON.stringify(data));//存储用户信息到浏览器
                this.$store.commit("SET_USER", JSON.stringify(data));
                this.$router.replace("/");
            }
        },
        mounted() {
        },
    };
</script>

<style scoped lang="scss">
    .title {
        text-align: center;
        font-size: 35px;
        padding-top: 100px;
        color: #fff;
        font-family: 华文楷体;
    }

    #login_wrap {
        /* background: url("../../assets/login_background.png"); */
        background-image: linear-gradient(to bottom right, #87CEFA, #3F5EFB);
        background-size: 100% 100%;
        position: fixed;
        height: 100%;
        width: 100%;
        overflow: auto;

        > div {
            background: rgba(95, 107, 158, 0.86);
            width: 600px;
            height: 500px;
            position: absolute;
            left: calc(50vw - 300px);
            top: calc(50vh - 250px);
            border-radius: 10px;
        }

        .login {
            border: none;
            height: 400px;
            width: 500px;
            padding-top: 50px;
            padding-left: 100px;
        }
    }
</style>
<!--100vh;100%窗口高度-->