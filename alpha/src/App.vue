<template>
  <div id="app">
    <!-- 路由占位符 -->
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
import axios from 'axios'
import mqtt from 'mqtt'
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
    name: "app",
  provide(){
    return {
      reload: this.reload
    }
  },
  data(){
    return {
      isRouterAlive: true,
      obj:{
            temp:{
              n:'/Kael/temp',
              v:'',
              t:''
            },
            humi:{
              n:'/Kael/humi',
              v:'',
              t:''
            },
            key:{
              n:'/Kael/key',
              v:'',
              t:''
            },
            RSSI:{
              n:'/Kael/RSSI',
              v:'',
              t:''
            },
          },
        interval:''
    }
  },

  methods: {
    getTbData() {
            let that = this
            // const userID=localStorage.getItem("userID")
            let postData=this.$qs.stringify({
                pagenum:0,
                pagesize:0,
                dataName:'',
                checkStatus:-1
            })
            // console.log(postData);
            // console.log(this.dbCurrentPage);
            // console.log(this.dbSize);
            const result = axios({
                method: 'post',
                url:'/getAlertList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                if(resp.data.data.unchecked>0){
                  that.$notify({
                        
                        message: "你现在有未处理的警报待处理，共"+resp.data.data.unchecked+"条",
                        offset: 100,
                        type: 'warning'
                    });
                }
                }
            })
        },
    reload(){
      this.isRouterAlive = false;
      this.$nextTick(function(){
        this.isRouterAlive = true;
      })
    },
    clientConnect() {
      let that = this
      
      
      client.on('connect', (e) => {
          // console.log("连接成功！！！")
          client.subscribe('/Kael/+', { qos: 0 }, (error) => {
          if (!error) {
              // console.log('app订阅成功')
          } else {
              console.log('app订阅失败')
          }
          })
      })
      client.on('message', (topic, message) => {
          let msg = JSON.parse(message.toString())
          // console.log(topic);
          // this.datalist.name=msg.n;
          // this.datalist.value=msg.v;
          if(msg.n=='temp') {
              // console.log(msg.v);
              that.obj.temp.v=msg.v;
              // var time = new Date()
              // console.log(msg.t);
              // var formatTime = time.toTimeString().substr(0,8)
              // that.obj.temp.t=formatTime
          }
          else if(msg.n=='humi') {
              // console.log(msg.v);
              that.obj.humi.v=msg.v;
              // var time = new Date()
              // console.log(msg.t);
              // var formatTime = time.toTimeString().substr(0,8)
              // that.obj.humi.t=formatTime
          }
          else if(msg.n=='key') {
              // console.log(msg.v);
              that.obj.key.v=msg.v;
              // var time = new Date()
              // console.log(msg.t);
              // var formatTime = time.toTimeString().substr(0,8)
              // that.obj.key.t=formatTime
          }
          else if(msg.n=='RSSI') {
              // console.log(msg.v);
              that.obj.RSSI.v=100-msg.v;
              // var time = new Date()
              // console.log(msg.t);
              // var formatTime = time.toTimeString().substr(0,8)
              // that.obj.RSSI.t=formatTime
          }
          // console.log(that.obj);
          // window.onresize = function temp() {
            // that.$store.commit(
                // "setHeight",
                // document.documentElement.clientHeight - 110
            // );
          // };
          this.$store.commit('setClient',that.obj)
          
      })
      // client.on('reconnect', (error) => {
          // console.log('app正在重连:', error)
      // })
      // 链接异常处理
      client.on('error', (error) => {
          console.log('app连接失败:', error)
      })
            // console.log(client);
    }
  },
  created(){
    this.getTbData();
    this.interval =setInterval(()=>{
            setTimeout(()=>{
                this.getTbData();
            },0)
        },30000)
  },
  mounted() {
    this.clientConnect()
    let that=this
    setInterval(()=>{
      var time = new Date()
      var formatTime = time.toTimeString().substr(0,8)
      that.obj.temp.t=formatTime
      that.obj.humi.t=formatTime
      that.obj.key.t=formatTime
      that.obj.RSSI.t=formatTime
      that.$store.commit('setClient',that.obj)
    },1000)
    localStorage.setItem('clientHeight',document.documentElement.clientHeight - 110)
  },
  beforeDestroy() {
    client.end()
  },
}
</script>

<style>

</style>
