<template>
    <!-- <div id="app"> -->
        
        <div id="news1" :style="{height: '100%',width: '100%'}">
            <!-- <dv-decoration-1 style="width:200px;height:50px;" /> -->
            <dv-scroll-board :config="config" style="width:100%;height:100%" />
        </div>
    <!-- </div> -->
</template>

<script>
import axios from 'axios';
import Charts from '@jiaminghi/charts';
// const barchart = document.getElementById('barchart')

// const bar = new Charts(barchart)
export default {
    name:'news1',
    data(){
        return {
            
            // msg:'',
            config: {
            header: ['列1', '列2', '列3'],
            data: [
                ['行1列1', '行1列2', '行1列3'],
                ['行2列1', '行2列2', '行2列3'],
                ['行3列1', '行3列2', '行3列3'],
                ['行4列1', '行4列2', '行4列3'],
                ['行5列1', '行5列2', '行5列3'],
                ['行6列1', '行6列2', '行6列3'],
                ['行7列1', '行7列2', '行7列3'],
                ['行8列1', '行8列2', '行8列3'],
                ['行9列1', '行9列2', '行9列3'],
                ['行10列1', '行10列2', '行10列3']
            ]
            }
        }
    },
}
</script>
<style lang="less" scoped>

</style>