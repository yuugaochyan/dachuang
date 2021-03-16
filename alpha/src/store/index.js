import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // userID:JSON.parse(localStorage.getItem("userID")),
    // token:JSON.parse(localStorage.getItem("token")),
    clientHeight:JSON.parse(localStorage.getItem("clientHeight")),
    showHint:JSON.parse(localStorage.getItem("showHint")),
    client:JSON.parse(localStorage.getItem("client")),
  },
  mutations: {
    // setuserID(state,v) {
      // localStorage.setItem('userID',JSON.stringify(v));
      // state.userInfo = v;
    // },
    // setToken(state,v) {
      // localStorage.setItem('token',JSON.stringify(v));
      // state.token = v;
    // },
    setHeight(state,v) {
      localStorage.setItem('clientHeight',JSON.stringify(v));
      state.clientHeight = v;
    },
    setShowHint(state,v) {
      localStorage.setItem('showHint',JSON.stringify(v));
      state.showHint = v;
    },
    setClient(state,v) {
      localStorage.setItem('client',JSON.stringify(v));
      state.client = v;
    }
  },
  actions: {
  },
  modules: {
  }
})
