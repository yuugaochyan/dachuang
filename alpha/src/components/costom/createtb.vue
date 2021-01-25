<template>
    <div id="app">
        <div class="main">
            <div class="as">
                <transition name="el-zoom-in-bottom" appear="true" >
                <el-button type="warning" v-if="!stmo" class="bt" @click="goback">
                    <i class="el-icon-caret-left"></i>
                    上一步
                </el-button>
                </transition>
            </div>
            <div class="col">
                
                <div class="but">
                    <transition name="el-zoom-in-top" :appear=true>
                    <div class="sta con" v-if="stmo" @click="staf">
                        <h1>静态数据</h1>
                        <i class="iconfont icon-jurassic_data"></i>
                    </div>
                    </transition>

                    <transition name="el-zoom-in-top" :appear=true>
                    <div class="cht con" v-if="chtb" @click="chartvisible=true">
                        <h1>chart图表</h1>
                        <i class="iconfont icon-jurassic_chart"></i>
                    </div>
                    </transition>
                    
    <el-dialog title="收货地址" :visible.sync="chartvisible">
        <el-form :model="chartform">
            <el-form-item label="活动名称" :label-width="formLabelWidth">
                <el-input v-model="chartform.name" autocomplete="off"></el-input>
            </el-form-item>
            
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="chartvisible = false">取 消</el-button>
            <el-button type="primary" @click="chartvisible = false">确 定</el-button>
        </div>
    </el-dialog>


                    <transition name="el-zoom-in-top" :appear=true>
                    <div class="mql con" v-if="mqln" @click="mqttlinevisible=true">
                        <h1>时间轴图表</h1>
                        <i class="iconfont icon-zhexiantu"></i>
                    </div>
                    </transition>
                </div>
                
            </div>
            <div class="col">
                <div class="but">
                    <transition name="el-zoom-in-top" :appear=true>
                    <div class="mov con" v-if="stmo" @click="movf">
                        <h1>动态数据</h1>
                        <i class="iconfont icon-yun1"></i>
                    </div>
                    </transition>

                    <transition name="el-zoom-in-top" :appear=true>
                    <div class="tb con" v-if="chtb" @click="tablevisible=true">
                        <h1>table表格</h1>
                        <i class="iconfont icon-biaoge"></i>
                    </div>
                    </transition>

                    <transition name="el-zoom-in-top" :appear=true>
                    <div class="mtn con" v-if="mqln" @click="mqttnumvisible=true">
                        <h1>数字翻牌器</h1>
                        <i class="iconfont icon-shuzi"></i>
                    </div>
                    </transition>
                </div>
            </div>
            <div class="as"></div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import mtqqtest from '@/components/factory/mqttfac/mtqqtest'
export default {
    name:"createtb",
    data() {
        return {
            stmo:true,
            chtb:false,
            mqln:false,
            chartvisible:false,
            tablevisible:false,
            mqttlinevisible:false,
            mqttnumvisible:false,
            chartform:{
                name:'',
            },
            formLabelWidth: '120px'
        }
    },
    methods: {
        staf() {
            this.stmo=false,
            this.chtb=true
        },
        movf() {
            this.stmo=false,
            this.mqln=true
        },
        goback() {
            this.stmo=true;
            this.chtb=false;
            this.mqln=false;
        }
    }
}
</script>

<style lang="less" scoped>
.main {
    width: 100%;
    height: 1500px;
    background-color: #333;
    display: flex;

}
.col {
    display: flex;
    flex:2;
    // background-color: #666;
    position: relative;
    height: 100%;
}
.but {
    height: 450px;
    width: 80%;
    background-color: #ddbb80;
    position: absolute;
    left:10%;
    right:10%;
    top:10%;
    border-radius: 25%;
    box-shadow: 15px -15px 15px #1a1a1a;
    // border: 1px solid #000;
    text-align: center;
}
.but:hover {
    background-color: #e5a13c;
    box-shadow: 15px 15px 15px #1a1a1a;
    cursor:pointer
}

.as {
    display: flex;
    flex: 1;
    background-color: #1a1a1a;
    //border-left: 1px solid #000;
    //border-right: 1px solid #000;
}
.con {
    width: 100%;
    height: 415px;
    border-radius: 25%;
}

h1 {
    font-size: 60px;
    color: #343434;
    margin-top: 75px;
}

.iconfont {
    font-size: 150px;
    color: #343434;
}
.bt {
    // padding: 70px;
    margin: 0 auto;
    // height: 70px;
    margin-top: 200px;
    font-size: 20px;
}
// .sta {
    // background-image: url("../../assets/pic/db.jpg");
    // background-repeat: no-repeat;
    // background-position: 0 70px;
    // border-radius: 50%;
    // background-size: contain;
// }
// .mov {
    // background-image: url("../../assets/pic/mqtt.jpg");
    // background-repeat: no-repeat;
    // background-position: 0 70px;
    // background-size: contain;
    // border-radius: 27%;
// }
// 
// .cht {
    // background-image: url("../../assets/pic/cht.jpg");
    // background-repeat: no-repeat;
    // background-position: 0 60px;
    // background-size: contain;
    // border-radius: 25%;
// }
// 
// .tb {
    // background-image: url("../../assets/pic/table.jpg");
    // background-repeat: no-repeat;
    // background-position: -20px 100px;
    // background-size: fill;
    // border-radius: 25%;
// }
// 
// .mql {
    // background-image: url("../../assets/pic/mqttline.jpg");
    // background-repeat: no-repeat;
    // background-position: -20px 100px;
    // background-size: fill;
    // border-radius: 25%;
// }
// 
// .mtn {
    // background-image: url("../../assets/pic/mqttnum.png");
    // background-repeat: no-repeat;
    // background-position: 0 100px;
    // background-size: contain;
    // border-radius: 25%;
// }
</style>