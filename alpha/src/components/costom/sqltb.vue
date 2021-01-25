<template>
        
        <div  v-bind:id=id ref="obdata" :style="{height: '100%',width: '100%',}">
            <dv-scroll-board :config="obdata" style="width:100%;height:100%" />
        </div>
        
</template>

<script>
import axios from 'axios'
export default {
    name:'chart',
    props:["id","obdata"],
    data(){
        return {
            
            reset:false,
            chart:''
        }
    },
    
    methods: {
        
    },
    mounted () {
        
    },
    watch: {
        
    }

}
</script>

<style lang="less" scoped>

</style>