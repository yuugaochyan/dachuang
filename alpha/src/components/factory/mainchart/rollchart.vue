<template>
    <!-- <div id="app"> -->
        
        <div id="rollchart" :style="{height: '100%',width: '100%'}">
            <!-- <dv-decoration-1 style="width:200px;height:50px;" /> -->
            <dv-active-ring-chart :config="config" style="width:100%;height:100%" />
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
            radius: '75%',
            activeRadius: '65%',
            lineWidth: 30,
            data: [
            {
                name: '周口',
                value: 55
            },
            {
                name: '南阳',
                value: 120
            },
            {
                name: '西峡',
                value: 78
            },
            {
                name: '驻马店',
                value: 66
            },
            {
                name: '新乡',
                value: 80
            }
            ],
            digitalFlopStyle: {
            fontSize: 20
            }
            }
        }
    },
}
</script>
<style lang="less" scoped>

</style>