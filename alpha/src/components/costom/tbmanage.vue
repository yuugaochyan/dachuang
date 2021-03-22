<template>
    <div id="app" v-if="reset">
        <div class="maintb">


                <div class="tb-main" id="tb_main">
                <el-tabs type="border-card">

                    <el-tab-pane >
                        <span slot="label"><i class="el-icon-s-tools"></i>可视化管理</span>
                        <div class="banner2">
                            <div class="search">
                            <el-input
                                placeholder="查找可视化"
                                v-model="nameInput"
                                @input="debounce"
                                width:100px>
                                <i slot="prefix" class="el-input__icon el-icon-search" id="input-icon"></i>
                            </el-input>
                            </div>
                            <div class="search">
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
                        :height="clientHeight"
                        :max-height="clientHeight"
                        border
                        style="width: 100%">
                            <el-table-column
                                prop="graphName"
                                label="可视化名字"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="graphType"
                                label="可视化类型"
                                width="580">
                            </el-table-column>
                            <el-table-column
                                label="操作"
                                width="180">
                                <template slot-scope="scope">
                                    <el-tooltip class="item" effect="dark" content="查看这个可视化" placement="bottom-end">
                                        <el-button @click="viewTB(scope.row)" circle type="primary" icon="iconfont icon-more"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="编辑这个可视化" placement="bottom">
                                        <el-button @click="editTB(scope.row)" circle type="warning" icon="iconfont icon-edit"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="删除这个可视化" placement="bottom-start">
                                        <el-button @click="deleteTB(scope.row)" circle type="danger" icon="iconfont icon-delete"></el-button>
                                    </el-tooltip>
                                </template>
                                
                            </el-table-column>
                        </el-table>
                        <!-- //&分页 -->
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
                        
                    </el-tab-pane>
                    
                </el-tabs>
                </div>


            
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import mtqqtest from '@/components/factory/mqttfac/mtqqtest'
export default {
    name:"tbmanage",
    inject:['reload'],
    data() {
        return {
            tbData:[],
            reset:false,
            formLabelWidth: '120px',
            tableHeight:null,
            clientHeight:'',
            //^tb分页
            tbSize:10,
            tbCurrentPage:1,
            tbtotal:0,
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
            timer:null
        }
    },
    methods: {
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
        // inputdebounce:debounce("search",500),
        tbSizeChange(val) {
            this.tbSize=val;
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize,
                graphName:that.nameInput,
                graphType:that.typeInput
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
                pagesize:that.tbSize,
                graphName:that.nameInput,
                graphType:that.typeInput
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
        gotoAddTB() {
            this.$router.push('/createtb')
        },
        
        getTbData() {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize,
                graphName:that.nameInput,
                graphType:that.typeInput
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
        
        viewTB(row) {
            // console.log(row.dbID);
            this.$router.push({
                name:'viewtb',
                params: {
                    tbID:row.graphID,
                    tbName:row.graphName
                }
            })
        },
        editTB(row) {
            // console.log(row.dbID);
            console.log(row.graphType);
            if(row.graphType=='chart') {
                this.$router.push({
                    name:'editchart',
                    params: {
                        tbID:row.graphID,
                        tbName:row.graphName
                    }
                })
            }
            else if(row.graphType=='table') {
                this.$router.push({
                    name:'edittable',
                    params: {
                        tbID:row.graphID,
                        tbName:row.graphName
                    }
                })
            }
            else if(row.graphType=='mqttline') {
                this.$router.push({
                    name:'editmqttline',
                    params: {
                        tbID:row.graphID,
                        tbName:row.graphName
                    }
                })
            }
            else if(row.graphType=='mqttnum') {
                this.$router.push({
                    name:'editmqttnum',
                    params: {
                        tbID:row.graphID,
                        tbName:row.graphName
                    }
                })
            }
        },
        deleteTB(row) {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                graphID:row.graphID,
            })
            const result = axios({
                method: 'post',
                url:'/deleteGraph',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.$message({
                    showClose: true,
                    message: resp.data.msg,
                    center: true,
                    type: 'success'
                });
                if(that.tbData.length==1) {
                    that.tbCurrentPage--;
                }
                that.getTbData()
            }
            })
        },
        
    },
    mounted() {

        this.getTbData();
        const that = this;
        this.clientHeight=localStorage.getItem('clientHeight')-150
        // console.log(this.clientHeight);
        window.onresize = function temp() {
            that.$store.commit(
                "setHeight",
                document.documentElement.clientHeight - 110
            );
            that.clientHeight=localStorage.getItem('clientHeight')-150
        };
        

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
.maintb {
    width: 100%;
    // height: 150%;
    background-color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
}


.tb-main {
    width: 80%;
    margin-top: 22PX;
    margin-bottom: 21px;
    box-shadow: 0 2px 5px #ddbb80ef;
    // border-radius: 0px
    // height: 80%;
}
.iconfont {
    // margin-right: 5px;
    font-size: 20px;
}
.banner2 {
    height: 7%;
    margin-bottom: 10px;
    text-align: right;
}
.search {
    margin-right: 10px;
    width: 4rem;
    display: inline-block;
}

</style>