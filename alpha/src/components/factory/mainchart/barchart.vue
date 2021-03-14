<template>
    <div id="app">
        
        <div id="barchart" :style="{height: '4rem',width: '100%',}">
            
            <!-- <dv-decoration-1 style="width:200px;height:50px;" /> -->
            <dv-capsule-chart :config="config" style="width:100%;height:100%" />
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Charts from '@jiaminghi/charts';
// const barchart = document.getElementById('barchart')

// const bar = new Charts(barchart)
import mqtt from 'mqtt';
const options= {
    connectTimeout: 40000,
    clientId: 'mqtitId-Home',
    username: 'cxd110',
    password: 'LEFT1105500hp',
    clean: true,
    // clientId: Math.random*100,
    connectTimeout: 4000, // 超时时间
    reconnectPeriod: 4000, // 重连时间间隔
}
const client = mqtt.connect('ws://182.254.214.162:1883/mqtt', options)
export default {
    name:'barchart',
    data(){
        return {
            barlist:[{name:'固定',value:'固定'}],
            datalist:{
                name:"",
                value:"",
            },
            
            // msg:'',
            config: {
            data:
            [
            {
                name: '南阳',
                value: 167
            },
            {
                name: '周口',
                value: 123
            },
            {
                name: '漯河',
                value: 98
            },
            {
                name: '郑州',
                value: 75
            },
            {
                name: '西峡',
                value: 66
            },
            ],
            colors: ['#e062ae', '#fb7293', '#e690d1', '#32c5e9', '#96bfff'],
            unit: '单s位'
            }
        }
    },
    mounted() {
        this.mqttMsg()
        // console.log("开始连");
    },
    methods: {
        mqttMsg() {
            client.on('connect', (e) => {
                // console.log("连接成功！！！")
                client.subscribe('/plc2/prosys/#', { qos: 0 }, (error) => {
                    if (!error) {
                        // console.log('订阅成功')
                    } else {
                    // console.log('订阅失败')
                    }
                })

            })
              // 接收消息处理
            client.on('message', (topic, message) => {
                // console.log('收到来自', topic, '的消息', message.toString())
                let msg = JSON.parse(message.toString())
                this.datalist.name=msg.n;
                this.datalist.value=msg.v;
                this.barlist.push(this.datalist);
                // console.log(this.barlist);
                this.config.data=this.barlist;
            })
        }
    }
        
}
</script>
<style lang="less" scoped>

</style>