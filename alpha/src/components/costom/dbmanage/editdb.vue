<template>
    <div id="app" >
        <div class="maincontain" @dblclick="drawer = true">
            

                <!-- <el-tooltip class="item" effect="dark" content="隐藏调整按钮" placement="bottom-start"> -->
                    <!-- <el-button @click="drawer = true" circle type="warning" icon="iconfont icon-preview" style="margin-left: 16px;"></el-button> -->
                <!-- </el-tooltip> -->

                <griddemo :dbID="dbID" :editable=true></griddemo>
                
                
                
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
                            prop="name"
                            label="可视化名字"
                            width="150">
                        </el-table-column>
                        <el-table-column
                            prop="info"
                            label="描述"
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
import griddemo from '@/components/costom/griddemo'
export default {
    components: { griddemo },
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
            interval:''
        }
    },
    methods: {
        handleSelectionChange(val) {
            this.multipleTable=val
        },
        getDbData() {
            let that = this;
            this.dbID = this.$route.params.dbID
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
            this.$message({
                    showClose: true,
                    message: '双击屏幕可打开工具箱,仪表盘将会每隔30秒进行自动保存,如遇数据显示不全请尝试刷新',
                    center: true,
                    type: 'info'
            });
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
                    userID:userID,
                    dbID:this.dbID,
                    tbList:tbIDList,
                    
                })
                // console.log(postData);
                const result = axios({
                    method: 'post',
                    url:'/getTBList',
                    data:postData
                }).then(function(resp){
                    if(resp.data.status==200) {
                        that.$notify({
                            
                            message: '添加成功',
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
                data:locaData,
            }
            // console.log(postData);
            const result = axios({
                method: 'post',
                url:'/saveDB',
                data:postData
            }).then(function(resp){
                
                if(resp.data.status==200) {
                    that.$notify({
                        
                        message: '保存成功',
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