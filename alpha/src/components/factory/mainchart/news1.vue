<template>
    <!-- <div id="app"> -->
        
        <div id="news1" :style="{height: '100%',width: '100%'}" v-if="reset">
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
            reset:false,
            
            // msg:'',
            config: {
            }
        }
    },
    methods: {
        news1Ajax() {
            let that = this;
            const result = axios.get('/getChart/news1',{
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
        this.news1Ajax();
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