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
                        height="700"
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
                    </el-tab-pane>
                    <el-tab-pane >
                        <span slot="label"><i class="iconfont icon-keshihua"></i>可视化管理</span>
                        <div class="banner2">
                            <el-button @click="gotoAddTB"  type="warning" icon="iconfont icon-add">创建可视化</el-button>
                        </div>
                        <el-table
                        :data="tbData"
                        height="700"
                        border
                        style="width: 100%">
                            <el-table-column
                                prop="name"
                                label="可视化名字"
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
        }
    },
    methods: {
        gotoAddTB() {
            this.$router.push('/createtb')
        },
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
        getTbData() {
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
        viewDB(row) {
            // console.log(row.dbID);
            this.$router.push({
                name:'viewdb',
                params: {
                    dbID:row.dbID,
                    dbName:row.name,
                    dbInfo:row.info
                }
            })
        },
        editDB(row) {
            this.$router.push({
                name:'editdb',
                params: {
                    dbID:row.dbID,
                    dbName:row.name,
                    dbInfo:row.info
                }
            })
        },
        deleteDB(row) {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
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
                    message: '已经删除成功',
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
                    tbID:row.tbID,
                    tbName:row.name
                }
            })
        },
        editTB(row) {
            // console.log(row.dbID);
            this.$router.push({
                name:'edittb',
                params: {
                    tbID:row.tbID,
                    tbName:row.name
                }
            })
        },
        deleteTB(row) {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                tbID:row.tbID,
            })
            const result = axios({
                method: 'post',
                url:'/deleteTB',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.$message({
                    showClose: true,
                    message: '已经删除成功',
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
        this.getDbData();
        this.getTbData();
        console.log(this.dbData);
        console.log(this.dbData);
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
    margin-right: 40PX;
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