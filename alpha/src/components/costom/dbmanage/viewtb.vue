<template>
    <div id="app" v-if="reset">
        <div  class="con">
            <div class="left">
                
                

            <div class="bt-next">
                <el-button type="warning"  @click="saveTB">保存修改</el-button>
            </div>


            
            </div>
            <div class="right">
                <chart v-if="tbData.type=='chart'" :id="tbData.i" :obdata="tbData.objectData"></chart>
                <mqttline v-if="tbData.type=='mqttline'" :id="tbData.i" :obdata="tbData.objectData"></mqttline>
                <sqltb v-if="tbData.type=='table'" :id="tbData.i" :obdata="tbData.objectData"></sqltb>
                <mqttnum v-if="tbData.type=='mqttnum'" :id="tbData.i" :obdata="tbData.objectData"></mqttnum>
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
            reset:false
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
            let postData=this.$qs.stringify({
                tbID:that.tbID,
            })
            const result = axios({
                method: 'post',
                url:'/getTBData',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data
                console.log(that.tbData);
                }
            })
        },
        saveTB() {
            console.log("sa");
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
.con {
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