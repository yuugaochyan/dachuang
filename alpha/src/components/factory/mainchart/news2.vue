<template>
    <!-- <div id="app"> -->
        
        <div id="news2" :style="{height: '100%',width: '100%'}">
            <!-- <dv-decoration-1 style="width:200px;height:50px;" /> -->
            <dv-scroll-ranking-board :config="config" style="width:100%;height:100%" />
        </div>
    <!-- </div> -->
</template>

<script>
import axios from 'axios';
import Charts from '@jiaminghi/charts';
// const barchart = document.getElementById('barchart')

// const bar = new Charts(barchart)
export default {
    name:'news2',
    data(){
        return {
            
            // msg:'',
            config: {
                data: [
                {
                    name: '周口',
                    value: 55123
                },
                {
                    name: '南阳',
                    value: 12022
                },
                {
                    name: '西峡',
                    value: 78932
                },
                {
                    name: '驻马店',
                    value: 63411
                },
                {
                    name: '新乡',
                    value: 44231
                }
                ],
                unit: '单位',
                valueFormatter ({ value }) {
                    console.warn(arguments)
                    const reverseNumber = (value + '').split('').reverse()
                    let valueStr = ''

                    while (reverseNumber.length) {
                    const seg = reverseNumber.splice(0, 3).join('')
                    valueStr += seg
                    if (seg.length === 3) valueStr += ','
                    }

                    return valueStr.split('').reverse().join('')
                }
            }
        }
    },
}
</script>
<style lang="less" scoped>

</style>