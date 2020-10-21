<template>
    <!-- <div id="app"> -->
        
        <div id="water" :style="{height: '100%',width: '100%'}">
            <!-- <dv-decoration-1 style="width:200px;height:50px;" /> -->
            <dv-water-level-pond :config="config" style="width:100%;height:100%" />
        </div>
    <!-- </div> -->
</template>

<script>
import axios from 'axios';
import Charts from '@jiaminghi/charts';
// const barchart = document.getElementById('barchart')

// const bar = new Charts(barchart)
export default {
    name:'rollchart',
    data(){
        return {
            
            // msg:'',
            config: {
                data: [66, 45],
                shape: 'round'
            }
        }
    },
}
</script>
<style lang="less" scoped>

</style>