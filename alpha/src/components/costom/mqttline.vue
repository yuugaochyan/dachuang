<template>
        
        <div  v-bind:id=id ref="obdata" :style="{height: '100%',width: '100%',}"></div>
        
</template>

<script>
import axios from 'axios'
import mqtt from 'mqtt'

export default {
    name:'mqttline',
    props:["id","obdata"],
    inject:['reload'],
    data(){
        return {
            
            reset:false,
            chart:'',
            vlist:[],
            tlist:[],
            interval:''
        }
    },
    
    methods: {
        
        drawline({
            legend=[],
            tag='',
            max='',
            min='',
            tagName='',
            lengs='',
            type='',
        }={}){
            let that=this
            let obj=JSON.parse(localStorage.getItem('client'))
            // console.log(obj);
            for(let key in obj) {
                // console.log(obj[key]);
                if(obj[key].n==tag) {
                    if(obj[key].v<max && obj[key].v>min){
                        that.vlist.push(obj[key].v);
                        that.tlist.push(obj[key].t);
                        // console.log(this.tmplist.length);
                        if(that.tlist.length>lengs) {
                            that.vlist.shift();
                            that.tlist.shift();
                        }
                    }    
                }
            }
            // console.log(this.tlist);
            // console.log(this.vlist);
            // this.reload()
            this.draw(this.tlist,this.vlist,legend,tagName,type)
            

        },

        draw(t,v,l,n,type) {
            let that=this
            this.chart = this.$echarts.init(document.getElementById(this.id))
            let option={
                
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    }
                },
                legend: { //图例
                    textStyle:{
                        // fontSize: 18,//字体大小
                        color: '#ffffff'
                    },
                    data: l,
                    
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
                        type: 'category',
                        // boundaryGap: false,
                        data: t,
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
                series: [{
                    data: v,
                    type: type,
                    areaStyle: {},
                    name:n,
                }]
                
            };
            this.chart.setOption(option)
            // window.onresize=that.chart.resize,
            window.addEventListener("resize", function () {
                if(that.chart) {
                    that.chart.resize();
                    // console.log("监听到变化");
                }
            })
        }
        
        
    },
    created () {
        
        
    },
    mounted() {
        let that = this;
        this.drawline(this.obdata);
        this.interval =setInterval(()=>{
            setTimeout(()=>{
                // console.log(client);
                // console.log(that.obdata);
                that.drawline(that.obdata);
            },300)
        },6000)
    },
    beforeDestroy() {
        // client.end()
        clearInterval(this.interval);
    
    },
    watch: {
        obdata: {
            handler(value) {
                this.drawline(value)
            }
        },
        // tlist: function() {
            // this.drawline(this.obdata)
        // },
        deep:true //深度监听
    }

}
</script>