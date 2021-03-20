<template>
    <div id="app">
        <div class="maintb">
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
                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="stmo" @click="staf">
                        <h2>历史数据</h2>
                        <!-- <i class="iconfont icon-jurassic_data"></i> -->
                        <i class="iconfont icon-jurassic_data"></i>
                        <!-- <i></i> -->
                    </div>
                    
                    </transition>

                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="chtb" @click="chartcreate">
                        <h2>chart图表</h2>
                        <i class="iconfont icon-jurassic_chart"></i>
                    </div>
                    </transition>
                    
    



                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="mqln" @click="mqttlinecreate">
                        <h2>时间轴图表</h2>
                        <i class="iconfont icon-zhexiantu"></i>
                    </div>
                    </transition>
                </div>
                
            </div>
            <div class="col">
                <div class="but">
                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="stmo" @click="movf">
                        <h2>实时数据</h2>
                        <i class="iconfont icon-yun1"></i>
                    </div>
                    </transition>

                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="chtb" @click="tablecreate">
                        <h2>table表格</h2>
                        <i class="iconfont icon-biaoge"></i>
                    </div>
                    </transition>

                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="mqln" @click="mqttnumcreate">
                        <h2>数字翻牌器</h2>
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
import createchart from './formcreate/createchart.vue'
export default{
    components: { createchart }, 
    name:"createtb",
    data() {
        return {
            stmo:true,
            chtb:false,
            mqln:false,


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
        },
        chartcreate() {
            this.$router.push('/createchart')
        },
        tablecreate() {
            this.$router.push('/createtable')
        },
        mqttlinecreate() {
            this.$router.push('/createmqttline')
        },
        mqttnumcreate() {
            this.$router.push('/createmqttnum')
        }
    }
}
</script>

<style lang="less" scoped>
.maintb {
    width: 100%;
    height: calc(93.6vh);
    background-color: #333;
    display: flex;
    justify-content: center;
    align-items: center;

}
.col {
    // display: flex;
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
    left:50%;
    // right:10%;
    top:50%;
    transform: translate(-50%,-50%);
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
    // background-color: #1a1a1a;
    //border-left: 1px solid #000;
    //border-right: 1px solid #000;
}
.con {
    width: 100%;
    height: 415px;
    border-radius: 25%;
    // position: relative;
    // text-align: center;
    // padding: 25px;
}

.con>h2 {
    font-size: 60px;
    color: #343434;
    padding-top: 75px;
    margin: 0 auto;
    // padding-left: 25%;
    // position: absolute;
    // top: 20%;
    // left: 50%;
    // transform: translate(-50%,-20%);
    // display: block;
}

.iconfont {
    font-size: 160px;
    color: #343434;
}
.bt {
    // padding: 70px;
    margin: 0 auto;
    // height: 70px;
    margin-top: 2.5rem;
    font-size: 20px;
}

</style>