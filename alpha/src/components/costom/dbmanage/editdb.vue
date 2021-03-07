<template>
    <div id="app" v-if="reset">
        <div class="maincontain" @dblclick="drawer = true">
            

                <!-- <el-tooltip class="item" effect="dark" content="隐藏调整按钮" placement="bottom-start"> -->
                    <!-- <el-button @click="drawer = true" circle type="warning" icon="iconfont icon-preview" style="margin-left: 16px;"></el-button> -->
                <!-- </el-tooltip> -->

        <div class="vueGridLayout">
        <grid-layout
            :layout="layoutData"
            :col-num="12"
            :row-height="layoutHeight"
            :is-draggable="editable"
            :is-resizable="editable"
            :is-mirrored="false"
            :vertical-compact="true"
            :margin="[10, 10]"
            :use-css-transforms="true"
        >
            <grid-item
            v-for="(item) in layoutData"
            :x="item.x"
            :y="item.y"
            :w="item.w"
            :h="item.h"
            :minW="minW"
            :minH="minH"
            :maxW="maxW"
            :maxH="maxH"
            :i="item.i"
            :key="item.i"
            @resized="resizedEvent"
            @moved="movedEvent"
            
            >
            
            
                <div class="title">
                    {{item.objectData.graphName}}
                    <div class="tool-bt">
                    <el-tooltip  effect="dark" content="删除这个可视化" placement="bottom-start" v-if="editable">
                        <el-button size="mini" @click="deleteTB(item.i)" circle type="info" icon="iconfont icon-delete" ></el-button>
                    </el-tooltip>
                    </div>
                    <div class="tool-bt">
                    <el-tooltip  effect="dark" content="编辑这个可视化" placement="bottom-start" v-if="editable">
                        <el-button size="mini" @click="editTB(item)" circle type="warning" icon="iconfont icon-edit" ></el-button>
                    </el-tooltip>
                    </div>
                </div>
                <div class="tb">
                    <chart v-if="item.type=='chart'" :id="item.i" :obdata="item.objectData"></chart>
                    <mqttline v-if="item.type=='mqttline'" :id="item.i" :obdata="item.objectData"></mqttline>
                    <sqltb v-if="item.type=='table'" :id="item.i" :obdata="item.objectData"></sqltb>
                    <mqttnum v-if="item.type=='mqttnum'" :id="item.i" :obdata="item.objectData"></mqttnum>
                    
                </div>
            </grid-item>
        </grid-layout>
    
    
    </div>
                
                
                
                <el-drawer
                    
                    :visible.sync="drawer"
                    :direction="direction"
                    size=180>
                    <span slot="title" class="tool-banner"><i class="el-icon-s-tools"></i>工具箱</span>
                    <div class="toolbar">
                        <el-tooltip class="item" effect="dark" content="调整图表位置（alt+R）" placement="bottom-end">
                            <el-button @click="asideResize" type="warning"  plain>适应调整</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="编辑仪表盘信息（alt+E）" placement="bottom-end">
                            <el-button @click="editTable=true" type="warning"  plain>编辑问题</el-button>
                        </el-tooltip><br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="新增可视化到仪表盘（alt+A)" placement="bottom-end">
                            <el-button @click="add" type="warning"  plain>新增图表</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="保存这个仪表盘（alt+S）" placement="bottom-end">
                            <el-button @click="savedb" type="warning"  plain>保存问题</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="返回仪表盘管理页面" placement="bottom-end">
                            <el-button @click="goback" type="warning"  plain>返回列表</el-button>
                        </el-tooltip>
                    </div>
                </el-drawer>


                <el-dialog title="添加一些新的可视化" :visible.sync="addTable">
                    <el-table
                    :data="tbData"
                    height="450"
                    border
                    style="width: 100%"
                    ref="multipleTable"
                    @selection-change="handleSelectionChange">
                        <el-table-column
                            type="selection"
                            width="55">
                        </el-table-column>
                        <el-table-column
                            prop="graphName"
                            label="可视化名字"
                            width="150">
                        </el-table-column>
                        <el-table-column
                            prop="graphType"
                            label="可视化类型"
                            width="200">
                        </el-table-column>
                        
                    </el-table>
                    <el-pagination
                            background
                            @size-change="tbSizeChange"
                            @current-change="tbCurrentChange"
                            :current-page="tbCurrentPage"
                            :page-sizes="[5, 10, 15, 20]"
                            :page-size="tbSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="tbtotal">
                        </el-pagination>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addTable = false">取 消</el-button>
                        <el-button type="primary" @click="editAdd">确 定</el-button>
                    </div>
                </el-dialog>


                <el-dialog title="编辑这个仪表盘的信息" :visible.sync="editTable">
                    <el-form :model="form" :rules="rules" ref="chartformref" >
                        <el-form-item label="仪表盘名称" :label-width="formLabelWidth" prop="name">
                            <el-input v-model="form.name" autocomplete="off" ></el-input>
                        </el-form-item>
                        <el-form-item label="仪表盘摘要" :label-width="formLabelWidth">
                            <el-input v-model="form.info" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="editTable = false">取 消</el-button>
                        <el-button type="primary" @click="editInfo">确 定</el-button>
                    </div>
                </el-dialog>

        </div>
    </div>
