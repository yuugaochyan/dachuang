<template>
    <div id="app" >

    <el-container>
    <el-header>
    <el-menu
    :default-active="activeIndex"
    class="el-menu-demo nav"
    mode="horizontal"
    @select="handleSelect"
    background-color="#333"
    text-color="#fff"
    active-text-color="#ffd04b"
    router>
        <el-menu-item index="welcome">主页</el-menu-item>
        <el-menu-item index="intro">工业互联网</el-menu-item>
        <el-submenu index="2">
            <template slot="title">工厂数据</template>
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
        <el-menu-item index="aboutus" >关于我们</el-menu-item>
        <el-button type="info" size="medium" icon="el-icon-user" class="bt" @click="turnToReg" v-if="isShow">注册</el-button>
        <el-button type="info" size="medium" icon="el-icon-user" class="bt" @click="turnToLogin" v-if="!isShow">退出</el-button>
        <el-button type="warning" size="medium" icon="el-icon-user-solid" class="bt" @click="turnToLogin" v-if="isShow">登录</el-button>
        
    </el-menu>
    </el-header>

    <el-main class="main">
        <router-view></router-view>
    </el-main>
    </el-container>       
        
        

    </div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            activeIndex: 'welcome',
            isShow:true,
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
            console.log(key);
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


.nav {
    position: fixed;
    top: 0px;
    width: 100%;
    box-shadow: 0 0 20px;
}


.bt {
    float: right;
    margin-right: 10px;
    margin-top: 11px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 30px;

}

.el-header, .el-main {
    padding: 0px;
}

</style>