<template>
    <div class="login_container">

        <el-carousel :interval="4000" type="card" height="350px">
            <el-carousel-item v-for="item in 6" :key="item">
                <img class="avImg" :src="imgSrc[item-1]"  alt="示例图片">
            </el-carousel-item>
        </el-carousel>

        <div class="login_box">
            <!-- 头像 -->
            <div class="avatar_box">
                <img  src="../assets/pic/dragon.gif" alt="头像">
            </div>

            <!-- 登录表单 -->
            <div class="loginform">
                <el-form :model="loginForm" 
                ref="loginFormRef"
                label-width="0px"
                :rules="rules">
                    <!-- 用户名 -->
                    <el-form-item prop="username">
                        <el-input placeholder="请输入用户名"
                        prefix-icon="el-icon-user-solid"
                        v-model="loginForm.username"
                        clearable></el-input>
                    </el-form-item>

                    <!-- 密码 -->
                    <el-form-item prop="password">
                        <el-input placeholder="请输入密码"
                        prefix-icon="el-icon-lock"
                        v-model="loginForm.password" 
                        show-password
                        clearable></el-input>
                    </el-form-item>

                    <!-- 提交按钮 -->
                    <el-form-item class="btns">
                        
                        <el-button type="primary" @click="onSubmit" class="btns2">马上开始</el-button>
                        <el-button type="info" @click="onSubmit2">新用户</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>

    </div>
</template>

<script>
import axios from 'axios';
// import img1 from "../assets/pic/loginbgp.jpg"
// import img2 from "../assets/pic/cd-background-1.jpg"
// import img3 from "../assets/pic/cd-background-2.jpg"
// import img4 from "../assets/pic/cd-background-3.jpg"
// import img5 from "../assets/pic/cd-background-4.jpg"
// import img6 from "../assets/pic/cdbg1.jpg"
export default {
    data() {
        return {
            loginForm: {
                // 登录表单数据绑定对象
                username: '',
                password: '',
            },
            imgSrc: [
            require("../assets/pic/pexels-miguel-á-padriñán-3520694.jpg"),
            require("../assets/pic/pexels-andrea-piacquadio-842548.jpg"),
            require("../assets/pic/pexels-markus-spiske-2004161.jpg"),
            require("../assets/pic/pexels-fauxels-3183153.jpg"),
            require("../assets/pic/pexels-pixabay-265087.jpg"),
            require("../assets/pic/cdbg1.jpg")],
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
                ],
            }
        }
    },
    mounted(){
        
    },
    methods: {
        
        onSubmit(){
            this.$refs.loginFormRef.validate((valid)=>{
            if(!valid) return;
            let that=this;
            const loading = this.$loading({
                lock: true,
                text: '正在努力加载数据',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            const result = axios.post('/queryUserByName',
                this.$qs.stringify({
                    username:that.loginForm.username,
                    userpassword:that.loginForm.password
                })).then(function(resp){
                    console.log(resp.data)
                    if(resp.data.state) {
                //console.log(that.loginForm.username)
                    that.$message({
                    showClose: true,
                    message: '登录成功！',
                    center: true,
                    type: 'success'
                    });
                    // window.sessionStorage.setItem('token',"testtoken");
                    // window.sessionStorage.setItem('token',resp.data.token);
                    // that.$store.commit("setToken",resp.data.token);
                    // that.$store.commit("setuserID",resp.data.userID);
                    // that.$store.commit("setShowHint",true);
                    localStorage.setItem('userID',resp.data.userID)
                    that.$router.push('/homepage')
                    localStorage.setItem('clientHeight',document.documentElement.clientHeight - 110)
                    loading.close();
                }
                else{
                    that.$message({
                    showClose: true,
                    message: '登录失败！密码错误或账号不存在',
                    center: true,
                    type: 'error'
                    });
                    loading.close();
                }
                })
            .catch(function (error){
            console.log(error)
            })
            })

            //    window.sessionStorage.setItem('token',"testtoken");
            //    this.$router.push('/homepage')
           // });
        },

        onSubmit2(){
            this.$router.push('/register')
        }

    }
}
</script>

<style lang="less" scoped>
    .login_container {
        //background-image: url("../assets/pic/loginbgp.jpg");
        background-repeat: no-repeat;
        background-position: center top;
        background-attachment: fixed;
        background-size: 120%, 100%;
        background-color: rgb(51, 51, 51);
        height: 100%;
        width: 100%;
    }
    .login_box {
        width: 450px;
        height: 250px;
        background-color: #fff;
        border-radius: 5%;
        position: absolute;
        top: 75%;
        left: 50%;
        transform: translate(-50%,-50%);
        box-shadow: 0 0 20px;
    }
    .avatar_box {
        height: 130px;
        width: 130px;
        border: 1px solid #333;
        border-radius: 100%;
        padding: 5px;
        box-shadow: 0 0 10px;
        img {
            width: 100%;
            height: 100%;
            border-radius: 100%;
            background-color: rgb(95, 95, 102);
        }
        position: absolute;
        // top: 10%;
        left: -15.6%;
        // transform: translate(50%,50%);
        margin-top: 55px;
        // margin-left: 25px;
        background-color: rgb(141, 140, 140);
        float: left;
        display: block;
    }
    .loginform {
        // margin-top: 82px;
        width: 300px;
        margin-top: 55px;
        margin-right: 30px;
        box-sizing: border-box;
        float: right;
        display: block;
    }
    .btns {
        display: flex;
        justify-content: flex-end;
        margin-right: 30px;
    }

    .btns2 {
        margin-right: 15px;
    }

    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 200px;
        margin: 0;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }

    .avImg {
        width: 100%;
        height: 100%;
    }


</style>