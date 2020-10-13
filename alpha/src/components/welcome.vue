<template>
    <div id="app" >


        <div class="fixedBg bgi0">
            <span>带标题</span>
        </div>
        <div class="text t1">
            <span>标题1</span>
            </div>
        <div class="fixedBg bgi1">
            <span>??</span>
        </div>
        <div class="text t2">
            <span>标题2</span>
        </div>
        <div class="fixedBg bgi2">
            <span>??</span>
        </div>
        <div class="text t3">
            <span>标题2</span>
        </div>
        <div class="fixedBg bgi3">
            <span>..</span>
        </div>
        <div class="text t4">
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
    background-image: url("../assets/pic/cd-background-1.jpg");
}
.bgi1 {
    background-image: url("../assets/pic/cdbg1.jpg");
}
.bgi2 {
    background-image: url("../assets/pic/cd-background-2.jpg");
}
.bgi3 {
    background-image: url("../assets/pic/cd-background-3.jpg");
}
.text {
    // background: #333;
    width: 100%;
    height: 500px;
    text-align: center;
    
}
span {
    color: #fff;
    font-size: 100px;
    line-height: 500px;
    
}

.t1 {
    background-color: #333;
}
.t2 {
    background-color: rgba(216, 153, 37, 0.8);
}
.t3 {
    background-color: #333
    ;
}
.t4 {
    background-color: rgba(216, 153, 37, 0.8);
}
</style>