<template>
    <div id="app">
        <div class="maninbox">
          <div class="column">
            <div class="panel">
              <h3>柱形图-？？？</h3>
              <div id="myChart" :style="{height: '3rem',width: '100%'}"></div>
              <div class="panel-footer"></div>
            </div>
            <div class="panel line">
              <h3>折线图-？？？</h3>
              <div class="chart" id="linechart"></div>
              <div class="panel-footer"></div>
            </div>
            <div class="panel pie">
              <h3>饼形图-？？？</h3>
              <div class="chart"></div>
              <div class="panel-footer"></div>
            </div>
          </div>

          <div class="column">
            <div class="no">
              <div class="hd">
                <ul>
                  <li>{{time1}}</li>
                  <li>{{time2}}</li>
                </ul>
              </div>
              <div class="bd">
                <ul>
                  <li>{{time1}}</li>
                  <li>{{time2}}</li>
                </ul>
              </div>
            </div>
            <div class="map">
              <div class="map1">
                
              </div>
              <div class="map2">
                2
              </div>
              <div class="map3">
                3
              </div>
            </div>
          </div>

          <div class="column">
            <div class="panel">
              <h3>柱形图-？？？</h3>
              <div class="chart"></div>
              <div class="panel-footer"></div>
            </div>
            <div class="panel">
              <h3>柱形图-？？？</h3>
              <div class="chart"></div>
              <div class="panel-footer"></div>
            </div>
            <div class="panel">
              <h3>柱形图-？？？</h3>
              <div class="chart"></div>
              <div class="panel-footer"></div>
            </div>
          </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
data(){
  return{
    time1:"123456",
    time2:"222222",
    // base : new Date(1968, 9, 3),
    // oneDay : 24 * 3600 * 1000,
    // date : [],
    // data : [Math.random() * 300],
  }
},
mounted(){
  
},
methods: {
  
    
}
}
</script>

<style lang="less" scoped>
.maninbox {
  // width: 100%;
  // height: 100%;
  display: flex;
  min-width: 980px;
  // width: 500px;
  max-width: 1920px;
  // background-color: pink;
  // background-image: url("../../assets/pic/loginbgp.jpg");
  // background-size: cover;
  // background-repeat: no-repeat;
  // background-color: #535263;
  background-color:  rgba(41, 44, 51, .99);;
  padding: 0.125rem 0.125rem 0;
  
  margin: 0 auto;
  .column {
    flex:3;
  }
  .column:nth-child(2){
      flex:5;
      margin: 0 0.125rem 0.1875rem;
  }
  .panel {
    position: relative;
    height: 3.875rem;
    background-color: rgba(95, 137, 161, 0.5);
    border: 1px solid rgba(25, 186, 139, 0.17);
    padding: 0 0.1875rem 0.5rem;
    margin-bottom: 0.1875rem;
    &::before{
      position: absolute;
      top: 0;
      left: 0;
      width: 10px;
      height: 10px;
      border-left: 2px solid #02a6b5;
      border-top: 2px solid #02a6b5;
      content: " ";
    }
    &::after{
      position: absolute;
      top: 0;
      right: 0;
      width: 10px;
      height: 10px;
      border-right: 2px solid #02a6b5;
      border-top: 2px solid #02a6b5;
      content: " ";
    }
    .panel-footer {
      position: absolute;
      bottom: 0;
      left: 0;
      width: 100%;
      &::before{
        position: absolute;
        bottom: 0;
        left: 0;
        width: 10px;
        height: 10px;
        border-left: 2px solid #02a6b5;
        border-bottom: 2px solid #02a6b5;
        content: " ";
      }
      &::after{
        position: absolute;
        bottom: 0;
        right: 0;
        width: 10px;
        height: 10px;
        border-right: 2px solid #02a6b5;
        border-bottom: 2px solid #02a6b5;
        content: " ";
      }
    }
    h3 {
      height: 0.6rem;
      color: #fff;
      line-height: 0.6rem;
      font-size: 0.25rem;
      text-align: center;
      font-weight: normal;
      margin: 0;
    }
    .chart {
      height: 3rem;
      // height: 240px;
      width: 100%;
      background-color: rgba(255, 255, 255, 0.377);
    }
  }
}



.no {
  background-color: rgba(95, 137, 161, 0.5);
  padding: 0.1875rem;
  .hd {
    position: relative;
    border: 1px solid rgba(15, 186, 139, 0.17);
    ul {
      display: flex;
      li {
        position: relative;
        flex:1;
        line-height: 1rem;
        font-size: 0.875rem;
        text-align: center;
        list-style: none;
        color: #ffeb7b;
        &:nth-child(1)::after {
          content: '';
          position: absolute;
          top: 25%;
          right: 0;
          height: 50%;
          width: 1px;
          background-color: rgba(255, 255, 255, 0.2);
        }
      }
    }
    &::before {
      position: absolute;
      top: 0;
      left: 0;
      content: '';
      width: 30px;
      height: 10px;
      border-top: 1px solid #02a6b5;
      border-left: 1px solid #02a6b5;
    }
    &::after {
      position: absolute;
      bottom: 0;
      right: 0;
      content: '';
      width: 30px;
      height: 10px;
      border-bottom: 1px solid #02a6b5;
      border-right: 1px solid #02a6b5;
    }
  }
  .bd {
    ul {
      display: flex;
      li {
        flex: 1;
        color: rgba(255, 255, 255, 0.7);
        text-align: center;
        font-size:0.225rem ;
        height: 0.5rem;
        line-height: 0.5rem;
        padding: 0.125rem;
      }
    }
  }
}
.map {
  height: 10.125rem;
  background-color: rgba(41, 44, 51, .99);
  position: relative;
  .map1 {
    background-image: url("../../assets/pic/map2.jpg");
    background-repeat: no-repeat;
    background-size: cover;
    // background-position-x: 48%;
    background-position-x: 48%;
    opacity: .3;
    width: 6.475rem;
    height: 6.475rem;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    animation: rotate1 15s linear infinite;
  }
  @keyframes rotate1 {
    form {
      transform: translate(-50%,-50%)
      rotate(0deg);
    }
    to {
      transform: translate(-50%,-50%)
      rotate(360deg);
    }
  }
}

</style>