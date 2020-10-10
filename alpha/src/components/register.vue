//? 用户名username 密码password 邮箱mail
<template>
    <div id="app">
        <div class="container">
            <div class="banner">
                <el-page-header class="pageHeader" @back="goBack" content="注册页面">
                </el-page-header>
            </div>

            <div class="regContainer">
                <div class="title">
                        <div class="lt">用户名</div>
                        <div class="lt">密码</div>
                        <div class="lt">邮箱</div>
                </div>
                <div class="regForm">
                    <el-form :model="regForm" 
                    ref="regFormRef"
                    label-width="0px">
                    <!-- 用户名 -->
                    <el-form-item>
                        <el-input placeholder="请输入用户名"
                        prefix-icon="el-icon-user-solid"
                        v-model="regForm.username"
                        clearable></el-input>
                    </el-form-item>

                    <!-- 密码 -->
                    <el-form-item>
                        <el-input placeholder="请输入密码"
                        prefix-icon="el-icon-lock"
                        v-model="regForm.password" 
                        show-password
                        clearable></el-input>
                    </el-form-item>

                    <!-- 邮箱 -->
                        <el-input placeholder="请输入邮箱"
                        prefix-icon="el-icon-message"
                        v-model="regForm.mail" 
                        clearable></el-input>
                    </el-form-item>

                    <!-- 提交按钮 -->
                    <el-form-item class="btns">
                        <el-button  @click="onSubmit" >完成注册</el-button>
                    </el-form-item>
                    </el-form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: 'app',
    data(){
        return{
            regForm: {
                username: '',
                password: '',
                mail: '',
            },
        }
    },
    methods:{
    goBack(){
        this.$router.push('/login')
    },
    onSubmit(){
        //TODO axios.get().then(function({
       //TODO }))
    }
    }

}
</script>

<style lang="less" scoped>
.container {
    background-color: #333;
    height: 100%;
}
.banner {
    height: 75px;
    width: 100%;
    background-color: rgba(255, 255, 255, 0.6);
    box-shadow:  0 0 30px ;
}
.pageHeader {
    padding: 25px;
}

.regContainer {
    width: 700px;
    height: 400px;
    margin: auto;
    margin-top: 100px;
    // margin-bottom: 100px;
    background-color: #fff;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 30px;
    border-top: 10px solid rgb(243, 209, 179);
    background-color: rgba(255, 255, 255, 0.95);
}
.regForm {
    width: 400px;
    height: 200px;
    margin-right: 100px;
    margin-top: 70px;
    display: block;
    float: right;
    // background-color: rgba(255, 255, 255, 0.6);
}
.btns {
    margin-left: 110px;
    margin-top: 50px;
}
.title {
    display: block;
    float: left;
    margin-top: 60px;
    margin-left: 100px;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
}
.lt {
    padding: 21px;
}

</style>