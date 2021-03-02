<template>
        
        <div  v-bind:id=id ref="obdata" :style="{height: '100%',width: '100%',}"></div>
        
</template>

<script>
import axios from 'axios'
export default {
    name:'chart',
    props:["id","obdata"],
    data(){
        return {
            
            reset:false,
            chart:''
        }
    },
    
    methods: {
        drawline({
            xarray=[], //^x轴
            series=[], //^数据
            legend=[],
            xtype='',
            ytype='',
            xArraySource='',
        }={}){
            let that=this
            this.chart = this.$echarts.init(document.getElementById(this.id))
            this.chart.setOption({
                
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
                        type: xtype,
                        // boundaryGap: false,
                        data: xarray,
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        },
                        name:xArraySource,
                        nameLocation:'middle',
                        nameTextStyle:{
                            color:"#ffffff", 
                            fontSize:16,  
                            padding:10
                        },
                    }
                ],
                yAxis: [
                    {
                        type: ytype,
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        },
                        name:series[0].dataCol,
                        nameLocation:'middle',
                        nameTextStyle:{
                            color:"#ffffff", 
                            fontSize:16,  
                            padding:10
                        },
                    }
                ],
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
        
        
    },
    mounted () {
        this.drawline(this.obdata);
    },
    watch: {
        obdata: {
            handler(value) {
                this.drawline(value)
                console.log(value);
            }
        },
        deep:true //深度监听
    }

}
</script>

<style lang="less" scoped>

</style>