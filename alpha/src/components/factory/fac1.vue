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
  this.drawBar();
},
methods: {
  drawBar(){
    // let bar=this.$echarts.init(document.getElementById('bar'))
    // bar.setOption({
      
      let myChart = this.$echarts.init(document.getElementById('myChart'))
        // 绘制图表
        var base = +new Date(1968, 9, 3);
        var oneDay = 24 * 3600 * 1000;
        var date = [];

       var data = [Math.random() * 300];

      for (var i = 1; i < 20000; i++) {
        var now = new Date(base += oneDay);
        date.push([now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'));
        data.push(Math.round((Math.random() - 0.5) * 20 + data[i - 1]));
      }
        myChart.setOption({

    tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0], '10%'];
        }
    },
    title: {
        left: 'center',
        text: '大数据量面积图',
    },
    toolbox: {
        feature: {
            dataZoom: {
                yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: date
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%']
    },
    dataZoom: [{
        type: 'inside',
        start: 0,
        end: 10
    }, {
        start: 0,
        end: 10,
        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
        handleSize: '80%',
        handleStyle: {
            color: '#fff',
            shadowBlur: 3,
            shadowColor: 'rgba(0, 0, 0, 0.6)',
            shadowOffsetX: 2,
            shadowOffsetY: 2
        }
    }],
    series: [
        {
            name: '模拟数据',
            type: 'line',
            smooth: true,
            symbol: 'none',
            sampling: 'average',
            itemStyle: {
                color: 'rgb(255, 70, 131)'
            },
            areaStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgb(255, 158, 68)'
                }, {
                    offset: 1,
                    color: 'rgb(255, 70, 131)'
                }])
            },
            data: data
        }
    ]


        });
  }
    
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