<template>
    
        
        <div  id="linechart" :style="{height: '100%',width: '100%',}"></div>
    
</template>

<script>
import axios from 'axios'
export default {
    name:'linechart',
    data(){
        return {
            linelist:[],
            legend:[],
            reset:false,
            linechart:'',
        }
    },
    
    methods: {
        drawline(linelist,legend){
            this.linechart = this.$echarts.init(document.getElementById('linechart'))
            this.linechart.setOption({
                // title: {
                    // text: '堆叠区域图',
                    // left: 'center'
                // },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    }
                },
                legend: {
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
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        },
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        },
                    }
                ],
                series: linelist
                
            });
            window.onresize=this.linechart.resize;
        },
        lineAjax(){
            let that=this
            this.$axios.get("/getChart/line")
            .then((resp)=>{
                that.linelist=resp.data.linelist;
                that.legend=resp.data.legend;
                // console.log(resp.data.linelist);
                this.drawline(this.linelist,this.legend);
            })
        },
        
    },
    beforeMount(){
        this.lineAjax();
    },
    mounted() {
    
    },
    watch: {
        linelist: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        }
    }

}
</script>

<style lang="less" scoped>

</style>