<template>
        
        <div  class="con">
            <div class="left">
                
                <transition name="el-fade-in" >
                <el-form :model="chartform" v-show="step1" :rules="rules" ref="chartformref" :inline="true">
                    <el-form-item label="问题名字"  prop="graphName">
                        <el-input v-model="chartform.graphName" clearable placeholder="为这个可视化问题取个名字吧"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item label="图表类型"  prop="graphType">
                        <el-radio v-model="chartform.graphType" label="bar">柱状图</el-radio>
                        <el-radio v-model="chartform.graphType" label="line">折线图</el-radio>
                        <el-radio v-model="chartform.graphType" label="scatter">散点图</el-radio>
                    </el-form-item>
                    <el-form-item label="数据来源"  prop="dataSource">
                    <el-select v-model="chartform.dataSource" placeholder="请选择数据源" >
                        <el-option
                        v-for="item in tagList"
                        :key="item.tagID"
                        :label="item.tagName"
                        :value="item.tag">
                        </el-option>
                    </el-select>
                    </el-form-item>
                    
                </el-form>
                </transition>

                <transition name="el-fade-in" >
                    <el-form :model="chartform" 
                    v-show="step2" 
                    :rules="rules2" ref="chartformref2" :inline="true">
                    
                    <el-form-item label="数据名" prop="name">
                        <el-input v-model="chartform.name" ></el-input>
                    </el-form-item>
                    <el-form-item label="同屏最大数" prop="name">
                        <el-input v-model="chartform.lengs" ></el-input>
                    </el-form-item>
                    <el-form-item label="最小值" prop="name">
                        <el-input v-model="chartform.min" ></el-input>
                    </el-form-item>
                    <el-form-item label="最大值" prop="name">
                        <el-input v-model="chartform.max" ></el-input>
                    </el-form-item>
                    <!-- <el-form-item label="预览生成图表"> -->
                        <!-- <el-button @click="drawline('bar')"></el-button> -->
                    <!-- </el-form-item> -->
                    </el-form>
                </transition>

                <!-- <transition name="el-fade-in" > -->
                <!-- <el-form :model="dbform" v-show="step3" :rules="rules3" ref="chartformref3"> -->
                    <!-- <el-form-item label="你的仪表盘"  prop="db"> -->
                    <!-- <el-select v-model="dbform.db" placeholder="请选择仪表盘"> -->
                        <!-- <el-option -->
                        <!-- v-for="item in dbData" -->
                        <!-- :key="item.dbId" -->
                        <!-- :label="item.dbName" -->
                        <!-- :value="item.dbID"> -->
                        <!-- </el-option> -->
                    <!-- </el-select> -->
                    <!-- </el-form-item> -->
                <!-- </el-form> -->
                <!-- </transition> -->

            <div class="bt-next">
                <el-button type="info"  @click="laststep" >{{steplabel1}}</el-button>
                <el-button type="warning"  @click="nextstep">{{steplabel2}}</el-button>
            </div>


            <el-steps :active="active" finish-status="success" class="bt-step" align-center>
                <el-step title="第一步" description="命名这个问题并选择图表类型和数据源"></el-step>
                <el-step title="第二步" description="配置相关数据设置，保存修改"></el-step>
                <!-- <el-step title="第三步" description="进入仪表盘看看吧"></el-step> -->
            </el-steps>
            </div>
            <div class="right">
                <div id="chart" :style="{height: '100%',width: '100%'}"></div>
            </div>
            
            
        </div>
        
</template>

<script>
import axios from 'axios'
import mqtt from 'mqtt'

