<template>
    <div id="app" v-if="reset">
        <div class="vueGridLayout">
        <grid-layout
          :layout="layoutData"
          :col-num="12"
          :row-height="layoutHeight"
          :is-draggable="editable"
          :is-resizable="editable"
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
            
            
            <div class="title">
              {{index}}. {{item.title}}
              <el-tooltip class="item" effect="dark" content="删除这个可视化" placement="bottom-start" v-if="editable">
                <el-button size="mini" @click="deleteTB(item.i)" circle type="del" icon="iconfont icon-delete" class="toolbt"></el-button>
              </el-tooltip>
            </div>
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
</template>

<script>
// import layoutData from '../../assets/virtualData/layoutData.json'
import VueGridLayout from 'vue-grid-layout'
import chart from '@/components/costom/chart'
import mqttline from '@/components/costom/mqttline'
import sqltb from '@/components/costom/sqltb'
import mqttnum from '@/components/costom/mqttnum'
import axios from 'axios'

const GridLayout = VueGridLayout.GridLayout
const GridItem = VueGridLayout.GridItem
export default {
  name: "griddemo",
  props:["dbID","editable"],
  inject:['reload'],
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
    asideResize(){
     let myEvent = new Event('resize'); // resize是指resize事件
     window.dispatchEvent(myEvent); // 触发window的resize事件
    },
    deleteTB(tbID){
      console.log(tbID);
      let that = this;
      let postData=this.$qs.stringify({
          dbID:that.dbID,
          tbID:tbID
      })
      const result = axios({
          method: 'post',
          url:'/deleteTBinDB',
          data:postData
      }).then(function(resp){
          if(resp.data.status==200) {
            that.$message({
              showClose: true,
              message: '删除成功',
              center: true,
              type: 'success'
            });
          }
          that.reload();
      })
      
    },
    init() {
      
      let that=this
      let postDta=this.$qs.stringify({
          dbID:this.db
      })
      this.$axios.post("/getDBitemInfo",postDta)
      .then((resp)=>{
          that.layoutData = resp.data.data;
          localStorage.setItem('pandectDisplace', JSON.stringify(that.layoutData));
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
    height: 90%;
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
  height: calc(93.6vh);
  width: 100%;
}
.toolbt {
  float: right;
  margin: 5px;
}
.el-button--del {
  color: #FFF;
  background-color: #883030;
  border-color: #883030;
}
.el-button--del.is-active,
.el-button--del:active {
  background:#b34141;
  border-color: #883030;
  color: #fff;
}

.el-button--del:focus,
.el-button--del:hover {
  background:#b34141;
  border-color:#883030;
  color: #fff;
}
</style>