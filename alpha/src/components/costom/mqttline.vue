<template>
        
        <div  v-bind:id=id ref="obdata" :style="{height: '100%',width: '100%',}"></div>
        
</template>

<script>
import axios from 'axios'
import mqtt from 'mqtt'
var client
const options= {
    connectTimeout: 40000,
    clientId: 'mqtitId-Home',
    username: 'hyiot',
    password: '1234abcd',
    clean: true
}
client = mqtt.connect('ws://39.100.250.145:8006/mqtt', options)
export default {
    name:'mqttline',
    props:["id","obdata"],
    data(){
        return {
            
            reset:false,
            chart:'',
            vlist:[],
            tlist:[],
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

            //*mqtt订阅 need:tag\max\min
            client.on('connect', (e) => {
                // console.log("连接成功！！！")
                client.subscribe(tag, { qos: 0 }, (error) => {
                if (!error) {
                    // console.log('订阅成功')
                } else {
                    console.log('订阅失败')
                }
                })
            })
        // 接收消息处理
            client.on('message', (topic, message) => {
                let msg = JSON.parse(message.toString())
                // this.datalist.name=msg.n;
                // this.datalist.value=msg.v;
                if(msg.n==tag) {
                    console.log(msg.v);
                }
                console.log(msg.v);
                if(msg.v<max && msg.v>min){
                    that.vlist.push(msg.v);
                    var time = new Date(msg.t*1000)
                    // console.log(msg.t);
                    var formatTime = time.toTimeString().substr(0,8)
                    // console.log(formatTime);
                    that.tlist.push(formatTime);

                    // console.log(this.tmplist.length);
                    if(that.tlist.length>lengs) {
                        that.vlist.shift();
                        that.tlist.shift();
                    }
                }
                // console.log(this.datalist);
                // this.drawBar(this.tmplist,this.timelist,this.linename);
                // console.log(this.tmplist);
                // console.log(this.barlist);
                // this.config.data=this.barlist;
                this.draw(this.tlist,this.vlist,legend,tagName,type)
            })
        },

        draw(t,v,l,n,type) {
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
                
            });
            
            // window.onresize=that.chart.resize,
            window.addEventListener("resize", function () {
                if(that.chart) {
                    that.chart.resize();
                    // console.log("监听到变化");
                }
            })
        }
        
        
    },
    mounted () {
        this.drawline(this.obdata);
    },
    beforeDestroy() {
        
        client.end()
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