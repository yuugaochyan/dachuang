<template>
    <div id="app">
        <div class="view">
        <el-carousel 
        height="600px"
        trigger="click"
        >
            <el-carousel-item v-for="item in 4" :key="item">
            <img class="avImg" :src="imgSrc[item-1]"  alt="示例图片">
            </el-carousel-item>
        </el-carousel>
        <div class="intro" >
            <el-button type="warning" plain round @click="turnToMore('http://www.baidu.com')">
                获得更多
            </el-button>
        </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            imgSrc: [
            require("../../assets/pic/cd-background-1.jpg"),
            require("../../assets/pic/cd-background-2.jpg"),
            require("../../assets/pic/cd-background-3.jpg"),
            require("../../assets/pic/cd-background-4.jpg"),
            ],
        }
    },
    methods: {
        turnToMore(url){
            window.open(url,'_blank')
        }
    },

}
</script>

<style lang="less" scoped>
.view {
    // margin-top: 60px;
    // width: 1200px;
    // margin: auto;
    // float: left;
    background-color: #333;
    height: 100%;
}
.intro {
    text-align: center;
    padding: 10px;

}
.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.avImg {
    width: 100%;
}
</style>