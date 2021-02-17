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
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
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

                <transition name="el-fade-in" >
                <el-form :model="dbform" v-show="step3" :rules="rules3" ref="chartformref3">
                    <el-form-item label="你的仪表盘"  prop="db">
                    <el-select v-model="dbform.db" placeholder="请选择仪表盘">
                        <el-option
                        v-for="item in dbData"
                        :key="item.dbID"
                        :label="item.dbName"
                        :value="item.dbID">
                        </el-option>
                    </el-select>
                    </el-form-item>
                </el-form>
                </transition>

            <div class="bt-next">
                <el-button type="info"  @click="laststep" v-show="active>0">{{steplabel1}}</el-button>
                <el-button type="warning"  @click="nextstep">{{steplabel2}}</el-button>
            </div>


            <el-steps :active="active" finish-status="success" class="bt-step" align-center>
                <el-step title="第一步" description="命名这个问题并选择图表类型和数据源"></el-step>
                <el-step title="第二步" description="配置相关数据设置，将问题存入你的库里"></el-step>
                <el-step title="第三步" description="放进仪表盘看看吧？"></el-step>
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
var client
const options= {
    connectTimeout: 40000,
    clientId: 'mqtitId-Home',
    username: 'hyiot',
    password: '1234abcd',
    clean: true
}
client = mqtt.connect('ws://39.100.250.145:8006/mqtt', options)
export default {
    name:'createmqttline',
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
            tbID:'',
            dbData:[]
        }
    },
    
    methods: {
        getDbData() {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
            })
            const result = axios({
                method: 'post',
                url:'/getDBList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.dbData=resp.data.data
            }
            })
        },
        drawline(Type){
            var vlist=[];
            var tlist=[];
            
            var tag=this.chartform.dataSource;
            let that=this
            
            // let postDta=this.$qs.stringify({
                // dataSource:that.chartform.dataSource
            // })
            // this.$axios.post("/getmqttTable",postDta)
            // .then((resp)=>{
                // tag=resp.data.tag;
                // that.tag=resp.data.tag;
            // })
            client.on('connect', (e) => {
                console.log("连接成功！！！")
                client.subscribe(tag, { qos: 0 }, (error) => {
                if (!error) {
                    // console.log('订阅成功')
                } else {
                    console.log('订阅失败')
                }
                })
            })
        // 接收消息处理
            client.on('message', (topic, message) => {
                let msg = JSON.parse(message.toString())
                // this.datalist.name=msg.n;
                // this.datalist.value=msg.v;
                // console.log(msg.v);
                var lengs=this.chartform.lengs;
                var max=this.chartform.max;
                var min=this.chartform.min;
                if(msg.v<max && msg.v>min){
                    vlist.push(msg.v);
                    var time = new Date(msg.t)
                    var formatTime = time.toTimeString().substr(0,8)
                    tlist.push(formatTime);

                    // console.log(tlist.length);
                    // console.log(lengs);
                    if(tlist.length>lengs) {
                        vlist.shift();
                        tlist.shift();
                    }
                }
                // console.log(this.datalist);
                // this.drawBar(this.tmplist,this.timelist,this.linename);
                // console.log(this.tmplist);
                // console.log(this.barlist);
                // this.config.data=this.barlist;
                this.draw(tlist,vlist,this.chartform.name,Type)
                
            })
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
            var postData={};
            var tagName=''
            this.$refs.chartformref.validate((valid)=>{
                if(!valid) return;
                
                if(this.active==0) {
                    this.active++;
                    this.steplabel2='保存图表'
                    this.step1=false;
                    setTimeout(function() {
                        that.step2=true;
                    },500);
                    this.drawline(this.chartform.graphType)
                }
                else if(this.active==1) {
                    this.$refs.chartformref2.validate((valid)=>{
                    if(!valid) return;
                    // console.log(options);
                    for(let key in this.tagList) {
                        // console.log(key);
                        // console.log(that.tagList[key]);
                        if(that.tagList[key].value==that.chartform.dataSource) {
                            tagName=that.tagList[key].label
                        }
                    }
                    // console.log(tagName);
                    const userID=localStorage.getItem("userID")

                        postData={
                            userID:userID,
                            graphName:this.chartform.graphName,
                            tag:this.chartform.dataSource,
                            tagName:tagName,
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
                            that.tbID=resp.data.tbID;
                            that.getDbData();
                            that.steplabel2='放入仪表盘',
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
            this.$router.push('/createtb')
        },
        gettag() {
            let that =this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID
            })
            const result = axios({
                method: 'post',
                url:'/getTagList',
                data:postData,
                }).then(function(resp){
                    if(resp.data.status==200) {
                    that.tagList=resp.data.data
                    that.step1=true
                }
            })
        }
    },
    beforeDestroy() {
        client.end()
    },
    
    mounted () {
        this.gettag();
    },
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