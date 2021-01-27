<template>
    <div id="app" v-if="reset">
        <div class="vueGridLayout">
          <dv-border-box-7> 
    <div class="board" style="width: 100%">
      <div class="home">
        <grid-layout
          :layout="layoutData"
          :col-num="12"
          :row-height="layoutHeight"
          :is-draggable="true"
          :is-resizable="true"
          :is-mirrored="false"
          :vertical-compact="true"
          :margin="[10, 10]"
          :use-css-transforms="true"
        >
          <grid-item
            v-for="(item,index) in layoutData"
            :x="item.x"
            :y="item.y"
            :w="item.w"
            :h="item.h"
            :minW="minW"
            :minH="minH"
            :maxW="maxW"
            :maxH="maxH"
            :i="item.i"
            :key="item.i"
            @resized="resizedEvent"
            @moved="movedEvent"
            @resize="resizeEvent"
            @move="moveEvent"
          >
            
            
            <div class="title">{{index}}. {{item.title}}</div>
            <div class="tb">
                <chart v-if="item.type=='chart'" :id="item.i" :obdata="item.objectData"></chart>
                <mqttline v-if="item.type=='mqttline'" :id="item.i" :obdata="item.objectData"></mqttline>
                <sqltb v-if="item.type=='table'" :id="item.i" :obdata="item.objectData"></sqltb>
                <mqttnum v-if="item.type=='mqttnum'" :id="item.i" :obdata="item.objectData"></mqttnum>
            </div>
          </grid-item>
        </grid-layout>
      </div>
    </div>
    </dv-border-box-7>
  </div>
    </div>
</template>

<script>
// import layoutData from '../../assets/virtualData/layoutData.json'
import VueGridLayout from 'vue-grid-layout'
import chart from '@/components/costom/chart'
import mqttline from '@/components/costom/mqttline'
import sqltb from '@/components/costom/sqltb'
import mqttnum from '@/components/costom/mqttnum'

const GridLayout = VueGridLayout.GridLayout
const GridItem = VueGridLayout.GridItem
export default {
  name: "griddemo",
  data() {
    return {
      layout: [
        { x: 0, y: 0, w: 4, h: 2, i: 0 } //数据格式
      ],
      // 布局数据
      layoutData: [],
      layoutHeight: 130,
      layoutConfig: {
        height: 330, // 默认高度
        dialogVisible: false // 是否可拖拽或改变大小
      },
      minW:2,
      minH:2,
      maxW:6,
      maxH:3,
      reset:false
    };
  },
  components: {
    GridLayout,
    GridItem,
    chart,
    mqttline,
    sqltb,
    mqttnum
  },

    methods: {
    init() {
      
      let that=this
      let postDta=this.$qs.stringify({
          graphID:'10003'
      })
      this.$axios.post("/getChart",postDta)
      .then((resp)=>{
          that.layoutData = resp.data.mainData;
      })
      
      
    },
    resizedEvent: function(i, newH, newW, newHPx, newWPx){
        // console.log("RESIZED i=" + i + ", H=" + newH + ", W=" + newW + ", H(px)=" + newHPx + ", W(px)=" + newWPx);
        localStorage.setItem('pandectDisplace', JSON.stringify(this.layoutData));
        this.asideResize();
    },
    movedEvent: function(i, newX, newY,e){
        localStorage.setItem('pandectDisplace', JSON.stringify(this.layoutData));
        this.asideResize();
    },
    resizeEvent: function(i, newH, newW, newHPx, newWPx){
        // console.log("RESIZED i=" + i + ", H=" + newH + ", W=" + newW + ", H(px)=" + newHPx + ", W(px)=" + newWPx);
        this.asideResize();
    },
    moveEvent: function(i, newX, newY,e){
        this.asideResize();
    },
    asideResize(){
       let myEvent = new Event('resize'); // resize是指resize事件
       window.dispatchEvent(myEvent); // 触发window的resize事件
    }
  },
  created() {
    this.init();
  },
  watch: {
        layoutData: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        }
    }
  
};
</script>

<style lang="less" scoped>
.vue-grid-item {
  background: #5b5b5f;
  
}
.tb {
    height: 85%;
    width: 97%;
    background-color: #5b5b5f;
    margin: 0 auto;
}
.title {
    margin: 0 auto;
    text-align: center;
    color: white;
    height: 10%;
    font-size: 150%;
}
.vueGridLayout {
  margin: 0 auto;
  height: 900px;
  width: 100%;
}


</style>