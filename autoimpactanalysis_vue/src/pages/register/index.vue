<template>
    <div id="register_wrap">
        <div>
            <h2 class="title">数据工作支持系统</h2>
            <div class="register">
                <el-form :model="user" :rules="rules" ref="userForm">
                    <el-form-item prop="userName">
                        <el-input placeholder="请输入账号" size="medium" style="margin: 10px 0;" prefix-icon="el-icon-user"
                                  v-model="user.userName"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock"
                                  v-model="user.password" show-password></el-input>
                    </el-form-item>
                    <el-form-item prop="confirmPassword">
                        <el-input placeholder="请确认密码" size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock"
                                  v-model="user.confirmPassword" show-password></el-input>
                    </el-form-item>
                    <el-form-item style="margin: 10px 0; text-align: right">
                        <el-button type="primary" size="small" autocomplete="off" @click="register">注册</el-button>
                        <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "../../utils/http";

    export default {
        name: "Register",
        data() {
            return {
                user: {},
                rules: {
                    userName: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    confirmPassword: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            register() {
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {//表单校验合法
                        if (this.user.password !== this.user.confirmPassword) {
                            this.$message.success("两次输入的密码不一致");
                            return false
                        }
                        axios.post("/user/register/", this.user).then(res => {
                            this.$router.push("/login");
                            this.$message.success("注册成功");
                        })
                    }
                });
            },
        }
    }
</script>

<style scoped lang="scss">
    .title {
        text-align: center;
        font-size: 35px;
        padding-top: 50px;
        color: #fff;
        font-family: 华文楷体;
    }
    #register_wrap {
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
    }

    .register {
        border: none;
        height: 400px;
        width: 500px;
        padding-top: 50px;
        padding-left: 100px;
    }
</style>
<!--100vh;100%窗口高度-->
