import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userID:JSON.parse(localStorage.getItem("userID")),
    token:JSON.parse(localStorage.getItem("token")),
    clientHeight:JSON.parse(localStorage.getItem("clientHeight")),
  },
  mutations: {
    setuserID(state,v) {
      localStorage.setItem('userID',JSON.stringify(v));
      state.userInfo = v;
    },
    setToken(state,v) {
      localStorage.setItem('token',JSON.stringify(v));
      state.token = v;
    },
    setHeight(state,v) {
      localStorage.setItem('clientHeight',JSON.stringify(v));
      state.clientHeight = v;
    }
  },
  actions: {
  },
  modules: {
  }
})
