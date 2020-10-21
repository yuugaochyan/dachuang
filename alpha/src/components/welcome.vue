<template>
    <div id="app" >


        <div class="fixedBg bgi0">
            <span class="title"><br><br><br></span>
        </div>
        <div class="text t1">
            <span class="txt1">
            工业互联网
            <!-- <img src="../assets/pic/banner3.jpg" alt=""> -->
            </span>
        </div>
        <div class="fixedBg bgi1">
            <span class="title"><br><br></span>
        </div>
        <div class="text t2">
            <span class="txt2">标题2</span>
        </div>
        <div class="fixedBg bgi2">
            <span class="title"><br><br></span>
        </div>
        <div class="text t3">
            <span class="txt1">标题2</span>
        </div>
        <div class="fixedBg bgi3">
            <span class="title"><br><br></span>
        </div>
        <div class=" t4">
            <span>标题3</span>
        </div>
        
        

    </div>
</template>

<script>
import axios from 'axios'
export default {

    
}
</script>

<style lang="less" scoped>




.fixedBg { 
    min-height: 380px; 
    background-position: center center; 
    background-repeat: no-repeat; 
    background-attachment: fixed;
    background-size: cover;
    
    text-align: center;
}
.bgi0 {
    background-image: url("../assets/pic/niu.gif");
}
.bgi1 {
    background-image: url("../assets/pic/icon.jpg");
}
.bgi2 {
    background-image: url("../assets/pic/cd-background-2.jpg");
}
.bgi3 {
    background-image: url("../assets/pic/cd-background-1.jpg");
}
.text {
    // background: #333;
    width: 100%;
    height: 500px;
    text-align: center;
    // line-height: 500px;
    // position: relative;
}

.title {
    color: #fff;
    font-size: 100px;
    line-height: 270px;
    
}
.txt1 {
    color: #fff;
    font-size: 100px;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    line-height: 500px;
    // position: absolute;
    // top: 50%;
    // left: 50%;
    // transform: translate(-50%, -50%);
    // vertical-align: middle;
}
.txt2 {
    color: #333;
    font-size: 35px;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    line-height: 500px;
    // vertical-align: middle;
    // position: absolute;
    // top: 50%;
    // left: 50%;
    // transform: translate(-50%, -50%);
}

.t1 {
    background-color: #333;
    // background-image: url("../assets/pic/icon3.png");
    // background-size: cover;
}
.t2 {
    background-color: rgba(216, 153, 37, 0.8);
}
.t3 {
    background-color: #333
    ;
}
.t4 {
    width: 100%;
    height: 50px;
    background-color: rgba(216, 153, 37, 0.8);
}
</style>