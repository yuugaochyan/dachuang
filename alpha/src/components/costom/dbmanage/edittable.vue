<template>
        
        <div  class="con" v-if="reset">
            <div class="left">
                
                <transition name="el-fade-in" >
                <el-form :model="chartform" v-show="step1" :rules="rules" ref="chartformref" :inline="true">
                    <el-form-item label="问题名字"  prop="graphName">
                        <el-input v-model="chartform.graphName" clearable placeholder="为这个可视化问题取个名字吧"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item label="数据来源"  prop="dataSource">
                    <el-select v-model="chartform.dataSource" placeholder="请选择数据源" @change="getData">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                    </el-form-item>
                </el-form>
                </transition>

                <transition name="el-fade-in" >
                <el-form :model="dbform" v-show="step2" :rules="rules2" ref="chartformref2">
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
                <el-step title="第二步" description="保存并返回"></el-step>
            </el-steps>
            </div>
            <div class="right">
                <dv-scroll-board :config="tableData" style="width:100%;height:100%" />
            </div>
            
            
        </div>
        
</template>

<script>
import axios from 'axios'
export default {
    name:'edittable',
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
                db: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
            },
            options: [{
                value: 'equipmentrepair',
                label: 'equipmentrepair'
            }, {
                value: 'eqpseasonstatistic',
                label: 'eqpseasonstatistic'
            }],
            active:0,
            steplabel1:"放弃编辑",
            steplabel2:"保存图表",
            tableData:{},
            step1:false,
            step2:false,
            chartform:{
                graphName:'',
                dataSource:'',
            },
            dbform:{
                db:''
            },
            config:{},
            tbData:{},
            tbID:'',
            dbData:[],
            reset:false
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
        getData () {

            let that=this
            let postDta=this.$qs.stringify({
                dataSource:that.chartform.dataSource
            })
            // console.log(that.chartform.dataSource);
            this.$axios.post("/getTable",postDta)
            .then((resp)=>{
                console.log(resp)
                that.tableData=resp.data.data
            }),
            this.config=this.tableData
            this.config = { ...this.config }

        },

        nextstep() {
            let that=this;
            var postData={}
            this.$refs.chartformref.validate((valid)=>{
                if(!valid) return;
                
                if(this.active==0) {
                    const userID=localStorage.getItem("userID")
                    postData={
                        graphID:this.tbID,
                        userID:userID,
                        graphName:this.chartform.graphName,
                        tableName:this.chartform.dataSource,
                    }
                        const result = axios({
                        method: 'post',
                        url:'/addTable',
                        data:postData
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.getDbData;
                            that.steplabel2='进入仪表盘',
                            that.steplabel1='算了'
                            that.$message({
                                showClose: true,
                                message: '保存成功',
                                center: true,
                                type: 'success'
                            });
                            that.step1=false;
                            setTimeout(function() {
                                that.step2=true;
                            },500);
                        }
                        })
                        this.$router.go(-1)
                }
                else if(this.active==1) {
                    this.$refs.chartformref2.validate((valid)=>{
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
            
            this.$router.go(-1)
            
        },
        getTbData() {
            let that = this;
            this.tbID = this.$route.params.tbID
            console.log(this.tbID);
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
                // that.chartform.graphType=resp.data.data.Graph.graphType
                that.chartform.dataSource=resp.data.data.Graph.tableName
                that.chartform.graphName=resp.data.data.Graph.graphName
                // that.Chart.name=resp.data.data.Graph.legend[0]
                }
            })
            setTimeout(()=>{
                this.getData()
                this.step1=true
            },500)
        },
        gettbList() {
            let that = this;
            // const userID=localStorage.getItem("userID")
            // 
            const result = axios({
                method: 'post',
                url:'/getTableSelectList',
                // data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.options=resp.data.data
            }
            })
        },
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
    mounted () {
        this.getDbData()
        this.gettbList()
    },
    // mounted () {
        // this.getData();
    // },
    watch: {
        options: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        },
    //     deep:true //深度监听
    }

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
</style>