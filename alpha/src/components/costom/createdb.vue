<template>
    <div id="app" v-if="reset">
        <div class="maindb">
            <div class="db-main" id="tb_main">
                <el-tabs type="border-card">
                    <el-tab-pane>
                        <span slot="label"><i class="el-icon-s-tools"></i>仪表盘管理</span>
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
                                width="380">
                            </el-table-column>
                            <el-table-column
                                prop="naviName"
                                label="导航名称"
                                width="180">
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
                            <el-table-column
                                label="是否推送到导航"
                                width="180">
                                <template slot-scope="scope">
                                    <el-switch v-model="scope.row.naviStatus" active-color="#13ce66" 
                                    :active-value=1 :inactive-value=0 @change="changNavi(scope)"></el-switch>
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

                <el-dialog title="导航栏中的名字" :visible.sync="pushToNavVis">
                    <!-- <el-form :model="nav" :rules="rule2" ref="chartformref2" > -->
                        <!-- <el-form-item label="仪表盘名称" :label-width="formLabelWidth" prop="name"> -->
                            <el-input v-model="NavName" autocomplete="off" id="navInput"></el-input>
                        <!-- </el-form-item> -->
                    <!-- </el-form> -->
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="expush">取 消</el-button>
                        <el-button type="primary" @click="pushToNav">确 定</el-button>
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
            dbSize:10,
            dbCurrentPage:1,
            dbtotal:'',
            pushToNavVis:false,
            NavName:'',
            dbID:''
        }
    },
    methods: {
        expush() {
            this.pushToNavVis=false
            for(let key in this.dbData) {
                // console.log(this.dbData[key]);
                if(this.dbData[key].dbID==this.dbID) {
                    this.dbData[key].naviStatus=0
                }
            }
        },
        changNavi(scope) {
            // console.log(scope.row.naviStatus==1);
            let that = this
            if(scope.row.naviStatus==1) {
            this.pushToNavVis=true
            this.NavName=scope.row.naviName
            this.dbID=scope.row.dbID
            }
            else {
                this.$messagebox.confirm('要取消推送到导航吗', '确认', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let postDta=this.$qs.stringify({
                        dbID:scope.row.dbID,
                        // userID:userID,
                        naviName:scope.row.naviName,
                        naviStatus:0,
                        })
                        // console.log(postDta);
                        const result = axios({
                            method: 'post',
                            url:'/updateDB',
                            data:postDta
                        }).then(function(resp){
                            if(resp.data.status==200) {
                                that.reload()
                            }
                        })
                }).catch(() => {
                    this.$message({
                    type: 'info',
                    message: '已取消删除'
                    });
                    scope.row.naviStatus=1
                });
                
                        
                    
                
            }
        },
        pushToNav() {
            let that=this
            if(this.NavName=='') {
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
                that.dbData=resp.data.data.list
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
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            // console.log(111);
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
        
        getDbData() {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
                pagenum:that.dbCurrentPage,
                pagesize:that.dbSize
            })
            // console.log(111)
            // console.log(that.dbCurrentPage)
            // console.log(that.dbSize)
            // console.log(userID)
            const result = axios({
                method: 'post',
                url:'/getDBList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.dbData=resp.data.data.list
                that.dbtotal=resp.data.data.total
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
                if(that.dbData.length==1) {
                    that.dbCurrentPage--;
                }
                that.reload()
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
                    that.getDbData()
                    that.showAddDB = false
                }
                })
            })
        }
    },
    mounted() {
        
            // this.reload()
            
        this.getDbData();
        // console.log(this.dbData);
        // console.log(this.dbData);
        const that = this;
        this.clientHeight=localStorage.getItem('clientHeight')-150
        // console.log(this.clientHeight);
        window.onresize = function temp() {
            that.$store.commit(
                "setHeight",
                document.documentElement.clientHeight - 110
            );
        };
        

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
    // height: 150%;
    background-color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
}
.db-main {
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
</style>