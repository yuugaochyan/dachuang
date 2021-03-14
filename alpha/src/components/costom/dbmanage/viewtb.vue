<template>
    <div id="app" v-if="reset">
        <div  class="container">
            <div class="left">
                
                <transition name="el-zoom-in-top" >
                    <div class="view-msg" v-if=showinfo>
                        <!-- <el-divider direction="vertical"></el-divider> -->
                        <h2>你的可视化：</h2><br>
                        <span class="view-t1">{{tbData.Graph.graphName}}</span>
                        <el-divider ></el-divider>
                        <!-- <br> -->
                        <!-- <el-divider direction="vertical"></el-divider> -->
                        <h3>可视化类型：</h3><br>
                        <span class="view-t2">{{tbData.type}}</span>
                        <el-divider ></el-divider>
                        <!-- <br> -->
                        <!-- <el-divider direction="vertical"></el-divider> -->
                        <h3>数据源：</h3><br>
                        <span class="view-t2">{{dataSource}}</span>
                        <el-divider ></el-divider>
                    </div>
                </transition>
                

            <div class="bt-next">
                <el-button type="warning"  @click="goback">返回列表</el-button>
            </div>


            
            </div>
            <div class="right">
                <chart v-if="tbData.type=='chart'" :id="tbID" :obdata="tbData.Graph"></chart>
                <mqttline v-if="tbData.type=='mqttline'" :id="tbID" :obdata="tbData.Graph"></mqttline>
                <sqltb v-if="tbData.type=='table'" :id="tbID" :obdata="tbData.Graph"></sqltb>
                <mqttnum v-if="tbData.type=='mqttnum'" :id="tbID" :obdata="tbData.Graph"></mqttnum>
            </div>
            
            
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import mtqqtest from '@/components/factory/mqttfac/mtqqtest'
import chart from '@/components/costom/chart'
import sqltb from '@/components/costom/sqltb'
import mqttline from '@/components/costom/mqttline'
import mqttnum from '@/components/costom/mqttnum'
export default {
    name:"viewtb",
    data() {
        return {
            tbData:{},
            tbID:'',
            tbName:'',
            reset:false,
            showinfo:false,
            dataSource:''
        }
    },
    components: {
        chart,
        mqttline,
        sqltb,
        mqttnum
    },
    methods: {
        getTbData() {
            let that = this;
            this.tbID = this.$route.params.tbID
            this.tbName = this.$route.params.tbName
            let postData=this.$qs.stringify({
                graphID:that.tbID,
            })
            const result = axios({
                method: 'post',
                url:'/getGraphInfo',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data
                // console.log(that.tbData);
                if(that.tbData.type=='mqttline'||that.tbData.type=='mqttnum') {
                    that.dataSource=that.tbData.Graph.tagName
                }
                else if(that.tbData.type=='table') {
                    that.dataSource=that.tbData.Graph.tableName
                }
                else {
                    that.dataSource=that.tbData.Graph.dataSource
                }
                }
            })
            setTimeout(()=>{
                this.showinfo=true
            },500)
        },
        goback() {
            this.$router.push('/createdb')
        }
    
    },
    created() {
        const loading = this.$loading({
            lock: true,
            text: '拼命加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        });
        this.getTbData();
        setTimeout(() => {
                loading.close();
        }, 1000);
    },
    watch: {
        tbData: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        }
    }
}
</script>

<style lang="less" scope>
.container {
    display: flex;
    height: calc(93.6vh);
    width: 100%;
}
.left {
    flex:1;
    text-align: left;
    padding: 20px;
    // color: #fff;
    background-color: #fff;
    // height: 100%;
    position: relative;
}
.right {
    flex:3;
    padding: 100px;
    background-color: #333;
}
.bt-step {
    position: absolute;
    // top: 80%;
    left: 0%;
    bottom: 5%;
    width: 100%;
}
.bt-next {
    position: absolute;
    bottom: 25%;
    right: 10%;
}
.view-msg {
    >h2 {
        color: rgb(117, 117, 117);
        border-left: solid 2px rgb(117, 117, 117);
        padding-left: 5px;
        // display: inline-block;
    }
    >h3 {
        color: rgb(117, 117, 117);
        border-left: solid 2px rgb(117, 117, 117);
        padding-left: 3px;
        // display: inline-block;
    }
    >.view-t1 {
        font-size: 30px;
        // padding-left: 35px;
    }
    >.view-t2 {
        font-size: 25px;
        // padding-left: 35px;
    }
}
</style>