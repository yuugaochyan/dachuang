<template>
    <div id="app" v-if="reset">
        <div  class="container">
            <div class="left">
                
                <transition name="el-zoom-in-top" >
                    <div class="msg" v-if="showinfo">
                        <el-form :model="chartform" :rules="rules" ref="chartformref" :inline="true">
                            <el-form-item label="问题名字"  prop="graphName">
                                <el-input v-model="chartform.graphName" clearable placeholder="为这个可视化问题取个名字吧"></el-input>
                            </el-form-item>
                            <br>
                            <el-form-item label="图表类型"  prop="graphType" 
                            v-if="tbData.type=='chart'||tbData.type=='mqttline'">
                                <el-radio v-model="chartform.graphType" label="bar">柱状图</el-radio>
                                <el-radio v-model="chartform.graphType" label="line">折线图</el-radio>
                                <el-radio v-model="chartform.graphType" label="pie">饼图</el-radio>
                                <el-radio v-model="chartform.graphType" label="scatter">散点图</el-radio>
                            </el-form-item>
                            <br>
                            <el-form-item label="数据来源"  prop="dataSource"
                            v-if="tbData.type=='chart'||tbData.type=='table'">
                            <el-select v-model="chartform.dataSource" placeholder="请选择数据源"
                            @change=getcolList>
                                <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
                            </el-form-item>
                            <el-form-item label="数据来源"  prop="dataSource"
                            v-if="tbData.type=='mqttnum'||tbData.type=='mqttline'">
                            <el-select v-model="chartform.dataSource" placeholder="请选择数据源"
                            @change=getcolList>
                                <el-option
                                v-for="item in options2"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
                            </el-form-item>
                        </el-form>
                    </div>
                </transition>
                <transition name="el-zoom-in-top">
                    <div v-show="showinfo2">
                    <el-form :model="Chart" :rules="rules2" ref="chartformref2" :inline="true">
                    <el-form-item label="x轴数据"  prop="xArraySource" >
                        <el-select v-model="Chart.xArraySource" 
                        placeholder="请选择x轴数据"
                        >
                            <el-option
                            v-for="opx in colList"
                            :key="opx"
                            :label="opx"
                            :value="opx">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="y轴数据"  prop="yArraySource" >
                        <el-select v-model="Chart.yArraySource" 
                        placeholder="请选择y轴数据"
                        >
                            <el-option
                            v-for="opy in colList"
                            :key="opy"
                            :label="opy"
                            :value="opy">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="y轴数据名" prop="name" >
                        <el-input v-model="Chart.name"></el-input>
                    </el-form-item>
                    </el-form>
                    </div>
                </transition>

            <div class="tb-bt-next">
                <el-button type="info"  @click="changeX">预览修改</el-button>
                <el-button type="warning"  @click="goback">保存修改</el-button>
            </div>


            
            </div>
            <div class="tb-right">
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
            rules: {
                graphName: [
                    { required: true, message: '请输入问题的名字！', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
                graphType: [
                    { required: true, message: '请选择图表类型！', trigger: 'blur' },
                ],
                dataSource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
            },
            rules2: {
                xArraySource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
                yArraySource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
                name: [
                    { required: true, message: '请输入该数据的名字！', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
            },
            xData:[],
            yData:[],
            tbData:{},
            tbID:'',
            tbName:'',
            reset:false,
            showinfo:false,
            showinfo2:false,
            chartform:{
                graphName:'',
                graphType:'',
                dataSource:'',
            },
            Chart:{
                xArraySource:'',
                yArraySource:'',
                name:'',
                yNum:1,
                yArraySource2:'',
                yArraySource3:'',
                name2:'',
                name3:''
            },
            options: [{
                value: 'equipmentrepair',
                label: 'equipmentrepair'
                }, {
                value: 'eqpseasonstatistic',
                label: 'eqpseasonstatistic'
            }],
            options2: [{
                value: 'equipmentrepair',
                label: 'equipmentrepair'
                }, {
                value: 'eqpseasonstatistic',
                label: 'eqpseasonstatistic'
            }],
            colList:[],
            tableData:{},
            
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
            this.chartform.graphName = this.$route.params.tbName
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
                // console.log(that.tbData);
                that.chartform.graphType=resp.data.data.Graph.graphType
                that.chartform.dataSource=resp.data.data.Graph.dataSource
                that.chartform.graphName=resp.data.data.Graph.graphName
                that.Chart.name=resp.data.data.Graph.legend[0]
                }
            })
            setTimeout(()=>{
                this.showinfo=true
            },500)
        },
        goback() {
            this.$refs.chartformref.validate((valid)=>{
                if(!valid) return;
                this.$router.push('/createdb')
            })
        },
        getcolList() {
            let that=this
            let postDta=this.$qs.stringify({
                dataSource:that.chartform.dataSource
            })
            // console.log(that.chartform.dataSource);
            this.$axios.post("/getTableData",postDta)
            .then((resp)=>{
                // console.log(resp)
                that.colList=resp.data.data.colList,
                that.tableData=resp.data.data.tableData
                that.showinfo2=true
                
            })
            
        },
        changeX() {
            // console.log(this.tbData.Graph);
            let that = this;
            
            for(let key in this.tableData) {
                if(key==that.Chart.xArraySource) {
                    that.xData=this.tableData[key]
                }
                if(key==that.Chart.yArraySource) {
                    that.yData=this.tableData[key]
                }
            }
            // console.log(this.xData);
            // console.log(this.yData);
            this.tbData.Graph.graphType=this.chartform.graphType
            this.tbData.Graph.series[0].type=this.chartform.graphType
            this.tbData.Graph.series[0].name=this.Chart.name
            this.tbData.Graph.series[0].data=this.yData
            this.tbData.Graph.xarray=this.xData
            // console.log(this.tbData.Graph);
        },
        
    
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
.tb-right {
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
.tb-bt-next {
    position: absolute;
    bottom: 5%;
    right: 10%;
}

</style>