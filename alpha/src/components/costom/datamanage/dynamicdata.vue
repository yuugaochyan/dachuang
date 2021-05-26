<template>
    <div id="app" v-if="reset">
        <div class="maindy">
            <div class="dy-main" id="dy_main">
                <el-tabs type="border-card">
                    <el-tab-pane>
                        <span slot="label"><i class="el-icon-s-tools"></i>实时数据管理</span>
                        <div class="banner3">
                            <h1>MQTT标签管理</h1>
                            <!-- <el-button @click="showAddDB=true"  type="warning" icon="iconfont icon-add">创建仪表盘</el-button> -->
                        </div>
                        <el-table
                        :data="tableData"
                        :height="clientHeight"
                        :max-height="clientHeight"
                        element-loading-text="拼命加载中"
                        border
                        style="width: 100%">
                            <el-table-column
                                v-for="(item,index) in datacol"
                                :key=index
                                :prop="item.va"
                                :label="item.la"
                                width="180">
                                <template slot-scope="scope">
                                    <span v-if="!editable[scope.$index]">{{scope.row[item.va]}}</span>
                                    <!-- <el-form :model="scope" :rules="rules" ref="editRef" >
                                        <el-form-item prop=""> -->
                                            <el-input v-if="editable[scope.$index]" 
                                            size="small" v-model="scope.row[item.va]"
                                            :id="item.va" :disabled="index==0"></el-input>
                                        <!-- </el-form-item>
                                    </el-form> -->
                                </template>
                            </el-table-column>
                            <el-table-column
                                label="操作"
                                width="180">
                                <template slot-scope="scope">
                                    <el-tooltip class="item" effect="dark" content="保存这条修改" placement="bottom-end">
                                        <el-button v-if="editable[scope.$index]" @click="saveRec(scope)" circle type="success" icon="el-icon-check"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="编辑这个标签" placement="bottom">
                                        <el-button @click="editRec(scope)" circle type="warning" icon="iconfont icon-edit"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="处理这个报警" placement="bottom-end">
                                        <el-switch v-model="scope.row.alertStatus" active-color="#13ce66" 
                                    :active-value=1 :inactive-value=0 @change="changStatus(scope)"></el-switch>
                                    </el-tooltip>
                                </template>
                                
                            </el-table-column>
                        </el-table>
                        <!-- //&分页 -->
                        <!-- <el-pagination -->
                            <!-- background -->
                            <!-- @size-change="SizeChange" -->
                            <!-- @current-change="CurrentChange" -->
                            <!-- :current-page="CurrentPage" -->
                            <!-- :page-sizes="[5, 10, 15, 20]" -->
                            <!-- :page-size="Size" -->
                            <!-- layout="total, sizes, prev, pager, next, jumper" -->
                            <!-- :total="total"> -->
                        <!-- </el-pagination> -->
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
    name:"dynamicdata",
    inject:['reload'],
    data() {
        return {
            tableData:[],
            datacol:[{va:"tagID",la:"标签ID"},{va:"tagName",la:"标签名"},{va:"tag",la:"订阅地址"},{va:"highLimit",la:"报警上限"},{va:"lowLimit",la:"报警下限"}],
            tableID:'',
            tableName:'test',
            reset:false,
            
            formLabelWidth: '120px',
            tableHeight:null,
            clientHeight:'',
            //^分页
            Size:10,
            CurrentPage:1,
            total:'',
            editable:[]
        }
    },
    methods: {
        SizeChange(val) {
            this.Size=val;
            let that = this;
            const userID=localStorage.getItem("userID")
            // TODO let postData=this.$qs.stringify({
                // tableID:that.tableID,
                // pagenum:that.CurrentPage,
                // pagesize:that.Size
            //TODO })
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/getTableData',
                // data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tableData=resp.data.data
            }
            })
        },
        CurrentChange(val) {
            let that = this;
            const userID=localStorage.getItem("userID")
            this.CurrentPage=val;
            // let postData=this.$qs.stringify({
                // tableID:that.tableID,
                // pagenum:that.CurrentPage,
                // pagesize:that.Size
            // })
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            // console.log(111);
            const result = axios({
                method: 'post',
                url:'/getMqttTagList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tableData=resp.data.data
            }
            })
        },
        
        getData() {
            let that = this;
            const userID=localStorage.getItem("userID")
            // this.tableID=this.$route.params.tableID
            // let postData=this.$qs.stringify({
                // tableID:that.tableID,
                // pagenum:that.dbCurrentPage,
                // pagesize:that.dbSize
            // })
            // console.log(111)
            // console.log(that.dbCurrentPage)
            // console.log(that.dbSize)
            // console.log(userID)
            const result = axios({
                method: 'post',
                url:'/getMqttTagList',
                // data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tableData=resp.data.data
                for(let key in that.tableData) {
                    that.editable[key]=false
                }
                // that.total=resp.data.data.total
            }
            })
        },
        
        editRec(scope) {
            console.log(scope);
            let that= this
            // scope.row.sellShow=true
            // console.log(this.editable[scope.$index]);
            this.editable[scope.$index]=true
            this.$set(this.editable,scope.$index,this.editable[scope.$index]);
            // this.$set(that.editable,scope.row,true)
            // console.log(this.editable[scope.$index]);
        },
        saveRec(scope) {
            let that = this;
            // const userID=localStorage.getItem("userID")
            // console.log(row[this.datacol[0]]);
            // console.log(this.tableID);
            
                var dataOb={}
                // console.log(scope.row);
                for(let key in scope.row) {
                    if(key=="alertStatus"){continue}
                    // console.log(scope.row[key]);
                    // console.log(key);
                    
                    if(scope.row[key]=='' && key!='tag'&&key!='tagID'&&key!='alertStatus') {
                        
                        // console.log(key);
                        let box=document.getElementById(key)
                        box.style.cssText="border:solid 0.5px #FF0000"
                        return
                    }
                    else if(key!='alertStatus') {
                        let box=document.getElementById(key)
                        box.style.cssText=""
                    }
                    // dataList.push(scope.row[key])
                    // console.log(key);
                    dataOb[key]=scope.row[key]
                }
                
                console.log(dataOb);
                let postData=this.$qs.stringify({
                    // tableID:this.tableID,
                    // rowIndex:this.datacol[0],
                    // row:dataOb
                    tagID:dataOb.tagID,
                    tagName:dataOb.tagName,
                    highLimit:dataOb.highLimit,
                    lowLimit:dataOb.lowLimit,
                    alertStatus:-1,
                })
                // console.log(postData);
                const result = axios({
                    method: 'post',
                    url:'/updateMqttTag',
                    data:postData
                }).then(function(resp){
                    if(resp.data.status==200) {
                    that.$message({
                        showClose: true,
                        message: resp.data.msg,
                        center: true,
                        type: 'success'
                    });
                    that.editable[scope.$index]=false
                    that.$set(that.editable,scope.$index,that.editable[scope.$index]);
                    that.getData()
                    }
                    else if(resp.data.status==400) {
                    that.$message({
                        showClose: true,
                        message: resp.data.msg,
                        center: true,
                        type: 'error'
                    });
                    }
                })
            
        },
        changStatus(scope){
            let that = this
            
                this.$messagebox.confirm('要改变是否报警吗', '确认', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let postDta=this.$qs.stringify({
                        tagID:scope.row.tagID,
                    tagName:'',
                    highLimit:1000,
                    lowLimit:-1000,
                    alertStatus:scope.row.alertStatus,
                        })
                        console.log(postDta);
                        const result = axios({
                            method: 'post',
                            url:'/updateMqttTag',
                            data:postDta
                        }).then(function(resp){
                            if(resp.data.status==200) {
                                that.reload()
                                that.$message({
                                showClose: true,
                                message: resp.data.msg,
                                center: true,
                                type: 'success'
                                });
                            }
                        })
                }).catch(() => {
                    this.$message({
                    type: 'info',
                    message: '已取消改变'
                    });
                    if(scope.row.alertStatus==1){
                        scope.row.alertStatus=0;
                    }
                    else{
                        scope.row.alertStatus=1
                    }
                });
                
                        
                    
                
            
        },
        
    },
    mounted() {
        
            // this.reload()
        
        this.getData();
        // console.log(this.dbData);
        // console.log(this.dbData);
        const that = this;
        this.clientHeight=localStorage.getItem('clientHeight')-125
        // console.log(this.clientHeight);
        window.onresize = function temp() {
            that.$store.commit(
                "setHeight",
                document.documentElement.clientHeight - 110
            );
            that.clientHeight=localStorage.getItem('clientHeight')-125
        };
        

    },
    watch: {
        tableData: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        }
    }
}
</script>

<style lang="less" scope>
.maindy {
    width: 100%;
    // height: 150%;
    background-color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
}
.dy-main {
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
.banner3 {
    height: 7%;
    margin-bottom: 10px;
    text-align: right;
}

.banner3>h1 {
    float: left;
    display: inline;
    color: #777;
    padding-left: 20px;
}
</style>