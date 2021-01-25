<template>
    <!-- <div id="app"> -->
        
        <div id="mtqqtest" :style="{height: '100%',width: '100%'}">
            
            <!-- <dv-decoration-1 style="width:200px;height:50px;" /> -->
            <!-- <dv-active-ring-chart :config="config" style="width:100%;height:100%" /> -->
            n:{{msg3}}
            V:{{msg1}}
            t:{{msg2}}
        </div>
    <!-- </div> -->
</template>

<script>
import axios from 'axios';
import mqtt from 'mqtt';
// const barchart = document.getElementById('barchart')

// const bar = new Charts(barchart)
var client
const options = {
    connectTimeout: 40000,
    clientId: 'mqtitId-Home',
    username: 'hyiot',
    password: '1234abcd',
    clean: true
}
client = mqtt.connect('ws://39.100.250.145:8006/mqtt', options)
export default {
    name:'mtqqtest',
    
    data(){
        return {
            
            msg1:'',
            msg2:'',
            msg3:'',
        }
    },
    mounted() {
        this.mqttMsg()
        console.log("开始连");
    },
    methods: {
        mqttMsg() {
            client.on('connect', (e) => {
                console.log("连接成功！！！")
                client.subscribe('/plc2/prosys/#', { qos: 0 }, (error) => {
                    if (!error) {
                        // console.log('订阅成功')
                    } else {
                    console.log('订阅失败')
                    }
                })

            })
              // 接收消息处理
            client.on('message', (topic, message) => {
                console.log('收到来自', topic, '的消息', message.toString())
                let msg = JSON.parse(message.toString())
                this.msg1 = msg.v
                this.msg2 = msg.t
                this.msg3 = msg.n
            })
        }
    }
}
</script>
<style lang="less" scoped>

</style>