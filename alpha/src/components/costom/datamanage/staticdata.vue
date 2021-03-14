<template>
    <div id="app" v-if="reset">
        <div class="mainst">
            <div class="st-main" id="tb_main">
                <el-tabs type="border-card">
                    <el-tab-pane>
                        <span slot="label"><i class="iconfont icon-zidingyi"></i>静态数据管理</span>
                        <div class="banner2">
                            <h1>{{tableName}}</h1>
                            <el-button @click="addRec"  type="warning" icon="iconfont icon-add">新增数据</el-button>
                        </div>
                        <el-table
                        :data="tableData"
                        :height="clientHeight"
                        :max-height="clientHeight"
                        element-loading-text="拼命加载中"
                        border
                        style="width: 100%"
                        >
                            <el-table-column
                                v-for="(item,index) in datacol"
                                :key=index
                                :prop="item"
                                :label="item"
                                width="180">
                                <template slot-scope="scope">
                                    <span v-if="!editable[scope.$index]">{{scope.row[item]}}</span>
                                    <!-- <el-form :model="scope" :rules="rules" ref="editRef" >
                                        <el-form-item prop=""> -->
                                            <el-input v-if="editable[scope.$index]" 
                                            size="small" v-model="scope.row[item]"
                                            :id="item" :disabled="index==0"></el-input>
                                        <!-- </el-form-item>
                                    </el-form> -->
                                </template>
                            </el-table-column>
                            
                            <el-table-column
                                label="操作"
                                width="180"
                                fixed="right">
                                <template slot-scope="scope">
                                    <el-tooltip class="item" effect="dark" content="保存这条修改" placement="bottom-end">
                                        <el-button v-if="editable[scope.$index]" @click="saveRec(scope)" circle type="success" icon="el-icon-check"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="编辑这条记录" placement="bottom">
                                        <el-button @click="editRec(scope)" circle type="warning" icon="iconfont icon-edit"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="删除这条记录" placement="bottom-start">
                                        <el-button v-if="!editable[scope.$index]" @click="deleteRec(scope.row)" circle type="danger" icon="iconfont icon-delete"></el-button>
                                    </el-tooltip>
                                </template>
                                
                            </el-table-column>
                        </el-table>
                        <!-- //&分页 -->
                        <el-pagination
                            background
                            @size-change="SizeChange"
                            @current-change="CurrentChange"
                            :current-page="CurrentPage"
                            :page-sizes="[5, 10, 15, 20]"
                            :page-size="Size"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="total">
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
    name:"staticdata",
    inject:['reload'],
    data() {
        return {
            rules: {
                editItem: [
                    { required: true, message: '不接受空值', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
            },
            tableData:[],
            datacol:[],
            tableID:'',
            tableName:'',
            reset:false,
            
            formLabelWidth: '120px',
            tableHeight:null,
            clientHeight:'',
            //^分页
            Size:5,
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
            let postData=this.$qs.stringify({
                tableID:that.tableID,
                pagenum:that.CurrentPage,
                pagesize:that.Size
            })
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/staticdata',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tableData=resp.data.data.list
            }
            })
        },
        CurrentChange(val) {
            let that = this;
            const userID=localStorage.getItem("userID")
            this.CurrentPage=val;
            let postData=this.$qs.stringify({
                tableID:that.tableID,
                pagenum:that.CurrentPage,
                pagesize:that.Size
            })
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            // console.log(111);
            const result = axios({
                method: 'post',
                url:'/staticdata',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tableData=resp.data.data.list
            }
            })
        },
        
        getData() {
            let that = this;
            // this.reload()
            // const userID=localStorage.getItem("userID")
            this.tableID=this.$route.params.tableID
            // console.log(this.tableID);
            let postData=this.$qs.stringify({
                tableID:that.tableID,
                pagenum:that.CurrentPage,
                pagesize:that.Size
            })
            // console.log(111)
            // console.log(that.dbCurrentPage)
            // console.log(that.dbSize)
            // console.log(userID)
            const result = axios({
                method: 'post',
                url:'/getData',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tableData=resp.data.data.list
                that.total=resp.data.data.total
                that.datacol=resp.data.data.datacol
                that.tableName=resp.data.data.tableName
                for(let key in that.tableData) {
                    that.editable[key]=false
                }
                // that.reload()
            }
            })
        },
        
        editRec(scope) {
            // console.log(scope.$index);
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
                        let box=document.getElementById(key)
                        box.style.cssText="border:solid 0.5px #FF0000"
                        return
                    }
                    else {
                        let box=document.getElementById(key)
                        box.style.cssText=""
                    }
                    // dataList.push(scope.row[key])
                    // console.log(key);
                    dataOb[key]=scope.row[key]
                }
                if(dataOb[this.datacol[0]]=='') {
                    dataOb[this.datacol[0]]=-1
                }
                // console.log(dataOb);
                let postData={
                    tableID:this.tableID,
                    rowIndex:this.datacol[0],
                    row:dataOb
                }
                // console.log(postData);
                const result = axios({
                    method: 'post',
                    url:'/saveData',
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
        deleteRec(row) {
            let that = this;
            const userID=localStorage.getItem("userID")
            // console.log(row[this.datacol[0]]);
            // console.log(this.tableID);
            let postData=this.$qs.stringify({
                tableID:this.tableID,
                id:row[this.datacol[0]],
                rowIndex:this.datacol[0]
            })
            console.log(row[this.datacol[0]]);
            console.log(this.datacol[0]);
            const result = axios({
                method: 'post',
                url:'/deleteData',
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
        addRec() {
            let that = this;
            // console.log(this.editable);
            let length=this.tableData.length
            var obj={}
            // console.log(length);
            for(let key in this.datacol) {
                obj[this.datacol[key]]=''
            }
            this.tableData.push(obj)
            // console.log(this.tableData);
            this.editable.push(true)
            // console.log(this.tableData);
            // console.log(this.editable);
            this.$set(that.editable,length,that.editable[length]);
        }
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
        };
        

    },
    watch: {
        tableData: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        },
        $route(to,from) {
            this.reload()
        }
    }
}
</script>

<style lang="less" scope>
.mainst {
    width: 100%;
    // height: 150%;
    background-color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
}
.st-main {
    width: 80%;
    margin-top: 22PX;
    margin-bottom: 21px;
    box-shadow: 0 2px 5px #ddbb80ef;
    // background-color: #fff;
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

h1 {
    display: inline;
    color: #777;
    float: left;
    padding-left: 20px;
}
</style>