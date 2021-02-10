<template>
    <div id="app" v-if="reset">
        <div  class="container">
            <div class="left">
                
                <transition name="el-zoom-in-top" >
                    <div class="msg" v-if=showinfo>
                        <h1>你的可视化：{{tbName}}</h1>
                        <h3>可视化类型：{{tbData.type}}</h3>
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
            showinfo:false
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
                tbID:that.tbID,
            })
            const result = axios({
                method: 'post',
                url:'/getGraphInfo',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data
                console.log(that.tbData);
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
        this.getTbData();
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

</style>