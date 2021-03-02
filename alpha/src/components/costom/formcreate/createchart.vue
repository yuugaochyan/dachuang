<template>
        
        <div  class="con">
            <div class="left">
                
                <transition name="el-fade-in" >
                <el-form :model="chartform" v-show="step1" :rules="rules" ref="chartformref" :inline="true">
                    <el-form-item label="问题名字"  prop="graphName">
                        <el-input v-model="chartform.graphName" clearable placeholder="为这个可视化问题取个名字吧"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item label="图表类型"  prop="graphType">
                        <el-radio v-model="chartform.graphType" label="bar">柱状图</el-radio>
                        <el-radio v-model="chartform.graphType" label="line">折线图</el-radio>
                        <el-radio v-model="chartform.graphType" label="pie">饼图</el-radio>
                        <el-radio v-model="chartform.graphType" label="scatter">散点图</el-radio>
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

                <!-- //^第二步-柱状图 -->
                <transition name="el-fade-in" >
                    <el-form :model="Chart" 
                    v-show="step2&&chartform.graphType=='bar'" 
                    :rules="rules2" ref="chartformref2" :inline=true>
                    <el-form-item label="x轴数据"  prop="xArraySource">
                        <el-select v-model="Chart.xArraySource" 
                        placeholder="请选择x轴数据"
                        @change="changeX">
                            <el-option
                            v-for="opx in colList"
                            :key="opx"
                            :label="opx"
                            :value="opx">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="y轴数据"  prop="yArraySource">
                        <el-select v-model="Chart.yArraySource" 
                        placeholder="请选择y轴数据"
                        @change="changeX">
                            <el-option
                            v-for="opy in colList"
                            :key="opy"
                            :label="opy"
                            :value="opy">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="y轴数据名" prop="name">
                        <el-input v-model="Chart.name" @change="changeX"></el-input>
                    </el-form-item>
                    <!-- <el-form-item label="预览生成图表"> -->
                        <!-- <el-button @click="drawline('bar')"></el-button> -->
                    <!-- </el-form-item> -->
                    </el-form>
                </transition>

                <!-- //^第二步-折线图 -->
                <transition name="el-fade-in" >
                    <el-form :model="lineChart" 
                    v-show="step2&&chartform.graphType=='line'" 
                    :rules="rules2" ref="chartformref2" :inline="true">
                    <el-form-item label="数据来源"  prop="xArraySource" placeholder="选择x轴数据">
                        <el-select v-model="lineChart.xArraySource" 
                        placeholder="请选择x轴数据"
                        @change="changeX" >
                            <el-option
                            v-for="opx in colList"
                            :key="opx"
                            :label="opx"
                            :value="opx">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item prop="yArraySource" placeholder="选择y轴数据" 
                    v-for="(item,index) in colList" :key="index" v-show="index<yNum">
                        <el-select v-model="lineChart.yArraySource[index]" 
                        placeholder="请选择y轴数据"
                        @change="changeX" >
                            <el-option
                            v-for="opy in colList"
                            :key="opy"
                            :label="opy"
                            :value="opy">
                            </el-option>
                        </el-select>
                        <el-button type="success"  @click="configLine(index)">设置</el-button>
                    </el-form-item>
                    <el-dialog
                        title="提示"
                        :visible.sync="lineConfigVis"
                        width="30%"
                        :before-close="handleClose">
                        <span>
                            <el-form :model="lineChart">
                                <el-form-item label="数据名">
                                    <el-input v-model="lineChart.name[configingLine]" clearable placeholder="为这个可视化问题取个名字吧"></el-input>
                                </el-form-item>
                                <el-form-item label="颜色">
                                    <el-color-picker v-model="lineChart.color[configingLine]"></el-color-picker>
                                </el-form-item>
                            </el-form>
                        </span>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="lineConfigVis = false">取 消</el-button>
                            <el-button type="primary" @click="lineConfigVis = false">确 定</el-button>
                        </span>
                    </el-dialog>

                    <br>
                    <el-button type="success"  @click="pushyNum">+</el-button>
                    <el-button type="danger"  @click="popyNum">-</el-button>
                    </el-form>
                </transition>

                

                <!-- //^第二步-饼图 -->
                <transition name="el-fade-in" >
                    <el-form :model="Chart" 
                    v-show="step2&&chartform.graphType=='pie'" 
                    :rules="rules3" ref="chartformref2" :inline="true">
                    <el-form-item label="饼名来源"  prop="xArraySource">
                        <el-select v-model="Chart.xArraySource" 
                        placeholder="请选择饼名来源"
                        @change="changeY">
                            <el-option
                            v-for="opx in colList"
                            :key="opx"
                            :label="opx"
                            :value="opx">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="饼值来源"  prop="yArraySource">
                        <el-select v-model="Chart.yArraySource" 
                        placeholder="请选择饼值来源"
                        @change="changeY">
                            <el-option
                            v-for="opy in colList"
                            :key="opy"
                            :label="opy"
                            :value="opy">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <!-- <el-form-item label="预览生成图表"> -->
                        <!-- <el-button @click="drawline('pie')"></el-button> -->
                    <!-- </el-form-item> -->
                    </el-form>
                </transition>

                <!-- //^第二步-散点图 -->
                <transition name="el-fade-in" >
                    <el-form :model="Chart" 
                    v-show="step2&&chartform.graphType=='scatter'" 
                    :rules="rules3" ref="chartformref2" :inline="true">
                    <el-form-item label="x轴数据"  prop="xArraySource">
                        <el-select v-model="Chart.xArraySource" 
                        placeholder="请选择x轴数据"
                        @change="changeX">
                            <el-option
                            v-for="opx in colList"
                            :key="opx"
                            :label="opx"
                            :value="opx">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="y轴数据"  prop="yArraySource">
                        <el-select v-model="Chart.yArraySource" 
                        placeholder="请选择y轴数据"
                        @change="changeX">
                            <el-option
                            v-for="opy in colList"
                            :key="opy"
                            :label="opy"
                            :value="opy">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <!-- <el-form-item label="预览生成图表"> -->
                        <!-- <el-button @click="drawline('scatter')"></el-button> -->
                    <!-- </el-form-item> -->
                    </el-form>
                </transition>

                <transition name="el-fade-in" >
                <el-form :model="dbform" v-show="step3" :rules="rules4" ref="chartformref3">
                    <el-form-item label="你的仪表盘"  prop="db">
                    <el-select v-model="dbform.db" placeholder="请选择仪表盘">
                        <el-option
                        v-for="item in dbData"
                        :key="item.dbID"
                        :label="item.dbName"
                        :value="item.dbID">
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
                <el-step title="第一步" description="命名这个问题并选择图表类型和数据源"></el-step>
                <el-step title="第二步" description="配置x-y轴和数据，将问题存入你的库里"></el-step>
                <el-step title="第三步" description="放进仪表盘看看吧？"></el-step>
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
            rules2: {
                xArraySource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
                yArraySource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
                name: [
                    { required: true, message: '请输入该数据的名字！', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
            },
            rules3: {
                xArraySource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
                yArraySource: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
            },
            rules4: {
                db: [
                    { required: true, message: '请选择数据来源！', trigger: 'blur' },
                ],
            },
            //!可能会删掉
            options: [{
                value: 'equipmentrepair',
                label: 'equipmentrepair'
            }, {
                value: 'eqpseasonstatistic',
                label: 'eqpseasonstatistic'
            }],
            listName: [{
                value: 'eqpname',
                label: 'eqpname'
            }, {
                value: 'season',
                label: 'season'
            }, {
                value: 'price',
                label: 'price'
            }],
            yNumList:[1,2,3],
            //!到此为止
            colList:[],
            chartform:{
                graphName:'',
                graphType:'',
                dataSource:'',
            },
            Chart:{
                xArraySource:'',
                yArraySource:'',
                name:'',
            },
            lineChart:{
                xArraySource:'',
                yArraySource:[''],
                name:[''],
                color:['']
            },
            yName:[],
            yNum:1,
            tableData:{},
            reset:false,
            active:0,
            steplabel2:'下一步',
            steplabel1:'上一步',
            step1:true,
            step2:false,
            step3:false,
            xData:[],
            yData:[],
            ylineData:[],
            xType:'',
            yType:'',
            dbform:{
                db:''
            },
            dbData:[],
            tbID:'',
            lineConfigVis:false,
            configingLine:1,
        }
    },
    
    methods: {
        getDbData() {
            let that = this;
            const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                userID:userID,
            })
            const result = axios({
                method: 'post',
                url:'/getDBList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.dbData=resp.data.data
            }
            })
        },
        configLine(index) {
            this.configingLine=index;
            this.lineConfigVis=true;
        },
        pushyNum() {
            if(this.yNum<this.colList.length) {
                this.yNum++;
                console.log(this.lineChart.yArraySource);
                this.drawline(this.chartform.graphType)
            }
        },
        popyNum() {
            if(this.yNum>0) {
                this.lineChart.yArraySource.pop();
                console.log(this.lineChart.yArraySource);
                this.yNum--
                this.drawline(this.chartform.graphType)
            }
        },
        drawline(Type){
            let that=this
            var series=[];
            var legend=[];
            var location=[];
            // console.log(Type);
            // console.log(this.Chart.xArraySource);

            if(Type=='bar'||Type=='line') {
                this.xType='category'
                this.yType='value'
            }
            else if(Type=='scatter') {
                this.xType='value'
                this.yType='value'
            }
            if(Type=='bar'||Type=='scatter') {
            for(let key in this.tableData) {
                if(key==that.Chart.xArraySource) {
                    that.xData=this.tableData[key]
                }
                if(key==that.Chart.yArraySource) {
                    that.yData=this.tableData[key]
                }
            }
            }
            if(Type=='line') {
                // console.log(this.lineChart.yArraySource);
                for(let key in this.tableData) {
                    if(key==that.lineChart.xArraySource) {
                        that.xData=this.tableData[key]
                    }
                    for(let i in this.lineChart.yArraySource) {
                        // console.log(this.lineChart.yArraySource[i]);
                        // console.log(key);
                        if(key==this.lineChart.yArraySource[i]) {
                            that.ylineData[i]=this.tableData[key];
                            let obj={
                            name:this.lineChart.name,
                            type:Type,
                            data:this.ylineData[i]
                            }
                            series.push(obj)
                            console.log(series);
                            legend.push(this.lineChart.name[i])
                            // console.log(this.tableData[key]);
                        }
                        
                    }
                }
                
                
                
            }
            else if(Type=='bar') {
                let obj={
                    name:this.Chart.name,
                    type:Type,
                    data:this.yData
                }
                series.push(obj)
                legend.push(this.Chart.name)
            }
            else if(Type=='scatter') {
                for(let x in this.xData) {
                    let loca=[this.xData[x],this.yData[x]];
                    location.push(loca)
                }
                let obj={
                    name:this.Chart.name,
                    type:Type,
                    data:location
                }
                series.push(obj)
                legend.push(this.Chart.name)
            }

            let chart = this.$echarts.init(document.getElementById('chart'))
            let option={
                
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    },
                    show: true,
                    formatter: '({c})'
                },
                legend: { //图例
                    textStyle:{
                        // fontSize: 18,//字体大小
                        color: '#ffffff'
                    },
                    data: legend,
                    
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                // grid: { //?绘图区域
                    // left: '3%',
                    // right: '4%',
                    // bottom: '3%',
                    // containLabel: true
                // },
                xAxis: [
                    {
                        type: this.xType,
                        name:this.Chart.xArraySource,
                        nameLocation:'middle',
                        nameTextStyle:{
                            color:"#ffffff", 
                            fontSize:16,  
                            padding:10
                        },
                        // boundaryGap: false,
                        data: this.xData,
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        },
                    }
                ],
                yAxis: [
                    {
                        type: this.yType,
                        name:this.Chart.yArraySource,
                        nameLocation:'middle',
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        },
                        nameTextStyle:{
                            color:"#ffffff", 
                            fontSize:16,  
                            padding:10
                        },
                    }
                ],
                series: series
                
            }
            chart.setOption(option);
            chart.setOption(option,true)
            // window.onresize=that.chart.resize,
            window.addEventListener("resize", function () {
                if(that.chart) {
                    that.chart.resize();
                    // console.log("监听到变化");
                }
            })
        
        },
        drawpie() {
            let that=this
            var series=[];

            
            for(let key in this.tableData) {
                if(key==that.Chart.xArraySource) {
                    that.xData=this.tableData[key]
                }
                if(key==that.Chart.yArraySource) {
                    that.yData=this.tableData[key]
                }
            }
                let obj={
                    type:'pie',
                    data:[]
                }
                for(let item in this.xData) {
                    let dataobj={
                        value:this.yData[item],
                        name:this.xData[item]
                    }
                    obj.data.push(dataobj)
                }
                // console.log(obj);
                series.push(obj)
            
            let chart = this.$echarts.init(document.getElementById('chart'))
            chart.setOption({
                
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    },
                    show: true
                },
                legend: { //图例
                    textStyle:{
                        // fontSize: 18,//字体大小
                        color: '#ffffff'
                    },
                    data: this.xData,
                    
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                // grid: { //?绘图区域
                    // left: '3%',
                    // right: '4%',
                    // bottom: '3%',
                    // containLabel: true
                // },
                series: series
                
            });
            
            // window.onresize=that.chart.resize,
            window.addEventListener("resize", function () {
                if(that.chart) {
                    that.chart.resize();
                    // console.log("监听到变化");
                }
            })
        },
        
        getData () {
            let that=this
            let postDta=this.$qs.stringify({
                dataSource:that.chartform.dataSource
            })
            console.log(that.chartform.dataSource);
            this.$axios.post("/getTableData",postDta)
            .then((resp)=>{
                console.log(resp)
                that.colList=resp.data.data.colList,
                that.tableData=resp.data.data.tableData
            })
        },

        nextstep() {
            let that=this;
            var postData={}
            this.$refs.chartformref.validate((valid)=>{
                if(!valid) return;
                
                if(this.active==0) {
                    this.active++;
                    this.steplabel2='保存图表'
                    this.step1=false;
                    this.getData();
                    setTimeout(function() {
                        that.step2=true;
                    },500);
                    if(this.chartform.graphType=='pie') {
                        this.drawpie()
                    }
                    else{
                    this.drawline(this.chartform.graphType)
                    }
                }
                else if(this.active==1) {
                    this.$refs.chartformref2.validate((valid)=>{
                    if(!valid) return;
                    const userID=localStorage.getItem("userID")
                    // console.log(userID);
                    //^封装数据bar
                    if(this.chartform.graphType=='bar'){
                        postData={
                            userID:userID,
                            graphName:this.chartform.graphName,
                            graphType:this.chartform.graphType,
                            dataSource:this.chartform.dataSource,
                            Chart:{
                                xType:"category",
                                yType:"value",
                                xArraySource:this.Chart.xArraySource,
                                series:[{
                                    name:this.Chart.name,
                                    dataCol:this.Chart.yArraySource
                                }]
                            }
                        }
                        const result = axios({
                        method: 'post',
                        url:'/addBarGraph',
                        data:postData
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.tbID=resp.data.tbID;
                            that.steplabel2='放入仪表盘',
                            that.steplabel1='算了'
                            that.$message({
                                showClose: true,
                                message: '保存成功',
                                center: true,
                                type: 'success'
                            });
                            that.getDbData();
                            that.step2=false;
                            setTimeout(function() {
                                that.step3=true;
                            },500);
                        }
                        })
                    }
                    //^封装数据scatter
                    else if(this.chartform.graphType=='scatter'){
                        postData={
                            userID:userID,
                            graphName:this.chartform.graphName,
                            graphType:this.chartform.graphType,
                            dataSource:this.chartform.dataSource,
                            Chart:{
                                xArraySource:this.Chart.xArraySource,
                                yArraySource:this.Chart.yArraySource
                            }
                        }
                        const result = axios({
                        method: 'post',
                        url:'/addScatterGraph',
                        data:postData
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.tbID=resp.data.tbID;
                            that.steplabel2='放入仪表盘',
                            that.steplabel1='算了'
                            that.$message({
                                showClose: true,
                                message: '保存成功',
                                center: true,
                                type: 'success'
                            });
                            that.getDbData();
                            that.step2=false;
                            setTimeout(function() {
                                that.step3=true;
                            },500);
                        }
                        })
                    }
                    //^封装数据line
                    else if(this.chartform.graphType=='line'){
                        if(this.Chart.yNum==1){
                        postData={
                            userID:userID,
                            graphName:this.chartform.graphName,
                            graphType:this.chartform.graphType,
                            dataSource:this.chartform.dataSource,
                            Chart:{
                                xType:"category",
                                yType:"value",
                                xArraySource:this.Chart.xArraySource,
                                series:[{
                                    name:this.Chart.name,
                                    dataCol:this.Chart.yArraySource
                                }]
                            }
                        }
                        }
                        else if(this.Chart.yNum==2) {
                            postData={
                            userID:userID,
                            graphName:this.chartform.graphName,
                            graphType:this.chartform.graphType,
                            dataSource:this.chartform.dataSource,
                            Chart:{
                                xType:"category",
                                yType:"value",
                                xArraySource:this.Chart.xArraySource,
                                series:[{
                                    name:this.Chart.name,
                                    dataCol:this.Chart.yArraySource
                                },{
                                    name:this.Chart.name2,
                                    dataCol:this.Chart.yArraySource2
                                }]
                            }
                            }
                        }
                        else if(this.Chart.yNum==3) {
                            postData={
                            userID:userID,
                            graphName:this.chartform.graphName,
                            graphType:this.chartform.graphType,
                            dataSource:this.chartform.dataSource,
                            Chart:{
                                xType:"category",
                                yType:"value",
                                xArraySource:this.Chart.xArraySource,
                                series:[{
                                    name:this.Chart.name,
                                    dataCol:this.Chart.yArraySource
                                },{
                                    name:this.Chart.name2,
                                    dataCol:this.Chart.yArraySource2
                                },{
                                    name:this.Chart.name3,
                                    dataCol:this.Chart.yArraySource3
                                }]
                            }
                            }
                        }
                        const result = axios({
                        method: 'post',
                        url:'/addLineGraph',
                        data:postData
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.tbID=resp.data.tbID;
                            that.steplabel2='放入仪表盘',
                            that.steplabel1='算了'
                            that.$message({
                                showClose: true,
                                message: '保存成功',
                                center: true,
                                type: 'success'
                            });
                            that.getDbData();
                            that.step2=false;
                            setTimeout(function() {
                                that.step3=true;
                            },500);
                        }
                        })
                    }
                    //^封装数据pie
                    else if(this.chartform.graphType=='pie'){
                        postData={
                            userID:userID,
                            graphName:this.chartform.graphName,
                            graphType:this.chartform.graphType,
                            dataSource:this.chartform.dataSource,
                            Chart:{
                                pieSource:this.Chart.xArraySource,
                                valueSource:this.Chart.yArraySource
                            }
                        }
                        const result = axios({
                        method: 'post',
                        url:'/addPieGraph',
                        data:postData
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.tbID=resp.data.tbID;
                            that.steplabel2='放入仪表盘',
                            that.steplabel1='算了'
                            that.$message({
                                showClose: true,
                                message: '保存成功',
                                center: true,
                                type: 'success'
                            });
                            that.step2=false;
                            that.getDbData();
                            setTimeout(function() {
                                that.step3=true;
                            },500);
                        }
                        })
                    }

                    

                    
                    // console.log(postData);
                    
                    })
                    
                }
                else if(this.active==2) {
                    this.$refs.chartformref3.validate((valid)=>{
                    if(!valid) return;
                    let postData=this.$qs.stringify({
                        dbID:this.dbform.db,
                        tblist:[this.tbID]
                    },{
                        indices:false
                    })
                    const result = axios({
                        method: 'post',
                        url:'/addGraphToDB',
                        data:postData,
                        indices:false
                        }).then(function(resp){
                            if(resp.data.status==200) {
                            that.active++;
                            that.$message({
                                showClose: true,
                                message: '已经放入仪表盘！前往仪表盘管理看看吧？',
                                center: true,
                                type: 'success'
                            });
                            setTimeout(function() {
                                that.$router.push('/createdb')
                            },300)
                        }
                    })
                    
                    })
                }
            })
        },
        laststep() {
            let that = this;
            this.active--;
            if(this.active==0) {
                this.steplabel2='下一步'
                this.step2=false;
                setTimeout(function() {
                    that.step1=true;
                },500)
            }
            if(this.active==1) {
                this.$router.push('/createtb')
            }
        },
        changeX() {
            // console.log(this.chartform.graphType);
            this.drawline(this.chartform.graphType)
        },
        changeY() {
            this.drawpie()
        },
        
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