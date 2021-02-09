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
                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="stmo" @click="staf">
                        
                        <h1>静态数据</h1>
                        <div>
                            <i class="iconfont icon-jurassic_data"></i>
                        </div>
                        
                    </div>
                    </transition>

                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="chtb" @click="chartcreate">
                        <h1>chart图表</h1>
                        <i class="iconfont icon-jurassic_chart"></i>
                    </div>
                    </transition>
                    
    



                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="mqln" @click="mqttlinecreate">
                        <h1>时间轴图表</h1>
                        <i class="iconfont icon-zhexiantu"></i>
                    </div>
                    </transition>
                </div>
                
            </div>
            <div class="col">
                <div class="but">
                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="stmo" @click="movf">
                        <h1>动态数据</h1>
                        <i class="iconfont icon-yun1"></i>
                    </div>
                    </transition>

                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="chtb" @click="tablecreate">
                        <h1>table表格</h1>
                        <i class="iconfont icon-biaoge"></i>
                    </div>
                    </transition>

                    <transition name="el-zoom-in-top" :appear="true">
                    <div class="con" v-if="mqln" @click="mqttnumcreate">
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
.main {
    width: 100%;
    height: calc(93.6vh);
    background-color: #333;
    display: flex;

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
    // text-align: center;
    // padding: 25px;
}

h1 {
    font-size: 60px;
    color: #343434;
    padding-top: 75px;
    margin: 0 auto;
    // display: block;
}

.iconfont {
    font-size: 150px;
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