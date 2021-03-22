<template>
    <div id="app" v-if="reset">
        <div class="maincontain">
            

                <!-- <el-tooltip class="item" effect="dark" content="隐藏调整按钮" placement="bottom-start"> -->
                    <!-- <el-button @click="drawer = true" circle type="warning" icon="iconfont icon-preview" style="margin-left: 16px;"></el-button> -->
                <!-- </el-tooltip> -->
        <!-- <div class="cover-nav"></div> -->
        <header class="drawer-bt" @mouseover="drawer=true" id="bt">
            <!-- <el-divider> -->
                <i class="el-icon-d-arrow-right"></i>
            <!-- </el-divider> -->
        </header>

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
                    :modal=false
                    size=180
                    @close="closeDrawer"
                    @open="openDrawer">
                    <span slot="title" class="tool-banner"><i class="el-icon-s-tools"></i>工具箱</span>
                    <div class="toolbar">
                        
                        <el-tooltip class="item" effect="dark" content="编辑仪表盘信息（alt+E）" placement="bottom-end">
                            <el-button @click="editTable=true" type="warning"  plain>编辑问题</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="新增可视化到仪表盘（alt+A)" placement="bottom-end">
                            <el-button @click="add" type="warning"  plain>新增图表</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="推送到导航栏" placement="bottom-end">
                            <el-button @click="pushNav=true" type="warning"  plain>添加到导航</el-button>
                        </el-tooltip>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="保存这个仪表盘（alt+S）" placement="bottom-end">
                            <el-button @click="savedb" type="warning"  plain>保存问题</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="调整图表位置（alt+R）" placement="bottom-end">
                            <el-button @click="asideResize" type="warning"  plain>适应调整</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="返回仪表盘管理页面" placement="bottom-end">
                            <el-button @click="goback" type="warning"  plain>返回列表</el-button>
                        </el-tooltip>
                    </div>
                </el-drawer>


                <el-dialog title="添加一些新的可视化" :visible.sync="addTable">
                    <div class="banner4" v-if="gotoCreate">
                        <div class="search2">
                        <el-input
                            placeholder="查找可视化"
                            v-model="nameInput"
                            @input="debounce"
                            width:100px>
                            <i slot="prefix" class="el-input__icon el-icon-search" id="input-icon"></i>
                        </el-input>
                        </div>
                        <div class="search2">
                        <el-select v-model="typeInput" placeholder="请选择类型" @change="debounce">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                            <i slot="prefix" class="el-input__icon el-icon-search" id="select-icon"></i>
                        </el-select>
                        </div>
                        <el-button @click="gotoAddTB"  type="warning" icon="iconfont icon-add">创建可视化</el-button>
                    </div>
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
                        <div class="createTB" slot="empty">
                        你的可视化里好像没有数据？
                        <el-link :underline="false" type="warning" @click="gotoCreate">
                            <i class="el-icon-plus"></i>去创建一些可视化吧！
                        </el-link>
                        <!-- <el-button @click="gotoCreate">前往</el-button> -->
                    </div>
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

                <el-dialog title="导航栏中的名字" :visible.sync="pushNav">
                    <!-- <el-form :model="nav" :rules="rule2" ref="chartformref2" > -->
                        <!-- <el-form-item label="仪表盘名称" :label-width="formLabelWidth" prop="name"> -->
                            <el-input v-model="NavName" autocomplete="off" id="navInput"></el-input>
                        <!-- </el-form-item> -->
                    <!-- </el-form> -->
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="pushNav = false">取 消</el-button>
                        <el-button type="primary" @click="pushToNav">确 定</el-button>
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
                info: [
                    { required: false, message: '好像你的输入不合法？太长啦！', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
            },
            dbData:[],
            dbID:'',
            // reset:false,
            drawer:false,
            direction: 'ltr',
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
            maxW:12,
            maxH:12,
            reset:false,
            msg1:'',
            msg2:'',
            msg3:'',
            msg4:'',
            tbtotal:0,
            tbSize:5,
            tbCurrentPage:1,
            isShowHint:'',
            preURL:'',
            pushNav:false,
            NavName:'',
            //^搜索
            nameInput:'',
            typeInput:'',
            options: [{
                value: 'chart',
                label: 'chart'
            }, {
                value: 'table',
                label: 'table'
            }, {
                value: 'mqttline',
                label: 'mqttline'
            }, {
                value: 'mqttnum',
                label: 'mqttnum'
            }, {
                value: '',
                label: '全部类型'
            }],
            timer:null,
            gotoCreate:true
        }
    },
    
    methods: {
        gotoAddTB() {
            this.$router.push('/createtb')
        },
        search() {
            let that = this
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize,
                graphName:that.nameInput,
                graphType:that.typeInput
            })
            // console.log(postData);
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
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
        debounce() {
            // console.log(this.timer);
            let that = this
            let input=document.getElementById('input-icon')
            let select=document.getElementById('select-icon')
            input.setAttribute('class','el-input__icon el-icon-loading')
            select.setAttribute('class','el-input__icon el-icon-loading')
            if(this.timer!==null) {
                clearTimeout(this.timer)
                // console.log(this.timer);
            }
            this.timer=setTimeout(function(){
                // console.log(that.timer);
                that.search()
                input.setAttribute('class','el-input__icon el-icon-search')
                select.setAttribute('class','el-input__icon el-icon-search')
            },1000)
            
        },
        gotoCreate() {
            this.$router.push('/createtb')
        },
        pushToNav() {
            let that=this
            if(this.NavName==''||this.NavName.length>=10) {
                let box=document.getElementById('navInput')
                box.style.cssText="border:solid 0.5px #FF0000"
                return
            }
            else {
                let box=document.getElementById('navInput')
                box.style.cssText=""
            }
            const userID=localStorage.getItem("userID")
            let postDta=this.$qs.stringify({
                dbID:this.dbID,
                // userID:userID,
                naviName:this.NavName,
                naviStatus:1,
            })
            console.log(this.dbID);
            const result = axios({
                method: 'post',
                url:'/updateDB',
                data:postDta
            }).then(function(resp){
                if(resp.data.status==200) {
                    that.reload()
                }
            })
        },
        openDrawer() {

                let bt =document.getElementById('bt')
                bt.style.cssText="margin-left:125px"

        },
        closeDrawer() {
            console.log(true);
            let bt =document.getElementById('bt')
            bt.style.cssText=""
        },
        changeShowHint() {
            this.$store.commit("setShowHint",this.isShowHint);
        },
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
            // let showhint=localStorage.getItem('showHint')
            this.isShowHint=localStorage.getItem("showHint")
            console.log(this.isShowHint);
            // if(this.isShowHint=='true') {
            // setTimeout(()=>{
                // this.msg1=this.$notify({
                        // showClose: true,
                        // message: '双击屏幕可打开工具箱，可在工具箱中关闭提示→',
                        // type: 'info',
                        // offset: 100,
                        // duration:0
                // });
            // },300)
            // setTimeout(()=>{
                // this.msg2=this.$notify({
                    // showClose: true,
                    // message: '仪表盘将会每隔30秒进行自动保存(✧◡✧)',
                    // type: 'warning',
                    // offset: 100,
                    // duration:0
                // });
            // },600)
            // setTimeout(()=>{
                // this.msg3=this.$notify({
                    // showClose: true,
                    // message: '如遇数据显示不全请尝试刷新Σ(⊙▽⊙"a',
                    // offset: 100,
                    // type: 'warning',
                    // duration:0
                // });
            // },900)
            // setTimeout(()=>{
                // this.msg4=this.$notify({
                    // message: '尝试使用快捷键！如alt+R来进行适应调整(*^▽^*)',
                    // offset: 100,
                    // type: 'info',
                    // duration:0
                // });
            // },1200)
            // }
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
                that.form.name = resp.data.DB.dbName
                that.form.info = resp.data.DB.info
                that.NavName=resp.data.DB.naviName
            }
        })
        that.asideResize()
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
                    dbName:this.form.name,
                    info:this.form.info,
                    naviStatus:-1,
                })
                const result = axios({
                    method: 'post',
                    url:'/updateDB',
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
                pagesize:that.tbSize,
                graphName:'',
                graphType:''
            })
            const result = axios({
                method: 'post',
                url:'/getGraphList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data.list
                that.tbtotal=resp.data.data.total
                if(that.tbtotal==0) {
                    that.gotoCreate=false
                }
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
        },60000)
        // this.resizeinterval =setInterval(()=>{
            // setTimeout(()=>{
                // this.asideResize();
            // },0)
        // },3000)
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
        let that = this
        setTimeout(() => {
            that.asideResize()
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
        // console.log(localStorage.getItem("showHint"));
        
        
    },
    beforeDestroy() {
        clearInterval(this.interval);
        clearInterval(this.resizeinterval);
        document.removeEventListener('keydown', this.handleEvent);
        document.removeEventListener('keydown', this.handleEvent2);
        document.removeEventListener('keydown', this.handleEvent3);
        document.removeEventListener('keydown', this.handleEvent4);
        // this.msg1.close()
        // this.msg2.close()
        // this.msg3.close()
        // this.msg4.close()
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
    min-height: 100%;
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
//   margin: 0 auto;
    // margin-left: 160px;
//   height: calc(93.6vh);
    width: 100%;
    
}
.tool-bt {
    float: right;
    margin-right: 5px;
    margin-top: 2px;
}
.cover-nav {
    // height: 67px;
    width: 100%;
    // position: absolute;
    // top: 0;
}
.drawer-bt {
    width: 30px;
    height: 100%;
    background-color: rgba(51, 51, 51, 0);
    color: #ddbb80;
    // display: flex;
    // justify-content: center;
    // align-items: center;
    padding-top: 300px;
    // padding-bottom: 120px;
    // text-align: center;
    font-size: 30px;
    position: absolute;
    left: 0;
    border-left:solid 3px #ddbb80;
    z-index:9;
    transition: 300ms 0ms;
}
.drawer-bt:hover {
    cursor:pointer
}

html,
body {
    background-color: #333;
}
.banner4 {
    height: 7%;
    margin-bottom: 10px;
    text-align: right;
}
.search2 {
    margin-right: 10px;
    width: 4rem;
    display: inline-block;
}

</style>