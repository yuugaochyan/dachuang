<template>
    <div id="app" >

    <el-container>
    <el-header>
    <el-menu
    :default-active='$route.path'
    
    class="el-menu-demo nav"
    mode="horizontal"
    @select="handleSelect"
    background-color="#333"
    text-color="#fff"
    active-text-color="#ffd04b"
    router>
        <el-menu-item class="iconfont icon-zhuye" index="/welcome">主页</el-menu-item>
        <el-menu-item class="iconfont icon-keshihua" index="/mainfac">大屏数据</el-menu-item>
        <!-- <el-menu-item index="/coshome"  -->
        <!-- :disabled="isshow" -->
        <!-- >自定义大屏</el-menu-item> -->
        <!-- @click.native="judgetoken" -->
        <el-menu-item index="/coshome" class="iconfont icon-zidingyi">
            自定义大屏
        </el-menu-item>
        <el-menu-item index="/aboutus" class="iconfont icon-guanyuwomen">关于我们</el-menu-item>
        <el-button type="info" size="medium" icon="el-icon-user" class="bt" @click="turnToReg" v-if="isshow">注册</el-button>
        <el-button type="info" size="medium" icon="iconfont icon-tuichu" class="bt" @click="logout" v-if="!isshow">退出</el-button>
        <el-button type="warning" size="medium" icon="el-icon-user-solid" class="bt" @click="turnToLogin" v-if="isshow">登录</el-button>
        
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
    inject:['reload'],
    data() {
        return {
            // activeIndex: this.index,
            isshow:true,
        };
    },
    mounted() {
        this.changeisshow();
    },
    methods: {
        // judgetoken(){
            // 
        // },
        changeisshow() {
            const tokenstr=this.$store.state.token;
            // const tokenstr="testtoken"
            if(tokenstr){
                this.isshow=false;
                console.log(tokenstr);
            }
            else{
                this.isshow=true;
                console.log(tokenstr);
            }
        },
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
            console.log(key);
        },
        turnToLogin(){
            this.$router.push('/login')
        },
        turnToReg(){
            this.$router.push('/register')
        },
        logout(){
            window.localStorage.clear();
            // this.$store.commit('setuserInfo','');
            this.$store.commit('setToken','');
            this.changeisshow();
            // this.reload()
            // this.$router.push('/homepage')
        }
    }

}
</script>

<style lang="less" scoped>
.iconfont {
    font-size: 15px;
}

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