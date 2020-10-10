<template>
    <div id="app" >
    <el-menu
    :default-active="activeIndex2"
    class="el-menu-demo"
    mode="horizontal"
    @select="handleSelect"
    background-color="#333"
    text-color="#fff"
    active-text-color="#ffd04b">
        <el-menu-item index="1">处理中心</el-menu-item>
        <el-submenu index="2">
            <template slot="title">我的工作台</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
            <el-menu-item index="2-3">选项3</el-menu-item>
            <el-submenu index="2-4">
                <template slot="title">选项4</template>
                <el-menu-item index="2-4-1">选项1</el-menu-item>
                <el-menu-item index="2-4-2">选项2</el-menu-item>
                <el-menu-item index="2-4-3">选项3</el-menu-item>
            </el-submenu>
        </el-submenu>
        <el-menu-item index="3">消息中心</el-menu-item>
        <el-menu-item index="4">订单管理</el-menu-item>
        <el-button type="info" size="medium" icon="el-icon-user" class="bt" @click="turnToReg">注册</el-button>
        <el-button type="warning" size="medium" icon="el-icon-user-solid" class="bt" @click="turnToLogin">登录</el-button>
        
    </el-menu>


        <div class="fixedBg bgi0">
            <span>带标题</span>
        </div>
        <div class="text">
            <span>标题1</span>
            </div>
        <div class="fixedBg bgi1">
            <span>??</span>
        </div>
        <div class="text">
            <span>标题2</span>
        </div>
        <div class="fixedBg bgi2">
            <span>??</span>
        </div>
        <div class="text">
            <span>标题2</span>
        </div>
        <div class="fixedBg bgi3">
            <span>..</span>
        </div>
        <div class="text">
            <span>标题3</span>
        </div>
        
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            activeIndex: '1',
            activeIndex2: '1'
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        turnToLogin(){
            this.$router.push('/login')
        },
        turnToReg(){
            this.$router.push('/register')
        }
    }

}
</script>

<style lang="less" scoped>

.fixedBg { 
    min-height: 380px; 
    background-position: center center; 
    background-repeat: no-repeat; 
    background-attachment: fixed;
    background-size: cover;
    
    text-align: center;
}
.bgi0 {
    background-image: url("../assets/pic/cd-background-1.jpg");
}
.bgi1 {
    background-image: url("../assets/pic/cdbg1.jpg");
}
.bgi2 {
    background-image: url("../assets/pic/cd-background-2.jpg");
}
.bgi3 {
    background-image: url("../assets/pic/cd-background-3.jpg");
}
.text {
    background: #333;
    width: 100%;
    height: 500px;
    text-align: center;
    
}
span {
    color: #fff;
    font-size: 100px;
    line-height: 500px;
    
}
.bt {
    float: right;
    margin-right: 10px;
    margin-top: 11px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 30px;

}

</style>