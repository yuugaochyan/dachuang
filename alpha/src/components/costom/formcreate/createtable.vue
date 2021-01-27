<template>
        
        <div  class="con">
            <div class="left">
                
                <transition name="el-fade-in" >
                <el-form :model="chartform" v-show="step1" :rules="rules" ref="chartformref" :inline="true">
                    <el-form-item label="问题名字"  prop="graphName">
                        <el-input v-model="chartform.graphName" clearable placeholder="为这个可视化问题取个名字吧"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item label="数据来源"  prop="dataSource">
                    <el-select v-model="chartform.dataSource" placeholder="请选择数据源">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                    </el-form-item>
                </el-form>
                </transition>

                

            <div class="bt-next">
                <el-button type="info"  @click="laststep" v-show="active>0">{{steplabel1}}</el-button>
                <el-button type="warning"  @click="nextstep">{{steplabel2}}</el-button>
            </div>


            <el-steps :active="active" finish-status="success" class="bt-step" align-center>
                <el-step title="第一步" description="命名这个问题并选择数据源"></el-step>
                <el-step title="第二步" description="放进仪表盘看看吧？"></el-step>
            </el-steps>
            </div>
            <div class="right">
                <div id="chart" :style="{height: '100%',width: '100%'}"></div>
            </div>
            
            
        </div>
        
</template>

<script>
import axios from 'axios'
export default {
    name:'createchart',
    data(){
        return {
            rules: {
                graphName: [
                    { required: true, message: '请输入问题的名字！', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
                graphType: [
                    { required: true, message: '请选择图表类型！', trigger: 'blur' },
                ],
                dataSource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
            },
            
        }
    },
    
    methods: {
        
    },
    
    // mounted () {
        // this.getData();
    // },
    // watch: {
        // formdata: {
            // handler(value) {
                // this.drawline(value)
            // }
        // },
        // deep:true //深度监听
    // }

}
</script>

<style lang="less" scoped>
.con {
    display: flex;
    height: calc(93.6vh);
    width: 100%;
}
.left {
    flex:1;
    text-align: left;
    padding: 20px;
    // color: #fff;
    background-color: #fff;
    // height: 100%;
    position: relative;
}
.right {
    flex:3
}
.bt-step {
    position: absolute;
    // top: 80%;
    left: 0%;
    bottom: 5%;
    width: 100%;
}
.bt-next {
    position: absolute;
    bottom: 25%;
    right: 10%;
}
</style>