export default {
    name:'editmqttline',
    data(){
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
                
                name: [
                    { required: true, message: '请输入该数据的名字！', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
                lengs: [
                    { required: true, message: '请输入同屏最大数', trigger: 'blur' },
                ],
                max: [
                    { required: true, message: '请输入最大值', trigger: 'blur' },
                ],
                min: [
                    { required: true, message: '请输入最小值', trigger: 'blur' },
                ],
            },
            rules3: {
                db: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
            },
            tagList: [],
            active:0,
            steplabel1:"放弃编辑",
            steplabel2:"下一步",
            tableData:{},
            step1:false,
            step2:false,
            step3:false,
            chartform:{
                graphName:'',
                graphType:'line',
                dataSource:'',
                name:'',
                lengs:10,
                max:10000,
                min:0,
            },
            dbform:{
                db:''
            },
            chart:'',
            tag:'',
            tbData:{},
            tbID:'',
            dbData:[],
            tlist:[],
            vlist:[],
            interval:''
        }
    },
    
    methods: {
        getDbData() {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:1,
                pagesize:1000,
            })
            const result = axios({
                method: 'post',
                url:'/getDBList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.dbData=resp.data.data.list
            }
            })
        },
        drawline(Type){
            
            var tag=this.chartform.dataSource;
            let that=this
            let obj=JSON.parse(localStorage.getItem('client'))
            // console.log(obj);
            for(let key in obj) {
                // console.log(obj[key]);
                if(obj[key].n==tag) {
                    if(obj[key].v<that.chartform.max && obj[key].v>that.chartform.min){
                        that.vlist.push(obj[key].v);
                        that.tlist.push(obj[key].t);
                        // console.log(this.tmplist.length);
                        if(that.tlist.length>that.chartform.lengs) {
                            that.vlist.shift();
                            that.tlist.shift();
                        }
                    }    
                }
            }
                // console.log(this.datalist);
                // this.drawBar(this.tmplist,this.timelist,this.linename);
                // console.log(this.tmplist);
                // console.log(this.barlist);
                // this.config.data=this.barlist;
                this.draw(this.tlist,this.vlist,this.chartform.name,Type)
                
            
        },
        draw(t,v,n,type) {
            let that=this
            this.chart = this.$echarts.init(document.getElementById('chart'))
            this.chart.setOption({
                
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    }
                },
                legend: { //图例
                    textStyle:{
                        // fontSize: 18,//字体大小
                        color: '#ffffff'
                    },
                    data: [n],
                    
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                // grid: { //?绘图区域
                    // left: '3%',
                    // right: '4%',
                    // bottom: '3%',
                    // containLabel: true
                // },
                xAxis: [
                    {
                        type: 'category',
                        // boundaryGap: false,
                        data: t,
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        },
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        },
                    }
                ],
                series: [{
                    data: v,
                    type: type,
                    areaStyle: {},
                    name:n,
                }]
                
            });
            
            // window.onresize=that.chart.resize,
            window.addEventListener("resize", function () {
                if(that.chart) {
                    that.chart.resize();
                    // console.log("监听到变化");
                }
            })
        },

        nextstep() {
            let that=this;
            var postData={}
            var tagName=''
            this.$refs.chartformref.validate((valid)=>{
                if(!valid) return;
                
                if(this.active==0) {
                    this.active++;
                    this.steplabel2='保存图表并返回'
                    this.steplabel1='上一步'
                    this.step1=false;
                    setTimeout(function() {
                        that.step2=true;
                    },500);
                    this.tlist=[]
                    this.vlist=[]
                    this.drawline(this.chartform.graphType)
                }
                else if(this.active==1) {
                    this.$refs.chartformref2.validate((valid)=>{
                    if(!valid) return;
                    const userID=localStorage.getItem("userID")
                    
                        postData={
                            userID:userID,
                            graphID:this.tbID,
                            graphName:this.chartform.graphName,
                            tag:this.chartform.dataSource,
                            // tagName:tagName,
                            max:this.chartform.max,
                            min:this.chartform.min,
                            lengs:this.chartform.lengs,
                            type:this.chartform.graphType,
                        }
                        const result = axios({
                        method: 'post',
                        url:'/addMQTT',
                        data:postData
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.getDbData()
                            that.steplabel2='进入仪表盘',
                            that.steplabel1='算了'
                            that.$message({
                                showClose: true,
                                message: '保存成功',
                                center: true,
                                type: 'success'
                            });
                            that.step2=false;
                            setTimeout(function() {
                                that.step3=true;
                            },500);
                        }
                        })
                    

                    

                    this.$router.go(-1)
                    console.log(postData);
                    
                    })
                    
                }
                else if(this.active==2) {
                    this.$refs.chartformref3.validate((valid)=>{
                    if(!valid) return;
                    let postData=this.$qs.stringify({
                        dbID:this.dbform.db,
                        tblist:[this.tbID]
                    },{
                        indices:false
                    })
                    const result = axios({
                        method: 'post',
                        url:'/addGraphToDB',
                        data:postData,
                        indices:false
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.$message({
                                showClose: true,
                                message: '已经放入仪表盘！前往仪表盘管理看看吧？',
                                center: true,
                                type: 'success'
                            });
                            setTimeout(function() {
                                that.$router.push('/createdb')
                            },300)
                        }
                    })
                    })
                }
            })
        },
        laststep() {
            let that = this;
            if(this.active==0) {
            this.$router.go(-1)
            // client.end()
            }
            else {
                this.active--;
                if(this.active==0) {
                this.steplabel2='下一步'
                this.steplabel1='放弃编辑'
                this.step2=false;
                setTimeout(function() {
                    that.step1=true;
                },500)
                }
                if(this.active==1) {
                    this.$router.push('/createdb')
                }
            }
        },
        getTbData() {
            let that = this;
            this.tbID = this.$route.params.tbID
            this.chartform.graphName = this.$route.params.tbName
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
                that.chartform.graphType=resp.data.data.Graph.type
                that.chartform.dataSource=resp.data.data.Graph.tag
                that.chartform.graphName=resp.data.data.Graph.graphName
                that.chartform.name=resp.data.data.Graph.legend[0]
                that.chartform.lengs=resp.data.data.Graph.lengs
                that.chartform.max=resp.data.data.Graph.max
                that.chartform.min=resp.data.data.Graph.min
                }
            })
            setTimeout(()=>{
                this.step1=true
                this.drawline(that.chartform.graphType)
            },500)
        },
        gettag() {
            let that =this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID
            })
            const result = axios({
                method: 'post',
                url:'/getMqttTagList',
                data:postData,
                }).then(function(resp){
                    if(resp.data.status==200) {
                    that.tagList=resp.data.data
                    that.step1=true
                }
            })
        }
    },
    created() {
        let that = this
        const loading = this.$loading({
            lock: true,
            text: '拼命加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        });
        this.getTbData();
        this.gettag();
        
        setTimeout(() => {
                loading.close();
        }, 1000);
    },
    mounted() {
        let that = this
        this.interval =setInterval(()=>{
            setTimeout(()=>{
                // console.log(client);
                if(that.active<=1) {
                    // console.log(that.chartform.graphType);
                    that.drawline(that.chartform.graphType)
                }
            },300)
        },5000)
    },
    beforeDestroy() {
        // client.end()
        clearInterval(this.interval)
    },
    
    // mounted () {
        // this.getData();
    // },
    // watch: {
        // tableData: {
            // handler() {
                // this.getData()
            // }
        // },
        // deep:true //深度监听
    // }

}
</script>

<style lang="less" scoped>
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
    padding: 25px;
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
</style>