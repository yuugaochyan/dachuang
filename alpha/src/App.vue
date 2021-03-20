<template>
  <div id="app">
    <!-- 路由占位符 -->
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
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
          }
    }
  },

  methods: {
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
              that.obj.temp.v=Number(msg.v);
              var time = new Date(msg.t*1000)
              // console.log(msg.t);
              var formatTime = time.toTimeString().substr(0,8)
              that.obj.temp.t=formatTime
          }
          else if(msg.n=='humi') {
              // console.log(msg.v);
              that.obj.humi.v=Number(msg.v);
              var time = new Date(msg.t*1000)
              // console.log(msg.t);
              var formatTime = time.toTimeString().substr(0,8)
              that.obj.humi.t=formatTime
          }
          else if(msg.n=='key') {
              // console.log(msg.v);
              that.obj.key.v=Number(msg.v);
              var time = new Date(msg.t*1000)
              // console.log(msg.t);
              var formatTime = time.toTimeString().substr(0,8)
              that.obj.key.t=formatTime
          }
          else if(msg.n=='RSSI') {
              // console.log(msg.v);
              that.obj.RSSI.v=100-Number(msg.v);
              var time = new Date(msg.t*1000)
              // console.log(msg.t);
              var formatTime = time.toTimeString().substr(0,8)
              that.obj.key.t=formatTime
          }
          // console.log(that.obj);
          // window.onresize = function temp() {
            // that.$store.commit(
                // "setHeight",
                // document.documentElement.clientHeight - 110
            // );
          // };
          // this.$store.commit('setClient',that.obj)
          // console.log(msg.v);
          // if(msg.v<max && msg.v>min){
              // that.vlist.push(msg.v);
              // var time = new Date(msg.t*1000)
              // console.log(msg.t);
              // var formatTime = time.toTimeString().substr(0,8)
              // console.log(formatTime);
              // that.tlist.push(formatTime);
              // console.log(this.tmplist.length);
              // if(that.tlist.length>lengs) {
                  // that.vlist.shift();
                  // that.tlist.shift();
              // }
          // }
          // console.log(this.datalist);
          // this.drawBar(this.tmplist,this.timelist,this.linename);
          // console.log(this.tmplist);
          // console.log(this.barlist);
          // this.config.data=this.barlist;
          // this.draw(this.tlist,this.vlist,legend,tagName,type)
      })
      client.on('reconnect', (error) => {
          console.log('app正在重连:', error)
      })
      // 链接异常处理
      client.on('error', (error) => {
          console.log('app连接失败:', error)
      })
            // console.log(client);
    }
  },
  mounted() {
    this.clientConnect()
    this.$store.commit(
        "setHeight",
        document.documentElement.clientHeight - 110
    );
  },
  beforeDestroy() {
    client.end()
  },
}
</script>

<style>

</style>
