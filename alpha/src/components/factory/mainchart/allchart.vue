
<template>
    <div id="app">
        <div id="allchart" :style="{height: '100%',width: '100%'}"></div>
        <!-- <div>{{msg}}</div> -->
    </div>
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
    name:'allchart',
    data(){
        return{
            msg:'',
            
            tmplist:[],
            timelist:[],
            linename:"",
            

        }
    },
    // created(){
        // this.mqttMsg()
    // },
    mounted(){
        // this.drawBar();
        this.mqttMsg();
    },
    methods: {
        mqttMsg(){
            client.on('connect', (e) => {
                console.log("连接成功！！！")
                client.subscribe('/modbus/40002', { qos: 0 }, (error) => {
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
                // console.log(msg.v);
                if(msg.v<10000 && msg.v>5){
                    this.tmplist.push(msg.v);
                    var time = new Date(msg.t)
                    var formatTime = time.toTimeString().substr(0,8)
                    this.timelist.push(formatTime);

                    this.linename=msg.n;
                }
                // console.log(this.datalist);
                this.drawBar(this.tmplist,this.timelist,this.linename);
                // console.log(this.tmplist);
                // console.log(this.barlist);
                // this.config.data=this.barlist;
            })
        },
        drawBar(tmplist,timelist,linename){
        // let bar=this.$echarts.init(document.getElementById('bar'))
        // bar.setOption({
        let allchart = this.$echarts.init(document.getElementById('allchart'))
        // 绘制图表
        
        allchart.setOption({
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            axisLabel:{
                interval: 0
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

            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: timelist,
                axisLabel: {
                    textStyle: {
                        color: '#ffffff'
                    }
                },
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                    textStyle: {
                        color: '#ffffff'
                    }
                },
            },
            series: [{
                data: tmplist,
                type: 'line',
                areaStyle: {},
                name:linename,
            }]


        });
        }
    }

}
</script>

<style lang="less" scoped>

</style>