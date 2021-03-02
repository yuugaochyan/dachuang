<template>
    <div id="app" v-if="reset">
        <div class="maindb">
            <div class="dbcol1"></div>
            <div class="dbcol2">
                <div class="banner"></div>
                <el-tabs type="border-card">
                    <el-tab-pane>
                        <span slot="label"><i class="iconfont icon-zidingyi"></i>仪表盘管理</span>
                        <div class="banner2">
                            <el-button @click="showAddDB=true"  type="warning" icon="iconfont icon-add">创建仪表盘</el-button>
                        </div>
                        <el-table
                        :data="dbData"
                        :height="clientHeight"
                        :max-height="clientHeight"
                        element-loading-text="拼命加载中"
                        border
                        style="width: 100%">
                            <el-table-column
                                prop="dbName"
                                label="仪表盘名字"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="info"
                                label="描述"
                                width="580">
                            </el-table-column>
                            <el-table-column
                                label="操作"
                                width="180">
                                <template slot-scope="scope">
                                    <el-tooltip class="item" effect="dark" content="查看这个仪表盘" placement="bottom-end">
                                        <el-button @click="viewDB(scope.row)" circle type="primary" icon="iconfont icon-more"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="编辑这个仪表盘" placement="bottom">
                                        <el-button @click="editDB(scope.row)" circle type="warning" icon="iconfont icon-edit"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="删除这个仪表盘" placement="bottom-start">
                                        <el-button @click="deleteDB(scope.row)" circle type="danger" icon="iconfont icon-delete"></el-button>
                                    </el-tooltip>
                                </template>
                                
                            </el-table-column>
                        </el-table>
                        <!-- //&分页 -->
                        <el-pagination
                            background
                            @size-change="dbSizeChange"
                            @current-change="dbCurrentChange"
                            :current-page="dbCurrentPage"
                            :page-sizes="[5, 10, 15, 20]"
                            :page-size="dbSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="dbtotal">
                        </el-pagination>
                    </el-tab-pane>

                    <el-tab-pane >
                        <span slot="label"><i class="iconfont icon-keshihua"></i>可视化管理</span>
                        <div class="banner2">
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

            <el-dialog title="创建新的仪表盘（问题）" :visible.sync="showAddDB">
                    <el-form :model="newDB" :rules="rules" ref="chartformref">
                        <el-form-item label="仪表盘名称" :label-width="formLabelWidth" prop="name">
                            <el-input v-model="newDB.name"  placeholder="长度1~15"></el-input>
                        </el-form-item>
                        <el-form-item label="仪表盘摘要" :label-width="formLabelWidth">
                            <el-input v-model="newDB.info"  placeholder="这不是必须的 但很有用"></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="showAddDB = false">取 消</el-button>
                        <el-button type="primary" @click="addDB">确 定</el-button>
                    </div>
                </el-dialog>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import mtqqtest from '@/components/factory/mqttfac/mtqqtest'
export default {
    name:"createdb",
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
            tbData:[],
            reset:false,
            newDB:{
                name:'',
                info:'',
            },
            showAddDB:false,
            formLabelWidth: '120px',
            tableHeight:null,
            clientHeight:'',
            //^db分页
            dbSize:5,
            dbCurrentPage:1,
            dbtotal:'',
            //^db分页
            tbSize:5,
            tbCurrentPage:1,
            tbtotal:'',
        }
    },
    methods: {
        dbSizeChange(val) {
            this.dbSize=val;
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.dbCurrentPage,
                pagesize:that.dbSize
            })
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
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
        dbCurrentChange(val) {
            let that = this;
            const userID=localStorage.getItem("userID")
            this.dbCurrentPage=val;
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.dbCurrentPage,
                pagesize:that.dbSize
            })
            console.log(this.dbCurrentPage);
            console.log(this.dbSize);
            console.log(111);
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
                that.tbData=resp.data.data
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
                that.tbData=resp.data.data
            }
            })
        },
        gotoAddTB() {
            this.$router.push('/createtb')
        },
        getDbData() {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.dbCurrentPage,
                pagesize:that.dbSize
            })
            console.log(111)
            console.log(that.dbCurrentPage)
            console.log(that.dbSize)
           console.log(userID)
            const result = axios({
                method: 'post',
                url:'/getDBList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.dbData=resp.data.data
                that.dbtotal=that.dbData.length
            }
            })
        },
        getTbData() {
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
                that.tbData=resp.data.data
                that.tbtotal=that.tbData.length
            }
            })
        },
        viewDB(row) {
            // console.log(row.dbID);
            this.$router.push({
                name:'viewdb',
                params: {
                    dbID:row.dbID,
                    dbName:row.dbName,
                    dbInfo:row.info
                }
            })
        },
        editDB(row) {
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            // location.reload();
            setTimeout(() => {
                loading.close();
            }, 1000);
            
            this.$router.push({
                name:'editdb',
                params: {
                    dbID:row.dbID,
                    dbName:row.dbName,
                    dbInfo:row.info
                }
            })
            
        },
        deleteDB(row) {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                dbID:row.dbID,
            })
            const result = axios({
                method: 'post',
                url:'/deleteDB',
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
                tbID:row.graphID,
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
            }
            })
        },
        addDB() {
            let that = this;
            this.$refs.chartformref.validate((valid)=>{
                if(!valid) return;
                const userID=localStorage.getItem("userID")
                let postData=this.$qs.stringify({
                    userID:userID,
                    
                    dbName:this.newDB.name,
                    info:this.newDB.info
                    
                })
                const result = axios({
                    method: 'post',
                    url:'/addDB',
                    data:postData
                }).then(function(resp){
                    if(resp.data.status==200) {
                    that.$message({
                        showClose: true,
                        message: resp.data.msg,
                        center: true,
                        type: 'success'
                    });
                    that.reload()
                }
                })
            })
        }
    },
    mounted() {
        const loading = this.$loading({
            lock: true,
            text: '拼命加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        });
            // this.reload()
            
        this.getDbData();
        this.getTbData();
        // console.log(this.dbData);
        // console.log(this.dbData);
        const that = this;
        this.clientHeight=localStorage.getItem('clientHeight')-150
        console.log(this.clientHeight);
        window.onresize = function temp() {
            that.$store.commit(
                "setHeight",
                document.documentElement.clientHeight - 110
            );
        };
        setTimeout(() => {
                loading.close();
        }, 1000);

    },
    watch: {
        dbData: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        }
    }
}
</script>

<style lang="less" scope>
.maindb {
    width: 100%;
    height: calc(93.6vh);
    background-color: #fff;
    display: flex;
}
.dbcol1 {
    flex:2;
    // margin-right: 40PX;
    background-color:  #ddbb80;
    height: 100%;
}
.dbcol2 {
    flex: 9;
    height: 100%;
    background-color: #fff;
    padding-right: 40px;
}
.banner {
    height: 5%;
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
</style>