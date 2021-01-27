<template>
<div id="app" v-if="reset">
    <div class="container">
        <div class="nav">
        <el-button size="small" type="warning" icon="iconfont icon-more" circle @click="isC" class="bt"></el-button>
            <!-- <i class="iconfont icon-preview bt" @click="isC" ></i> -->
            <el-menu 
            :default-active='$route.path'
            class="el-menu-vertical"  
            :collapse="isCollapse"
            background-color="#333"
            text-color="#fff"
            active-text-color="#ffd04b"
            router>
                <el-menu-item index="/createdb">
                    <i class="iconfont icon-daping"></i>
                    <span slot="title">仪表盘</span>
                </el-menu-item>
                <el-menu-item index="/createtb">
                    <i class="iconfont icon-xinjian"></i>
                    <span slot="title">问题可视化</span>
                </el-menu-item>

                <el-menu-item index="/griddemo">
                    <i class="el-icon-menu"></i>
                    <span slot="title">导航二</span>
                </el-menu-item>
            </el-menu>
        </div>
    
        <div class="main">
            
                <router-view></router-view>
            
            
        </div>
    </div>
    
</div>
</template>

<script>
import axios from 'axios';
export default {
    name:"coshome",
    data() {
        return {
            isCollapse: true,
            reset: true,
            userID:'',
            userName:'',
            
        }
    },
    methods: {
        goback(){
            this.$router.push('/homepage')
        },
        isC(){
            this.isCollapse = !this.isCollapse
		}
    },
    beforeMount(){
        
    },
    mounted() {

    },
    watch: {
        
    }
    
}
</script>

<style lang="less" scoped>
.bt {
    margin-top: 15px;
    margin-left: 10px;
}

.container {
    height: 100%;
    width: 100%;
    background-color: #333;
}

.iconfont {
    font-size: 17px;
}


.el-menu-vertical {
    border-right: 0;
    height: 100%;
}
.el-menu-vertical:not(.el-menu--collapse) {
    width: 150px;
    border-right: 0;
    background-color: #333;
    height: 100%;
}
.nav {
    // float: left;
    display: inline-block;
    background-color: #333;
    height: 100%;
    vertical-align:top;
    // text-align: center;
}
.main {
    // float: left;
    display: inline-block;
    height: 100%;
    width: calc(100% - 151px);
    vertical-align:top;
}
.iconfont {
    margin-right: 10px;
}

</style>