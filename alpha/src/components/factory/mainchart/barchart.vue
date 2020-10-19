<template>
    <div id="app">
        
        <div id="barchart" :style="{height: '4rem',width: '100%',}">
            <!-- <dv-decoration-1 style="width:200px;height:50px;" /> -->
            <dv-capsule-chart :config="config" style="width:100%;height:100%" />
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Charts from '@jiaminghi/charts';
// const barchart = document.getElementById('barchart')

// const bar = new Charts(barchart)
export default {
    name:'barchart',
    data(){
        return {
            
            // msg:'',
            config: {
            data: [
            {
                name: '南阳',
                value: 167
            },
            {
                name: '周口',
                value: 123
            },
            {
                name: '漯河',
                value: 98
            },
            {
                name: '郑州',
                value: 75
            },
            {
                name: '西峡',
                value: 66
            },
            ],
            colors: ['#e062ae', '#fb7293', '#e690d1', '#32c5e9', '#96bfff'],
            unit: '单s位'
            }
        }
    },
    // mounted() {
        // this.drawbar();
    // },
    methods: {
        updateHandler () {
        const { config } = this
        this.config.data =  [
            {
                name: '南阳',
                value: 167
            },
            {
                name: '周口',
                value: 123
            },
            {
                name: '漯河',
                value: 98
            },
            {
                name: '郑州',
                value: 75
            },
            {
                name: '西峡',
                value: 66
            },
            ]
        // this.config.lineDash = [10, 4]
        this.config = { ...this.config }
    }
    }
        
}
</script>
<style lang="less" scoped>

</style>