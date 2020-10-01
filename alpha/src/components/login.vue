<template>
    <div class="login_container">
        <div class="login_box">
            <!-- 头像 -->
            <div class="avatar_box">
                <img src="../assets/logo.png" alt="头像">
            </div>

            <!-- 登录表单 -->
            <div class="loginform">
                <el-form :model="loginForm" 
                ref="loginFormRef"
                label-width="0px">
                    <!-- 用户名 -->
                    <el-form-item>
                        <el-input placeholder="请输入用户名"
                        prefix-icon="el-icon-user-solid"
                        v-model="loginForm.username"
                        clearable></el-input>
                    </el-form-item>

                    <!-- 密码 -->
                    <el-form-item>
                        <el-input placeholder="请输入密码"
                        prefix-icon="el-icon-goods"
                        v-model="loginForm.password" 
                        show-password
                        clearable></el-input>
                    </el-form-item>

                    <!-- 提交按钮 -->
                    <el-form-item class="btns">
                        <el-button type="primary" @click="onSubmit">马上开始</el-button>
                        <el-button type="info">新用户</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            loginForm: {
                // 登录表单数据绑定对象
                username: '',
                password: '',
            }
        }
    },
    methods: {
        onSubmit(){
            this.$refs.loginFormRef.validate(( /* async */ valid)=>{
                if(!valid) return;
                // const {data:res } = await this.$http.post('login',this.loginForm);
                // this.$message.sucess("登陆成功");

                // window.sessionStorage.setItem('token', res.data.token)
                this.$router.push('/home')
            });
        }

    }
}
</script>

<style lang="less" scoped>
    .login_container {
        background-image: url("../assets/pic/loginbgp.jpg");
        background-repeat: no-repeat;
        background-position: center top;
        background-attachment: fixed;
        background-size: 120%, 100%;
        background-color: rgb(0, 0, 0);
        height: 100%;
        width: 100%;
    }
    .login_box {
        width: 450px;
        height: 300px;
        background-color: #fff;
        border-radius: 5%;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        box-shadow: 0 0 20px;
    }
    .avatar_box {
        height: 130px;
        width: 130px;
        border: 1px solid skyblue;
        border-radius: 100%;
        padding: 10px;
        box-shadow: 0 0 10px;
        img {
            width: 100%;
            height: 100%;
            border-radius: 100%;
            background-color: rgb(95, 95, 102);
        }
        position: absolute;
        left: 50%;
        transform: translate(-50%,-50%);
        background-color: rgb(165, 207, 247);
    }
    .loginform {
        margin-top: 82px;
        padding: 30px;
        box-sizing: border-box;
    }
    .btns {
        display: flex;
        justify-content: flex-end;
    }
</style>