<template>
    <div id="app" v-if="reset">
        <div class="maincontain" @dblclick="drawer = true">
            

                <!-- <el-tooltip class="item" effect="dark" content="隐藏调整按钮" placement="bottom-start"> -->
                    <!-- <el-button @click="drawer = true" circle type="warning" icon="iconfont icon-preview" style="margin-left: 16px;"></el-button> -->
                <!-- </el-tooltip> -->

                <griddemo :dbID="dbID" :editable=false></griddemo>
                
                
                
                <el-drawer
                    
                    :visible.sync="drawer"
                    :direction="direction"
                    size=180>
                    <span slot="title" class="tool-banner"><i class="el-icon-s-tools"></i>工具箱</span>
                    <div class="toolbar">
                        <el-button @click="asideResize" type="warning"  plain>适应调整</el-button>
                        <br>
                        <el-divider></el-divider>
                        <el-button @click="gotoedit" type="warning"  plain>前往编辑</el-button>
                        <br>
                        <el-divider></el-divider>
                        <el-button @click="goback" type="warning"  plain>返回列表</el-button>
                    </div>
                </el-drawer>

        </div>
    </div>
</template>

<script>
import axios from 'axios'
import mtqqtest from '@/components/factory/mqttfac/mtqqtest'
import griddemo from '@/components/costom/griddemo'
export default {
    components: { griddemo },
    name:"viewdb",
    data() {
        return {
            dbData:[],
            dbID:'',
            dbName:'',
            dbInfo:'',
            reset:false,
            drawer:false,
            direction: 'rtl',
        }
    },
    methods: {
        getDbData() {
            let that = this;
            this.dbID = this.$route.params.dbID
            this.dbName = this.$route.params.dbName
            this.dbInfo = this.$route.params.dbInfo
            let postData=this.$qs.stringify({
                dbID:that.dbID,
            })
            const result = axios({
                method: 'post',
                url:'/getDBItemInfo',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.dbData=resp.data.data
                }
            })
            this.$message({
                    showClose: true,
                    message: '双击屏幕可打开工具箱,如遇数据显示不全请尝试刷新',
                    center: true,
                    type: 'info'
            });
        },
        asideResize(){
            let myEvent = new Event('resize'); // resize是指resize事件
            window.dispatchEvent(myEvent); // 触发window的resize事件
        },
        gotoedit() {
            this.$router.push({
                name:'editdb',
                params: {
                    dbID:this.dbID,
                    dbName:this.dbName,
                    dbInfo:this.dbInfo
                }
            })
        },
        goback() {
            this.$router.push('/createdb')
        }
    },
    mounted() {
        this.getDbData();
    },

    watch: {
        dbData: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        }
    }
}
</script>

<style lang="less" scope>
.maindb {
    width: 100%;
    height: calc(93.6vh);
    background-color: #333;
}
.tool-banner {
    // background-color: #ddbb80;
    font-size: 17px;
}
.toolbar {
    padding: 20px;
    background-color: #ddbb80;
    height: 100%;
}
.bt {
    margin-left: 50px;
}

.iconfont {
    // margin-right: 5px;
    font-size: 15px;
}
.el-drawer /deep/ :focus {
    outline: none;
}
.el-drawer:focus {
    outline: none;
}
</style>