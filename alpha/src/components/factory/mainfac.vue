<template>
    <div id="app">
    <!-- <dv-full-screen-container> -->
        <!-- <dv-loading>Loading...</dv-loading> -->
        <div class="container">
            <dv-border-box-10 :color="['rgb(24, 30, 39)','rgb(13, 231, 194)']" >
                <div class="mainbd">
                    <div class="column">
                        <div class="pie">
                            <dv-border-box-8 >
                                <div class="title">
                                    <h3>pie</h3>
                                </div>
                                <div class="piechart">
                                    
                                </div>
                            </dv-border-box-8>
                        </div>
                        <div class="pie">
                            <dv-border-box-8 :reverse="true">
                                <div class="title">
                                    <h3>pie</h3>
                                </div>
                                <div class="piechart">

                                </div>
                            </dv-border-box-8>
                        </div>
                        <div class="alarm">
                            <dv-border-box-9>
                                alarm
                            </dv-border-box-9>
                        </div>
                    </div>
                    <div class="column">
                        <div class="top">
                            <div class="line">
                                <dv-border-box-4 :color="['rgb(26, 39, 43)','rgb(24, 30, 39)']" >
                                    <div class="linetitle">
                                        <dv-decoration-1 style="width:200px;height:50px;" />
                                    </div>
                                    <linechart class="lchart"></linechart>
                                    <div class="cut">
                                        <dv-decoration-2 :reverse="true" style="width:5px;height:250px;" dur:15 :color="['rgb(13, 231, 194)','rgb(24, 30, 39)']" />
                                    </div>
                                </dv-border-box-4>
                            </div>
                            <div class="bar">
                                <dv-border-box-4 :reverse="true" :color="['rgb(26, 39, 43)','rgb(24, 30, 39)']" >
                                    <barchart class="bchart"></barchart>
                                    <div class="bartitle">
                                        <dv-decoration-1 style="width:200px;height:50px;" />
                                    </div>
                                    
                                </dv-border-box-4>
                            </div>
                            <div class="num">
                                <!-- 'rgb(26, 39, 43)' -->
                                <!-- rgb(24, 30, 39) -->
                                <dv-border-box-11 title="数字" :color="['#0de7c2','#1c2b28']">
                                    <div class="numtext">
                                        <!-- 13, 231, 194 -->
                                        <dv-decoration-9 style="width:150px;height:150px;" :color="['rgb(24, 30, 39)','rgb(13, 231, 194)']">
                                            1212
                                        </dv-decoration-9>
                                    </div>
                                </dv-border-box-11>
                            </div>
                        </div>
                        <div class="bot">
                            <div class="news">
                                <div class="tinynews">
                                    <dv-border-box-13 :color="['rgb(26, 39, 43)','rgb(24, 30, 39)']">
                                        news1
                                    </dv-border-box-13>
                                </div>
                                <div class="tinynews">
                                    <dv-border-box-13 :color="['rgb(26, 39, 43)','rgb(24, 30, 39)']">
                                        news2
                                    </dv-border-box-13>
                                </div>
                            </div>
                            <div class="all">
                                <dv-border-box-3 :color="['rgb(26, 39, 43)','rgb(24, 30, 39)']">
                                    <allchart></allchart>
                                    <!-- <router-view name="allchart"></router-view> -->
                                </dv-border-box-3>
                            </div>
                        </div>
                    </div>
                </div>
            </dv-border-box-10>
        </div>
        <!-- </dv-full-screen-container> -->
        <!-- <dv-loading>Loading...</dv-loading> -->       
    </div>
</template>

<script>
import barchart from '@/components/factory/mainchart/barchart'
import linechart from '@/components/factory/mainchart/linechart'
import allchart from '@/components/factory/mainchart/allchart'
import axios from 'axios'
export default {
    name:"mainfac",
    data(){
        return {
            msg:''
        }
    },
    components: {
        allchart,
        linechart,
        barchart,
    },

methods: {
    

}
}
</script>

<style lang="less" scoped>
.container {
    height: 100%;
    width: 100%;
    min-width: 1224px;
    max-width: 1920px;
    background-image: url("../../assets/pic/bg5.png");
    background-repeat: no-repeat;
    background-size: cover;
    // opacity: 0.5;
}
.mainbd {
    display: flex;
    // background-color:  #fff;
    padding: 0.25rem;
    margin: 0 auto;
    .column {
        flex: 1;
        &:nth-child(2) {
            flex: 4;
        }
        .pie {
            height: 3.375rem;
            // background-color: #fff;
            margin-right: 0.125rem;
            margin-bottom: 0.125rem;
            padding: 0.125rem;
            position: relative;
            h3 {
                font-weight: normal;
                padding: 0;
                margin: 0;
                // text-align: center;
            }
            .title {
                position: absolute;
                bottom: 0.125rem;
                // left: 40%;
                right: 0.125rem;
            }
            .piechart {
                height: 100%;
                width: 100%;
                background-color: rgba(51, 51, 51, 0.212);
            }
        }
        .alarm {
            height: 4rem;
            // background-color: #fff;
            padding: 0.125rem;
            margin-right: 0.125rem;
        }
        // background-color: #333;
    }
}

.top {
    height: 5rem;
    // background-color: #fff;
    // padding: 0.125rem;
    margin-bottom: 0.125rem;
    display: flex;
    .line {
        flex:2;
        // background-color: #fff;
        padding: 0.125rem;
        margin-right: 0.125rem;
        position: relative;
        .linetitle {
            position: absolute;
            top: 3%;
            left: 3%;
        }
        .cut {
            position: absolute;
            right: 0%;
        }
        .lchart {
            position: absolute;
            top: 17%;
            left: 3%;
            width: 100%;
            height: 100%;
        }
    }
    .bar {
        flex:2;
        // background-color: #fff;
        padding: 0.125rem;
        margin-right: 0.125rem;
        position: relative;
        .bartitle {
            position: absolute;
            bottom: 3%;
            right: 3%;
        }
        .bchart {
            position: absolute;
            top: 5%;
            left: -3%;
            width: 100%;
            height: 100%;
        }
    }
    .num {
        flex:1;
        // background-color: #fff;
        padding: 0.125rem;
        position: relative;
        .numtext {
            position: absolute;
            top: 30%;
            left: 12.5%;
        }
    }
}
.bot {
    height: 6.625rem;
    display: flex;
    .news {
        flex: 1;
        // background-color: #fff;
        // padding: 0.125rem;
        margin-right: 0.125rem;
        .tinynews {
            // background-color: #fff;
            padding: 0.125rem;
            height: 3rem;
            margin-bottom: 0.125rem;
        }
    }
    .all {
        flex: 2.3;
        // background-color: #fff;
        padding: 0.125rem;
    }
}
</style>