//? 用户名-username 密码-password 邮箱-mail
<template>
    <div id="app">
        <div class="container">

            <div class="banner">
                <!-- <el-page-header class="pageHeader" @back="goBack" content="注册页面"> -->
                <!-- </el-page-header> -->
                <el-link class="back" :underline="false" icon="el-icon-back" type="warning" @click="goBack">返回登录</el-link>
                <span class="cut">|</span>
                <span class="title1">注册页面</span>



            </div>

            <div class="regContainer">
                <!-- <div class="title"> -->
                        <!-- <div class="lt">用户名</div> -->
                        <!-- <div class="lt">密码</div> -->
                        <!-- <div class="lt">邮箱</div> -->
                <!-- </div> -->
                <div class="regForm">
                    <el-form :model="regForm" 
                    ref="regFormRef"
                    label-width="80px"
                    :rules="rules">
                    <!-- 用户名 -->
                    <el-form-item label="用户名" prop="username">
                        <el-input placeholder="请输入用户名"
                        prefix-icon="el-icon-user-solid"
                        v-model="regForm.username"
                        clearable></el-input>
                    </el-form-item>

                    <!-- 密码 -->
                    <el-form-item label="密码" prop="password">
                        <el-input placeholder="请输入密码"
                        prefix-icon="el-icon-lock"
                        v-model="regForm.password" 
                        show-password
                        clearable></el-input>
                    </el-form-item>

                    <!-- 邮箱 -->
                    <el-form-item label="邮箱" prop="mail">
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

            <div class="footer">
                <img src="" alt="">
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'app',
  data() {
    return {
      regForm: {
        username: '',
        password: '',
        mail: '',
      },
      rules: {
        username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          mail: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ]
      }
    }
  },
  methods: {

    goBack() {
      this.$router.push('/login')
    },
    onSubmit() {
      this.$refs.regFormRef.validate((valid)=>{
        if(!valid) return;
        
      
      let that=this;
      const result = axios.post('http://localhost:8888/addUser',
          this.$qs.stringify({
        username: that.regForm.username,
        userpassword: that.regForm.password,
        useremail: that.regForm.mail,
      })).then(function (resp) {
        console.log(resp.data)
        if (resp.data) {
          that.$message({
            showClose: true,
            message: '注册成功！登录以开始',
            center: true,
            type: 'success'

          })}
        else{
          that.$message({
            showClose: true,
            message: '注册失败',
            center: true,
            type: 'error'
          })
        }

      })


        this.$router.push('/login')
        })
    },


        // this.$message({
            // showClose: true,
            // message: '注册成功！登录以开始',
            // center: true,
            // type: 'success'
        // });
        // this.$router.push('/login')
        //?showClose: true,
        //?   message: '错了哦，这是一条错误消息',
        //?   type: 'error',
        //?   center: true,
    }



}
</script>

<style lang="less" scoped>
.container {
    background-color: rgb(214, 214, 214);
    height: 100%;
}
.banner {
    height: 75px;
    width: 100%;
    background-color:rgba(51, 51, 51, 0.815);
    box-shadow:  0 0 20px ;
    line-height: 75px;
    background-image: url("../assets/pic/未标题-2.png");
    background-repeat: no-repeat;
    background-size: contain;
    background-position: 100px 10px;
}
.back {
    font-size: 25px;
    display: block;
    float: left;
    margin-left: 15px;
}
.cut {
    font-size: 30px;
    padding-left: 15px;
    padding-right: 15px;
    color: rgb(129, 129, 129);
    display: block;
    float: left;
}
.title1 {
    font-size: 30px;
    color: #fff;
    display: block;
    float: left;
}

// .pageHeader {
    // padding: 25px;
// }

.regContainer {
    width: 700px;
    height: 420px;
    margin: auto;
    margin-top: 100px;
    // margin-bottom: 100px;
    background-color: #fff;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 30px;
    border-top: 10px solid rgb(73, 73, 73);
    // border-bottom: 10px solid #333;
    background-color: rgba(255, 255, 255, 0.95);
    background-image: url("../assets/pic/koch.gif");
    background-repeat: no-repeat;
    background-position:40px 220px;
}
.regForm {
    width: 400px;
    height: 200px;
    margin: auto;
    margin-top: 70px;
    // display: block;
    // float: left;
    // background-color: rgba(255, 255, 255, 0.6);
}
.btns {
    margin-left: 75px;
    margin-top: 50px;
}



</style>