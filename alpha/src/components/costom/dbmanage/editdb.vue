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
            v-for="(item,index) in layoutData"
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
              {{index}}. {{item.title}}
              <el-tooltip class="item" effect="dark" content="删除这个可视化" placement="bottom-start" v-if="editable">
                <el-button size="mini" @click="deleteTB(item.i)" circle type="del" icon="iconfont icon-delete" class="toolbt"></el-button>
              </el-tooltip>
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
                        <el-button @click="asideResize" type="warning"  plain>适应调整</el-button>
                        <br>
                        <el-divider></el-divider>
                        <el-button @click="editTable=true" type="warning"  plain>编辑问题</el-button>
                        <br>
                        <el-divider></el-divider>
                        <el-button @click="add" type="warning"  plain>新增图表</el-button>
                        <br>
                        <el-divider></el-divider>
                        <el-button @click="savedb" type="warning"  plain>保存问题</el-button>
                        <br>
                        <el-divider></el-divider>
                        <el-button @click="goback" type="warning"  plain>返回列表</el-button>
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
      maxW:6,
      maxH:3,
      reset:false
        }
    },
    methods: {
        handleSelectionChange(val) {
            this.multipleTable=val
        },
        deleteTB(tbID){
      console.log(tbID);
      let that = this;
      let postData=this.$qs.stringify({
          dbID:that.dbID,
          tbID:tbID
      })
      const result = axios({
          method: 'post',
          url:'/deleteTBinDB',
          data:postData
      }).then(function(resp){
          if(resp.data.status==200) {
            that.$message({
              showClose: true,
              message: '删除成功',
              center: true,
              type: 'success'
            });
          }
          that.reload();
      })
      
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
            this.$message({
                    showClose: true,
                    message: '双击屏幕可打开工具箱,仪表盘将会每隔30秒进行自动保存,如遇数据显示不全请尝试刷新',
                    center: true,
                    type: 'info',
                    duration:0
            });
        },
        init() {
      
      let that=this
      let postDta=this.$qs.stringify({
          dbID:this.dbID
      })
      console.log(this.dbID);
      const result = axios({
          method: 'post',
          url:'/getDBitemInfo',
          data:postDta
      }).then(function(resp){
          if(resp.data.status==200) {
              that.layoutData = resp.data.data;
              localStorage.setItem('pandectDisplace', JSON.stringify(that.layoutData));
          }
      })
      
    },
        asideResize(){
            let myEvent = new Event('resize'); // resize是指resize事件
            window.dispatchEvent(myEvent); // 触发window的resize事件
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
        add() {
            this.addTable=true;
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
            })
            const result = axios({
                method: 'post',
                url:'/getTBList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data
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
                    tbIDList.push(this.multipleTable[tb].tbID)
                }
                console.log(tbIDList);

                
                let postData=this.$qs.stringify({
                    
                    dbID:this.dbID,
                    tbList:tbIDList,
                    
                })
                // console.log(postData);
                const result = axios({
                    method: 'post',
                    url:'/addGraphToDB',
                    data:postData
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
            let postData={
                userID:userID,
                items:locaData,
            }
            // console.log(postData);
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
        }
    },
    created() {
        this.interval =setInterval(()=>{
            setTimeout(()=>{
                this.savedb();
            },0)
        },30000)
    },
    mounted() {
        this.getDbData();
    },
    beforeDestroy() {
        clearInterval(this.interval);
    },
    watch: {
        layoutData: function(){
            this.$nextTick(function(){
                this.reset = true;
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
    height: 90%;
    width: 97%;
    background-color: #5b5b5f;
    margin: 0 auto;
}
.title {
    margin: 0 auto;
    text-align: center;
    color: white;
    height: 10%;
    font-size: 150%;
}
.vueGridLayout {
  margin: 0 auto;
  height: calc(93.6vh);
  width: 100%;
}
.toolbt {
  float: right;
  margin: 5px;
}
.el-button--del {
  color: #FFF;
  background-color: #883030;
  border-color: #883030;
}
.el-button--del.is-active,
.el-button--del:active {
  background:#b34141;
  border-color: #883030;
  color: #fff;
}

.el-button--del:focus,
.el-button--del:hover {
  background:#b34141;
  border-color:#883030;
  color: #fff;
}
</style>