</template>

<script>
import axios from 'axios'
import mtqqtest from '@/components/factory/mqttfac/mtqqtest'
import VueGridLayout from 'vue-grid-layout'
import chart from '@/components/costom/chart'
import mqttline from '@/components/costom/mqttline'
import sqltb from '@/components/costom/sqltb'
import mqttnum from '@/components/costom/mqttnum'
const GridLayout = VueGridLayout.GridLayout
const GridItem = VueGridLayout.GridItem
export default {
    components: { 
    GridLayout,
    GridItem,
    chart,
    mqttline,
    sqltb,
    mqttnum },
    name:"editdb",
    inject:['reload'],
    data() {
        return {
            rules: {
                name: [
                    { required: true, message: '请输入仪表盘的名字！这是必须的', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
            },
            dbData:[],
            dbID:'',
            // reset:false,
            drawer:false,
            direction: 'rtl',
            editTable:false,
            addTable:false,
            form: {
                name: '',
                info: '',
            },
            formLabelWidth: '120px',
            tbData:[],
            multipleTable:[],
            interval:'',
            resizeinterval:'',
            editable:true,
            layout: [
              { x: 0, y: 0, w: 4, h: 2, i: 0 } //数据格式
            ],
            // 布局数据
            layoutData: [],
            layoutHeight: 130,
            layoutConfig: {
              height: 330, // 默认高度
              dialogVisible: false // 是否可拖拽或改变大小
            },
            minW:2,
            minH:2,
            maxW:10,
            maxH:10,
            reset:false,
            msg1:'',
            msg2:'',
            msg3:'',
            msg4:'',
            tbtotal:0,
            tbSize:5,
            tbCurrentPage:1,
        }
    },
    methods: {
        asideResize(){
            let myEvent = new Event('resize'); // resize是指resize事件
            window.dispatchEvent(myEvent); // 触发window的resize事件
        },
        handleSelectionChange(val) {
            this.multipleTable=val
        },
        deleteTB(tbID){
            console.log(tbID);
            let that = this;
            let postData=this.$qs.stringify({
                itemID:tbID
            })
            const result = axios({
                method: 'post',
                url:'/removeGraph',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.$message({
                    showClose: true,
                    message: resp.data.msg,
                    center: true,
                    type: 'success'
                });
                }
                that.reload();
            })
            
        },
        editTB(tb) {
            console.log(tb.type);
            if(tb.type=='chart') {
                this.$router.push({
                    name:'editchart',
                    params: {
                        tbID:tb.objectData.graphID,
                        tbName:tb.title
                    }
                })
            }
            else if(tb.type=='table') {
                this.$router.push({
                    name:'edittable',
                    params: {
                        tbID:tb.objectData.graphID,
                        tbName:tb.title
                    }
                })
            }
            else if(tb.type=='mqttline') {
                this.$router.push({
                    name:'editmqttline',
                    params: {
                        tbID:tb.objectData.graphID,
                        tbName:tb.title
                    }
                })
            }
            else if(tb.type=='mqttnum') {
                this.$router.push({
                    name:'editmqttnum',
                    params: {
                        tbID:tb.objectData.graphID,
                        tbName:tb.title
                    }
                })
            }
        },
    
    resizedEvent: function(i, newH, newW, newHPx, newWPx){
        // console.log("RESIZED i=" + i + ", H=" + newH + ", W=" + newW + ", H(px)=" + newHPx + ", W(px)=" + newWPx);
        localStorage.setItem('pandectDisplace', JSON.stringify(this.layoutData));
        this.asideResize();
    },
    movedEvent: function(i, newX, newY,e){
        localStorage.setItem('pandectDisplace', JSON.stringify(this.layoutData));
        this.asideResize();
        
    },
        getDbData() {
            let that = this;
            this.dbID = this.$route.params.dbID
            // console.log(this.dbID);
            this.form.name = this.$route.params.dbName
            this.form.info = this.$route.params.dbInfo
            // console.log(this.form.name);
            // let postData=this.$qs.stringify({
                // dbID:that.dbID,
            // })
            // const result = axios({
                // method: 'post',
                // url:'/getDBList',
                // data:postData
            // }).then(function(resp){
                // if(resp.data.status==200) {
                    // that.dbData=resp.data.data
                // }
            // })
            this.init();
            setTimeout(()=>{
                this.msg1=this.$notify({
                        showClose: true,
                        message: '双击屏幕可打开工具箱→',
                        type: 'info',
                        offset: 100,
                        duration:0
                });
            },300)
            setTimeout(()=>{
                this.msg2=this.$notify({
                    showClose: true,
                    message: '仪表盘将会每隔30秒进行自动保存(✧◡✧)',
                    type: 'warning',
                    offset: 100,
                    duration:0
                });
            },600)
            setTimeout(()=>{
                this.msg3=this.$notify({
                    showClose: true,
                    message: '如遇数据显示不全请尝试刷新Σ(⊙▽⊙"a',
                    offset: 100,
                    type: 'warning',
                    duration:0
                });
            },900)
            setTimeout(()=>{
                this.msg4=this.$notify({
                    message: '尝试使用快捷键！如alt+R来进行适应调整(*^▽^*)',
                    offset: 100,
                    type: 'info',
                    duration:0
                });
            },1200)
        },
        init() {
        
        let that=this
        let postDta=this.$qs.stringify({
            dbID:this.dbID
        })
        console.log(this.dbID);
        const result = axios({
            method: 'post',
            url:'/getDBItemInfo',
            data:postDta
        }).then(function(resp){
            if(resp.data.status==200) {
                that.layoutData = resp.data.data;
                localStorage.setItem('pandectDisplace', JSON.stringify(that.layoutData));
                // that.asideResize();
            }
        })
        
        },
        
        goback() {
            this.$router.push('/createdb')
        },
        editInfo() {
            this.editTable = false;
            //TODO 
            this.$refs.chartformref.validate((valid)=>{
                if(!valid) return;
                let that =this;
                const userID=localStorage.getItem("userID")
                console.log(this.dbID);
                let postData=this.$qs.stringify({
                    userID:userID,
                    dbID:this.dbID,
                    db:{
                        name:this.form.name,
                        info:this.form.info
                    }
                })
                const result = axios({
                    method: 'post',
                    url:'/addDB',
                    data:postData
                }).then(function(resp){
                    if(resp.data.status==200) {
                    that.$notify({
                        message: '修改成功',
                        type: 'success',
                        offset:100
                    });
                    that.reload()
                }
                })
            })
        },
        tbSizeChange(val) {
            this.tbSize=val;
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize
            })
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/getGraphList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data.list
            }
            })
        },
        tbCurrentChange(val) {
            let that = this;
            const userID=localStorage.getItem("userID")
            this.tbCurrentPage=val;
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize
            })
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/getGraphList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data.list
            }
            })
        },
        add() {
            this.addTable=true;
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize
            })
            const result = axios({
                method: 'post',
                url:'/getGraphList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data.list
                that.tbtotal=resp.data.data.total
            }
            })
        },
        editAdd() {
            // console.log(this.multipleTable);
            
                this.addTable=false
                this.savedb()
                var tbIDList=[]
                let that = this;
                const userID=localStorage.getItem("userID")
                for(let tb in this.multipleTable) {
                    tbIDList.push(this.multipleTable[tb].graphID)
                }
                // console.log(tbIDList);

                
                let postData=this.$qs.stringify({
                    dbID: this.dbID,
                    tblist: tbIDList
                },{
                    indices:false
                })
                console.log(postData);
                const result = axios({
                    method: 'post',
                    url:'/addGraphToDB',
                    data:postData,
                    indices:false
                }).then(function(resp){
                    if(resp.data.status==200) {
                        that.$notify({
                            
                            message: resp.data.msg,
                            offset: 100,
                            type: 'success'
                        });
                        that.reload()
                    }
                })
            
        },
        savedb() {
            let Data=JSON.parse(localStorage.getItem('pandectDisplace'))
            const userID=localStorage.getItem("userID")
            // console.log(Data);
            var locaData=[];
            for(let key in Data) {
                
                // console.log(key);
                let pDitem={
                    x:Data[key].x,
                    y:Data[key].y,
                    w:Data[key].w,
                    h:Data[key].h,
                    i:Data[key].i
                }
                // console.log(pDitem);
                locaData.push(pDitem);
            }
            let that = this;
            let postData=locaData

            console.log(postData);
            const result = axios({
                method: 'post',
                url:'/saveItemLoc',
                data:postData
            }).then(function(resp){
                
                if(resp.data.status==200) {
                    that.$notify({
                        
                        message: resp.data.msg,
                        offset: 100,
                        type: 'success'
                    });
                    
                }
            })
        },
        handleEvent(e){
            var that = this;
            //alt+F  
            let key = window.event.keyCode;  
            if (83 == e.keyCode && e.altKey) {
                //~如果是alt+S 的操作的话，方法执行与此
                that.savedb()
            }
        },
        handleEvent2(e){
            var that = this;
            //alt+F  
            let key = window.event.keyCode;  
            if (82 == e.keyCode && e.altKey) {
                //~如果是alt+R 的操作的话，方法执行与此
                that.asideResize()
                // console.log("resize");
            }
        },
        handleEvent3(e){
            var that = this;
            //alt+F  
            let key = window.event.keyCode;  
            if (69 == e.keyCode && e.altKey) {
                //~如果是alt+E 的操作的话，方法执行与此
                that.editTable=true
            }
        },
        handleEvent4(e){
            var that = this;
            //alt+F  
            let key = window.event.keyCode;  
            if (65 == e.keyCode && e.altKey) {
                //~如果是alt+A 的操作的话，方法执行与此
                that.add();
                // console.log("resize");
            }
        },
    },
    created() {
        this.interval =setInterval(()=>{
            setTimeout(()=>{
                this.savedb();
            },0)
        },30000)
        this.resizeinterval =setInterval(()=>{
            setTimeout(()=>{
                this.asideResize();
            },0)
        },1000)
        const loading = this.$loading({
            lock: true,
            text: '拼命加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        });
        this.getDbData();
        document.addEventListener('keydown',this.handleEvent)
        document.addEventListener('keydown',this.handleEvent2)
        document.addEventListener('keydown',this.handleEvent3)
        document.addEventListener('keydown',this.handleEvent4)
        // await this.asideResize();
        setTimeout(() => {
                loading.close();
        }, 1000);
    },
    mounted() {
        // this.reload()
        // this.forceUpdate()
        // this.$router.go(0);
        // setTimeout(()=>{
            // this.$router.go(0);
            // this.asideResize()
        // },500)
        
    },
    beforeDestroy() {
        clearInterval(this.interval);
        clearInterval(this.resizeinterval);
        document.removeEventListener('keydown', this.handleEvent);
        document.removeEventListener('keydown', this.handleEvent2);
        document.removeEventListener('keydown', this.handleEvent3);
        document.removeEventListener('keydown', this.handleEvent4);
        this.msg1.close()
        this.msg2.close()
        this.msg3.close()
        this.msg4.close()
    },
    watch: {
        layoutData: function(){
            this.$nextTick(function(){
                this.reset = true;
                // location.reload();
            })
            
        }
    }

    // watch: {
        // dbData: function(){
            // this.$nextTick(function(){
                // this.reset = true;
            // })
        // }
    // }
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
.vue-grid-item {
  background: #5b5b5f;
  
}
.tb {
    height: 82%;
    width: 97%;
    background-color: #5b5b5f;
    margin: 0 auto;
}
.title {
    margin: 0 auto;
    // text-align: center;
    padding-left: 7%;
    color: white;
    height: 17%;
    font-size: 170%;
    // padding-right: 1%;
    // padding-top: 2%;
}
.vueGridLayout {
  margin: 0 auto;
  height: calc(93.6vh);
  width: 100%;
}
.tool-bt {
    float: right;
    margin-right: 5px;
    margin-top: 2px;
}


</style>