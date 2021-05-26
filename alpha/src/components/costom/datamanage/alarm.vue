<template>
    <div id="app" v-if="reset">
        <div class="mainal">


                <div class="al-main" id="tb_main">
                <el-tabs type="border-card">

                    <el-tab-pane >
                        <span slot="label"><i class="el-icon-s-tools"></i>报警管理</span>
                        <div class="banner2">
                            <div class="search">
                            <el-input
                                placeholder="查找数据"
                                v-model="nameInput"
                                @input="debounce"
                                width:100px>
                                <i slot="prefix" class="el-input__icon el-icon-search" id="input-icon"></i>
                            </el-input>
                            </div>
                            <div class="search">
                            <el-select v-model="typeInput" placeholder="请选择状态类型" @change="debounce">
                                <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                                <i slot="prefix" class="el-input__icon el-icon-search" id="select-icon"></i>
                            </el-select>
                            </div>
                            <el-tooltip class="item" effect="dark" content="批量删除" placement="bottom-start">
                                <el-button  @click="deletemuti()"  type="danger" icon="iconfont icon-delete">批量删除</el-button>
                            </el-tooltip>
                            <!-- <el-button @click="gotoAddTB"  type="warning" icon="iconfont icon-add">创建可视化</el-button> -->
                        </div>
                        <el-table
                        ref="multipleSelection"
                        :data="tbData"
                        :height="clientHeight"
                        :max-height="clientHeight"
                        border
                        style="width: 100%"
                        @selection-change="handleSelectionChange">
                            <el-table-column
                            type="selection"
                            width="55">
                            </el-table-column>
                            <el-table-column
                                prop="alertID"
                                label="报警ID"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="dataName"
                                label="数据名"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="value"
                                label="值"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="time"
                                label="报警时间"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="msg"
                                label="报警信息"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                label="操作"
                                width="180">
                                <template slot-scope="scope">
                                    <el-tooltip class="item" effect="dark" content="删除这个报警" placement="bottom-start">
                                        <el-button @click="deleteTB(scope.row)" circle type="danger" icon="iconfont icon-delete"></el-button>
                                    </el-tooltip>
                                    <!-- <span style="width:5px"></span> -->
                                    <el-divider direction="vertical"></el-divider>
                                    <el-tooltip class="item" effect="dark" content="处理这个报警" placement="bottom-end">
                                        <el-switch v-model="scope.row.checkStatus" active-color="#13ce66" 
                                    :active-value=1 :inactive-value=0 @change="changStatus(scope)"></el-switch>
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
            //^分页
            tbSize:10,
            tbCurrentPage:1,
            tbtotal:0,
            //^搜索
            nameInput:'',
            typeInput:-1,
            options: [{
                value: 1,
                label: '已处理'
            }, {
                value: 0,
                label: '未处理'
            }, {
                value: -1,
                label: '所有状态'
            }],
            timer:null,
            multipleSelection: [],
            interval:''
        }
    },
    methods: {
        search() {
            let that = this
            // const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize,
                dataName:that.nameInput,
                checkStatus:that.typeInput
            })
            // console.log(postData);
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/getAlertList',
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
            let that = this
            // const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize,
                dataName:that.nameInput,
                checkStatus:that.typeInput
            })
            // console.log(postData);
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/getAlertList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data.list
                that.tbtotal=resp.data.data.total
            }
            })
        },
        tbCurrentChange(val) {
            let that = this;
            const userID=localStorage.getItem("userID")
            this.tbCurrentPage=val;
            let postData=this.$qs.stringify({
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize,
                dataName:that.nameInput,
                checkStatus:that.typeInput
            })
            // console.log(postData);
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/getAlertList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data.list
                that.tbtotal=resp.data.data.total
            }
            })
        },
        
        
        getTbData() {
            let that = this
            // const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                pagenum:that.tbCurrentPage,
                pagesize:that.tbSize,
                dataName:that.nameInput,
                checkStatus:that.typeInput
            })
            // console.log(postData);
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/getAlertList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data.list
                that.tbtotal=resp.data.data.total
            }
            })
        },
        
        
        deleteTB(row) {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                alertID:row.alertID
            })
            const result = axios({
                method: 'post',
                url:'/deleteAlerts',
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
        deletemuti(){
            let that = this;
            // const userID=localStorage.getItem("userID")
            console.log(that.multipleSelection);
            var list=[]
            for(let key in that.multipleSelection){
                // console.log(key);
                list[key]=that.multipleSelection[key].alertID
            }
            console.log(list);
            let postData=this.$qs.stringify({
                alertID:list
            },{indices:false})
            const result = axios({
                method: 'post',
                url:'/deleteAlerts',
                data:postData,
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
        changStatus(scope){
            let that = this
            
                this.$messagebox.confirm('要改变这条报警的状态吗', '确认', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let postDta=this.$qs.stringify({
                        alertID:scope.row.alertID,
                        checkStatus:scope.row.checkStatus
                        })
                        console.log(postDta);
                        const result = axios({
                            method: 'post',
                            url:'/editAlert',
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
                    if(scope.row.checkStatus==1){
                        scope.row.checkStatus=0;
                    }
                    else{
                        scope.row.checkStatus=1
                    }
                });
                
                        
                    
                
            
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        }
    },
    created(){
        this.getTbData();
        this.interval =setInterval(()=>{
            setTimeout(()=>{
                this.getTbData();
            },0)
        },60000)
    },
    mounted() {
        

        
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
.mainal {
    width: 100%;
    // height: 150%;
    background-color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
}


.al-main {
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