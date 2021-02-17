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
            
            
            >
            
            
                <div class="title">
                    {{item.title}}
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
                        <el-tooltip class="item" effect="dark" content="调整图表位置（alt+R）" placement="bottom-end">
                            <el-button @click="asideResize" type="warning"  plain>适应调整</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="前往编辑界面(alt+G）" placement="bottom-end">
                            <el-button @click="gotoedit" type="warning"  plain>前往编辑</el-button>
                        </el-tooltip>
                        <br>
                        <el-divider></el-divider>
                        <el-tooltip class="item" effect="dark" content="返回仪表盘管理页面" placement="bottom-end">
                            <el-button @click="goback" type="warning"  plain>返回列表</el-button>
                        </el-tooltip>
                    </div>
                </el-drawer>

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
    name:"viewdb",
    data() {
        return {
            dbData:[],
            dbID:'',
            dbName:'',
            dbInfo:'',
            // reset:false,
            drawer:false,
            direction: 'rtl',
            editable:false,
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
            reset:false,
            msg1:'',
            msg2:'',
            msg3:'',
        }
    },
    methods: {
        getDbData() {
            let that = this;
            this.dbID = this.$route.params.dbID
            this.dbName = this.$route.params.dbName
            this.dbInfo = this.$route.params.dbInfo
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
                    message: '如遇数据显示不全请尝试刷新Σ(⊙▽⊙"a',
                    offset: 100,
                    type: 'warning',
                    duration:0
                });
            },600)
            setTimeout(()=>{
                this.msg3=this.$notify({
                    message: '尝试使用快捷键！如alt+R来进行适应调整(*^▽^*)',
                    offset: 100,
                    type: 'info',
                    duration:0
                });
            },900)
        },
        asideResize(){
            let myEvent = new Event('resize'); // resize是指resize事件
            window.dispatchEvent(myEvent); // 触发window的resize事件
        },
        gotoedit() {
            this.$router.push({
                name:'editdb',
                params: {
                    dbID:this.dbID,
                    dbName:this.dbName,
                    dbInfo:this.dbInfo
                }
            })
        },
        goback() {
            this.$router.push('/createdb')
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
            }
        })
        
        },
        handleEvent(e){
            var that = this;
            //alt+F  
            let key = window.event.keyCode;  
            if (71 == e.keyCode && e.altKey) {
                //~如果是alt+G 的操作的话，方法执行与此
                that.gotoedit()
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
    },
    created() {
        this.getDbData();
        document.addEventListener('keydown',this.handleEvent)
        document.addEventListener('keydown',this.handleEvent2)
    },
    mounted() {
        setTimeout(()=>{
            this.asideResize()
        },500)
    },
    beforeDestroy() {
        document.removeEventListener('keydown', this.handleEvent);
        document.removeEventListener('keydown', this.handleEvent2);
        this.msg1.close()
        this.msg2.close()
        this.msg3.close()
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
</style>