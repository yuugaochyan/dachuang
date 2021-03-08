<template>
        
        <div  class="con">
            <div class="left">
                
                <transition name="el-fade-in" >
                <el-form :model="chartform" v-show="step1" :rules="rules" ref="chartformref" :inline="true">
                    <el-form-item label="问题名字"  prop="graphName">
                        <el-input v-model="chartform.graphName" clearable placeholder="为这个可视化问题取个名字吧"></el-input>
                    </el-form-item>
                    <br>
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
                    

                    <el-form-item label="警告值下限" prop="name">
                        <el-input v-model="chartform.min" ></el-input>
                    </el-form-item>
                    <el-form-item label="警告值上限" prop="name">
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
                        :key="item.dbId"
                        :label="item.dbName"
                        :value="item.dbID">
                        </el-option>
                    </el-select>
                    </el-form-item>
                </el-form>
                </transition>

            <div class="bt-next">
                <el-button type="info"  @click="laststep">{{steplabel1}}</el-button>
                <el-button type="warning"  @click="nextstep">{{steplabel2}}</el-button>
            </div>


            <el-steps :active="active" finish-status="success" class="bt-step" align-center>
                <el-step title="第一步" description="命名这个问题并选择数据源"></el-step>
                <el-step title="第二步" description="配置警告值上下限，保存修改"></el-step>
                <!-- <el-step title="第三步" description="进入仪表盘看看吧？"></el-step> -->
            </el-steps>
            </div>
            <div class="right">
                <dv-digital-flop :config="config" style="width:100%;height:100%;" />
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
    name:'editmqttnum',
    data(){
        return {
            rules: {
                graphName: [
                    { required: true, message: '请输入问题的名字！', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
                dataSource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
            },
            rules2: {
                
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
                dataSource:'',
                name:'',
                max:10000,
                min:0,
            },
            dbform:{
                db:''
            },
            config:{
                number:[0.0],
                toFixed: 1,
                content:'{nt}',
                style: {
                    fontSize:120,
                    fill:'#dfdfdf'
                }
            },
            tag:'',
            tbID:'',
            tbData:{},
            dbData:[]
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
        drawline(){

            
            var tag='';
            let that=this
            
            // let postDta=this.$qs.stringify({
                // dataSource:that.chartform.dataSource
            // })
            // this.$axios.post("/getmqttTable",postDta)
            // .then((resp)=>{
                // tag=resp.data.tag;
                // that.tag=resp.data.tag;
            // })
            tag=this.chartform.dataSource
            console.log(client.connected);
            if(client.connected) {
                client.end()
            }
            client.on('connect', (e) => {
                console.log("连接成功！！！")
                client.subscribe(tag, { qos: 0 }, (error) => {
                if (!error) {
                    console.log('订阅成功')
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
                console.log(msg.v);
                that.config.number[0]=parseInt(msg.v)
                var max=that.chartform.max;
                var min=that.chartform.min;
                // console.log(that.config.number[0]);
                if(msg.v>max){
                    that.config.style.fill='red'
                }
                else if(msg.v<min){
                    that.config.style.fill='white'
                }
                else {
                    that.config.style.fill='#3de7c9'
                }
                that.config.content=that.chartform.graphName+': {nt}'
                this.config = { ...this.config }
                
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
                    this.drawline()
                }
                else if(this.active==1) {
                    this.$refs.chartformref2.validate((valid)=>{
                    if(!valid) return;
                    const userID=localStorage.getItem("userID")
                    for(let key in this.tagList) {
                        // console.log(key);
                        // console.log(that.tagList[key]);
                        if(that.tagList[key].value==that.chartform.dataSource) {
                            tagName=that.tagList[key].label
                        }
                    }
                        postData={
                            userID:userID,
                            graphID:this.tbID,
                            graphName:this.chartform.graphName,
                            tag:this.chartform.dataSource,
                            tagName:tagName,
                            max:this.chartform.max,
                            min:this.chartform.min,
                        }
                        const result = axios({
                        method: 'post',
                        url:'/addMQTT',
                        data:postData
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.getDbData()
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
                    this.$router.go(-1)
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
            client.end()
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
                that.chartform.graphName=resp.data.data.Graph.graphName
                that.chartform.dataSource=resp.data.data.Graph.tag
                that.chartform.name=resp.data.data.Graph.tagName
                that.chartform.max=resp.data.data.Graph.max
                that.chartform.min=resp.data.data.Graph.min
                }
            })
            setTimeout(()=>{
                this.step1=true
                this.drawline()
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
    mounted () {
        const loading = this.$loading({
            lock: true,
            text: '拼命加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        });
        this.getTbData();
        this.getDbData()
        this.gettag();
        setTimeout(() => {
                loading.close();
        }, 700);
        
    },
    beforeDestroy() {
        client.end()
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