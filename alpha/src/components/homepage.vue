<template>
    <div id="app" v-if="reset">

    <el-container>
    <el-header>
    <el-menu
    :default-active='$route.path'
    id="nav"
    class="el-menu-demo nav"
    mode="horizontal"
    @select="handleSelect"
    background-color="#333"
    text-color="#fff"
    active-text-color="#ffd04b"
    router>
        <el-menu-item class="iconfont icon-zhuye" index="/welcome">主页</el-menu-item>
        <el-menu-item class="iconfont icon-keshihua" index="/mainfac">示例数据</el-menu-item>
        <!-- <el-menu-item index="/coshome"  -->
        <!-- :disabled="isshow" -->
        <!-- >自定义大屏</el-menu-item> -->
        <!-- @click.native="judgetoken" -->
        <el-submenu index="/coshome">
            <template slot="title"><i class="iconfont icon-zidingyi"></i>大屏配置</template>
            <el-menu-item index="/createdb">仪表盘管理</el-menu-item>
            <el-menu-item index="/tbmanage">可视化管理</el-menu-item>
        </el-submenu>

        <el-submenu index="/homepage">
            <template slot="title"><i class="iconfont icon-zidingyi"></i>数据管理</template>
            <el-menu-item index="/dynamicdata">实时数据</el-menu-item>
            <el-submenu index="/datamanage">
                <template slot="title"><i class="iconfont icon-zidingyi"></i>历史数据</template>
                <el-menu-item 
                    v-for="item in staticList"
                    :key=item.index
                    :index="item.index">{{item.naviName}}</el-menu-item>
            </el-submenu>
            
        </el-submenu>

        <el-submenu index="/viewdb">
            <template slot="title"><i class="iconfont icon-zidingyi"></i>仪表盘</template>
            <el-menu-item 
                v-for="item in dbList"
                :key=item.index
                :index="item.index">{{item.naviName}}</el-menu-item>
            
        </el-submenu>
        <!-- <el-menu-item index="/aboutus" class="iconfont icon-guanyuwomen">关于我们</el-menu-item> -->
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
            staticList:[],
            reset:false,
            dbList:[],
        };
    },
    
    methods: {
        // judgetoken(){
            // 
        // },
        changeisshow() {
            const tokenstr=localStorage.getItem('token');
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
            // console.log(key, keyPath);
            
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
            // localStorage.setItem('token','')
            // this.$store.commit('setToken','');
            // this.changeisshow();
            // this.reload()
            this.$router.push('/login')
        },
        getStaticList() {
            const userID=localStorage.getItem("userID")
            let that = this
            let postData=this.$qs.stringify({
                userID:userID,
            })
            const result = axios({
                method: 'post',
                url:'/getNaviList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                    that.staticList=resp.data.tableNavi
                    that.dbList=resp.data.dbNavi
                }
            })
        },
        
    },
    mounted() {
        this.getStaticList()
        // this.getDbList()
        this.changeisshow();
    },
    watch: {
        staticList: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        },
        $route(to,from) {
            this.reload()
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
    z-index: 10;
    // position: absolute;
    // top: 0px;
    width: 100%;
    box-shadow: 0 0 20px;
    // overflow: hidden;

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

.el-header {
    height: 75px;
}

</style>