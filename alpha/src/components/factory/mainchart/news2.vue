<template>
    <!-- <div id="app"> -->
        
        <div id="news2" :style="{height: '100%',width: '100%'}" v-if="reset">
            <!-- <dv-decoration-1 style="width:200px;height:50px;" /> -->
            <dv-scroll-ranking-board  :config="config" style="width:100%;height:100%" />
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
            reset:false,
            
            // msg:'',
            config: {
                //data: [],
                //unit: '单位',
                //valueFormatter ({ value }) {
                //    console.warn(arguments)
                //    const reverseNumber = (value + '').split('').reverse()
                //    let valueStr = ''
//
                //    while (reverseNumber.length) {
                //    const seg = reverseNumber.splice(0, 3).join('')
                //    valueStr += seg
                //    if (seg.length === 3) valueStr += ','
                //    }
//
                //    return valueStr.split('').reverse().join('')
                //}
            }
        }
    },
    methods: {
        news2Ajax() {
            let that = this;
            const result = axios.get('/getChart/news2',{
                params:{
                    userid:that.$store.state.token
                }
                }).then((resp)=>{
                    that.config=resp.data;
                    // console.log(resp.data);
                    // console.log(that.reset);
                }).catch((resp)=>{
                    console.log(resp);
                })
        }
    },
    beforeMount(){
        this.news2Ajax();
    },
    mounted() {
        
    },
    
    watch: {
        config: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        }
    }


}
</script>
<style lang="less" scoped>

</style>