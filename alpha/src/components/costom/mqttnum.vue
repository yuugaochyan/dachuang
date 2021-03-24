<template>
        
        <div  v-bind:id=id ref="obdata" :style="{height: '100%',width: '100%',}">
            <dv-digital-flop :config="config" style="width:100%;height:100%;" />
            
        
        </div>
        
</template>

<script>
import axios from 'axios'
import mqtt from 'mqtt'
export default {
    name:'mqttnum',
    props:["id","obdata"],
    data(){
        return {
            
            reset:false,
            chart:'',
            vlist:[],
            tlist:[],
            config:{
                number:[0.00],
                toFixed: 2,
                content:this.obdata.tagName+': {nt}',
                style: {
                    fontSize:120,
                    fill:'#dfdfdf'
                }
            },
            interval:''
        }
    },
    
    methods: {
        
        drawline({
            tag='',
            max='',
            min='',
            tagName='',
            shape=''
        }={}){
            let that=this
            
            

            let obj=JSON.parse(localStorage.getItem('client'))
            // console.log(obj);
            for(let key in obj) {
                // console.log(obj[key]);
                if(obj[key].n==tag) {
                    that.config.number[0]=parseInt(obj[key].v)
                    that.config.toFixed=2  
                    if(obj[key].v>max){
                    that.config.style.fill='red'
                    }
                    else if(obj[key].v<min){
                        that.config.style.fill='white'
                    }
                    else {
                        that.config.style.fill='#e0bf41'
                    }
                    
                    this.config = { ...this.config }
                }
            }
            
                // console.log(this.datalist);
                // this.drawBar(this.tmplist,this.timelist,this.linename);
                // console.log(this.tmplist);
                // console.log(this.barlist);
                // this.config.data=this.barlist;
                
            
        },

        
        
        
    },
    mounted() {
        let that = this;
        this.drawline(this.obdata);
        this.interval =setInterval(()=>{
            setTimeout(()=>{
                // console.log(client);
                // console.log(that.obdata);
                that.drawline(that.obdata);
            },300)
        },7000)
    },
    beforeDestroy() {
        clearInterval(this.interval);
        // client.end()
    },
    watch: {
        obdata: {
            handler(value) {
                this.drawline(value)
            }
        },
        // tlist: function() {
            // this.drawline(this.obdata)
        // },
        deep:true //深度监听
    }

}
</script>