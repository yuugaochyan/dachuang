<template>
        
        <div  v-bind:id=id ref="obdata" :style="{height: '100%',width: '100%',}">
            <dv-digital-flop :config="config" style="width:100%;height:100%;" />
            
        
        </div>
        
</template>

<script>
import axios from 'axios'
import mqtt from 'mqtt'
var client = mqtt.connect('ws://39.100.250.145:8006/mqtt', options)
const options= {
    connectTimeout: 40000,
    clientId: 'mqtitId-Home',
    username: 'hyiot',
    password: '1234abcd',
    clean: true
}
client = mqtt.connect('ws://39.100.250.145:8006/mqtt', options)
export default {
    name:'mqttnum',
    props:["id","obdata"],
    data(){
        return {
            
            reset:false,
            chart:'',
            vlist:[],
            tlist:[],
            config:{
                number:[0.0],
                toFixed: 1,
                content:this.obdata.tagName+': {nt}',
                style: {
                    fontSize:120,
                    fill:'#dfdfdf'
                }
            },
            interval:''
        }
    },
    
    methods: {
        
        drawline({
            tag='',
            max='',
            min='',
            tagName='',
            shape=''
        }={}){
            let that=this
            
            

            //*mqtt订阅 need:tag\max\min
            client.on('connect', (e) => {
                console.log("连接成功！！！")
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
                // console.log(msg.v);
                that.config.number[0]=parseInt(msg.v)
                // console.log(that.config.number[0]);
                if(msg.v>max){
                    that.config.style.fill='red'
                }
                else if(msg.v<min){
                    that.config.style.fill='white'
                }
                else {
                    that.config.style.fill='#3de7c9'
                }
                
                this.config = { ...this.config }
                // console.log(this.datalist);
                // this.drawBar(this.tmplist,this.timelist,this.linename);
                // console.log(this.tmplist);
                // console.log(this.barlist);
                // this.config.data=this.barlist;
                
            })
            client.on('reconnect', (error) => {
                // console.log('正在重连:', error)
            })
            // 链接异常处理
            client.on('error', (error) => {
                console.log('连接失败:', error)
            })
        },

        
        
        
    },
    created () {
        let that = this;
        this.drawline(this.obdata);
        this.interval =setInterval(()=>{
            setTimeout(()=>{
                
            },0)
        },30000)
    },
    beforeDestroy() {
        clearInterval(this.interval);
        // client.end()
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