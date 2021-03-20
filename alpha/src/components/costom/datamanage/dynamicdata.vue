<template>
    <div id="app" v-if="reset">
        <div class="maindy">
            <div class="dy-main" id="dy_main">
                <el-tabs type="border-card">
                    <el-tab-pane>
                        <span slot="label"><i class="el-icon-s-tools"></i>实时数据管理</span>
                        <div class="banner2">
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
                                prop="tag"
                                label="标签"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="tagName"
                                label="标签名字"
                                width="580">
                                <template slot-scope="scope">
                                <span v-if="!editable[scope.$index]">{{scope.row.tagName}}</span>
                                    
                                    <el-input v-if="editable[scope.$index]" 
                                    size="small" v-model="scope.row.tagName"
                                    :id="scope.row.tagID"></el-input>
                                    </template>
                            </el-table-column>
                            <el-table-column
                                label="操作"
                                width="180">
                                <template slot-scope="scope">
                                    <el-tooltip class="item" effect="dark" content="保存这条修改" placement="bottom-end">
                                        <el-button v-if="editable[scope.$index]" @click="saveRec(scope)" circle type="success" icon="el-icon-check"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="编辑这个仪表盘" placement="bottom">
                                        <el-button @click="editRec(scope)" circle type="warning" icon="iconfont icon-edit"></el-button>
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
            datacol:[],
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
                    // console.log(scope.row[key]);
                    // console.log(key);
                    if(scope.row[key]=='' && key!=this.datacol[0]) {
                        
                        // console.log(scope.$index);
                        let box=document.getElementById(scope.row.tagID)
                        box.style.cssText="border:solid 0.5px #FF0000"
                        return
                    }
                    else {
                        let box=document.getElementById(scope.row.tagID)
                        box.style.cssText=""
                    }
                    // dataList.push(scope.row[key])
                    // console.log(key);
                    dataOb[key]=scope.row[key]
                }
                if(dataOb[this.datacol[0]]=='') {
                    dataOb[this.datacol[0]]=-1
                }
                console.log(dataOb);
                let postData=this.$qs.stringify({
                    // tableID:this.tableID,
                    // rowIndex:this.datacol[0],
                    // row:dataOb
                    tagID:dataOb.tagID,
                    tagName:dataOb.tagName
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
        
    },
    mounted() {
        
            // this.reload()
        
        this.getData();
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
            that.clientHeight=localStorage.getItem('clientHeight')-150
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
.banner2 {
    height: 7%;
    margin-bottom: 10px;
    text-align: left;
}

.h1 {
    display: inline;
    color: #777;
}
</style>